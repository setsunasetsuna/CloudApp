package com.setsuna.cloudapp.download;

import android.text.TextUtils;

import com.example.mylib.util.MakeFileHash;
import com.example.mylib.util.SDUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.setsuna.cloudapp.Constant;
import com.setsuna.cloudapp.base.CloudApp;
import com.setsuna.cloudapp.entity.DownloadPreEntity;
import com.setsuna.cloudapp.share.ShareCookie;
import com.setsuna.cloudapp.utils.FormatSize;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by setsuna on 2016/9/28.
 */
public class DownloadThread extends Thread {
    private DownLoadFile mDownLoadFile;
    private DownloadControl mDownloadControl;
    private ShareCookie mShareCookie;
    private DownloadMSG mDownloadMSG=new DownloadMSG();

    private void postMSG(int change){
        mDownloadMSG.setChange(change);
        mDownloadMSG.setDownLoadFile(mDownLoadFile);
        EventBus.getDefault().post(mDownloadMSG);
    }

    @Override
    public void run() {
        super.run();
        mDownloadControl=new DownloadControl();
        mShareCookie=new ShareCookie(CloudApp.mContext);
        while (true){
            synchronized (DownloadThread.class){
                mDownLoadFile=mDownloadControl.getTaskWait();
                if (mDownLoadFile==null){
                    break;
                }
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_PREPARING);
                postMSG(DownloadMSG.CHANGE_STATE);
                
            }
            boolean isPreOK=doPrepare();
            if (!isPreOK){
                continue;
            }
            mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_DOWNLOADING);
            postMSG(DownloadMSG.CHANGE_STATE);
            doDownload();
        }
    }

    private void doDownload() {
        try {
            URL url=new URL(mDownLoadFile.getUrl());
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestProperty("Cookie","fuid="+mShareCookie.getFuid()+"; token="+mShareCookie.getToken());
            //断点下载，就是增加这样一个请求头即可
            httpURLConnection.setRequestProperty("Range", "bytes=" + mDownLoadFile.getDownloadSize() + "-");
            InputStream inputStream=httpURLConnection.getInputStream();
            File file=new File(mDownLoadFile.getSavePath());
            File fileDir=new File(file.getParent());
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            boolean hasDone=mDownloadControl.taskExist(mDownLoadFile);
            if (hasDone){
                file.delete();
            }
            FileOutputStream fileOutputStream=null;
            if (mDownLoadFile.getDownloadSize()!=0){
                fileOutputStream=new FileOutputStream(file,true);
            }else {
                fileOutputStream=new FileOutputStream(file);
            }

            byte[] bs=new byte[1024*500];
            int total=0;
            long newDownSize=0;
            long begin=System.currentTimeMillis();
            while ((total=inputStream.read(bs))!=-1){
                mDownloadControl.syncNowState(mDownLoadFile);
                if (mDownLoadFile.getState()==DownloadState.STATE_PAUSE||mDownLoadFile.getState()==DownloadState.STATE_CANCLE){
                    break;
                }
                fileOutputStream.write(bs,0,total);
                mDownloadControl.updateDownloadSize(mDownLoadFile,total);
                postMSG(DownloadMSG.CHANGE_PROGRESS);
                long now=System.currentTimeMillis();
                long cha=now-begin;
                newDownSize+=total;
                if (cha>1000){
                    double second=cha*1.0/1000;
                    String speed= FormatSize.getSize((long) Math.ceil(newDownSize/second))+"/s";
                    mDownloadControl.updateSpeed(mDownLoadFile,speed);
                    postMSG(DownloadMSG.CHANGE_SPEED);
                    begin=now;
                    newDownSize=0;
                }
            }
            if (mDownLoadFile.getState()!=DownloadState.STATE_PAUSE&&mDownLoadFile.getState()!=DownloadState.STATE_CANCLE){
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SHA1CHECKING);
                postMSG(DownloadMSG.CHANGE_STATE);
                if (TextUtils.isEmpty(mDownLoadFile.getSha1())){
                    mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SHA1ERROR);
                    postMSG(DownloadMSG.TASK_OVER);
                }else {
                    String sha1Local= MakeFileHash.getFileSHA1(file.getAbsolutePath());
                    String sha1Right=mDownLoadFile.getSha1();
                    if (sha1Local.equalsIgnoreCase(sha1Right)){
                        mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_DONE);
                        postMSG(DownloadMSG.TASK_OVER);
                    }else {
                        mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SHA1ERROR);
                        mDownloadControl.clearDownload(mDownLoadFile);
                        postMSG(DownloadMSG.CHANGE_STATE);
                    }
                }
            }
            inputStream.close();
            fileOutputStream.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mDownloadControl.updateState(mDownLoadFile, DownloadState.STATE_SERVER);
            postMSG(DownloadMSG.CHANGE_STATE);
        } catch (IOException e) {
            e.printStackTrace();
            mDownloadControl.updateState(mDownLoadFile, DownloadState.STATE_NETERROR);
            postMSG(DownloadMSG.CHANGE_STATE);
        }

    }

    private boolean doPrepare() {
        return preSD() && preURL();
    }

    private boolean preSD() {
        synchronized (DownloadThread.class){
            boolean isSDOK= SDUtil.canUseSDCard(mDownLoadFile.getTotalSize()+mDownloadControl.getAllIngTaskNeedSize());
            if (!isSDOK){
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SDERROR);
                postMSG(DownloadMSG.CHANGE_STATE);
            }
            return isSDOK;
        }
    }

    private boolean preURL() {
        RequestParams requestParams=new RequestParams(Constant.DOWNLOAD_PRE_URL);
        requestParams.addBodyParameter("fid",mDownLoadFile.getFid());
        try {
            String jsonStr= x.http().postSync(requestParams,String.class);
            if (TextUtils.isEmpty(jsonStr)){
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SERVER);
                postMSG(DownloadMSG.CHANGE_STATE);
                return false;
            }
            Gson gson=new Gson();
            DownloadPreEntity downloadPreEntity=null;

            try {
                downloadPreEntity=gson.fromJson(jsonStr,DownloadPreEntity.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SERVER);
                postMSG(DownloadMSG.CHANGE_STATE);
                return false;
            }
            if (downloadPreEntity==null){
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SERVER);
                postMSG(DownloadMSG.CHANGE_STATE);
                return false;
            }
            String url=downloadPreEntity.getUrl();
            if (!downloadPreEntity.isState() || TextUtils.isEmpty(url)){
                mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_SERVER);
                postMSG(DownloadMSG.CHANGE_STATE);
                return false;
            }
            mDownloadControl.updateUrl(mDownLoadFile, url.replace("http://101.200.183.103", "http://101.200.183.103:9999"));
            return true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            mDownloadControl.updateState(mDownLoadFile,DownloadState.STATE_NETERROR);
            postMSG(DownloadMSG.CHANGE_STATE);
            return false;
        }
    }


}
