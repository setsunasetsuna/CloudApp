package com.setsuna.cloudapp.download;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.setsuna.cloudapp.base.CloudApp;
import com.setsuna.cloudapp.entity.FileEntity;
import com.setsuna.cloudapp.utils.GetSavaPath;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by setsuna on 2016/9/28.
 */
public class DownloadControl {
    private DbManager.DaoConfig mDaoConfig = DownloadConfig.mDaoConfig;
    private Context mContext = CloudApp.mContext;
    private Intent mIntent;

    public void addToQueue(FileEntity.DataBean dataBean, List<FileEntity.PathBean> pathBeans) {
        if (TextUtils.isEmpty(dataBean.getCid())) {
            addFile(dataBean, pathBeans);
        } else {
            addDir(dataBean, pathBeans);
        }
        startDownload();
    }


    private void addDir(FileEntity.DataBean dataBean, List<FileEntity.PathBean> pathBeans) {
    }

    private void addFile(FileEntity.DataBean dataBean, List<FileEntity.PathBean> pathBeans) {
        DownLoadFile downLoadFile = new DownLoadFile();
        downLoadFile.setFid(dataBean.getFid());
        downLoadFile.setName(dataBean.getN());
        downLoadFile.setPid(dataBean.getPid());
        downLoadFile.setSavePath(DownloadConfig.ROOT_PATH + "/" + GetSavaPath.getPath(pathBeans) + dataBean.getN());
        downLoadFile.setTotalSize(dataBean.getS());
        downLoadFile.setSha1(dataBean.getSha1());
        downLoadFile.setState(DownloadState.STATE_WAITING);
        downLoadFile.setTime(System.currentTimeMillis());

        try {
            x.getDb(mDaoConfig).save(downLoadFile);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void startDownload() {
        if (mIntent == null) {
            mIntent = new Intent();
            mIntent.setClass(mContext, DownloadService.class);
        }
        mContext.startService(mIntent);
    }

    /**
     * @param downLoadFile
     * @param url
     */
    public void updateUrl(DownLoadFile downLoadFile, String url) {
        downLoadFile.setUrl(url);
        try {
            x.getDb(mDaoConfig).update(downLoadFile, "url");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateState(DownLoadFile downLoadFile, int state) {
        downLoadFile.setState(state);
        try {
            x.getDb(mDaoConfig).update(downLoadFile, "state");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateSpeed(DownLoadFile downLoadFile, String speed) {
        downLoadFile.setSpeed(speed);
        try {
            x.getDb(mDaoConfig).update(downLoadFile, "speed");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateDownloadSize(DownLoadFile downLoadFile, long newSize) {
        downLoadFile.setDownloadSize(downLoadFile.getDownloadSize() + newSize);
        try {
            x.getDb(mDaoConfig).update(downLoadFile, "downloadSize", "downloadSizeFormat");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void clearDownload(DownLoadFile downLoadFile) {
        downLoadFile.setDownloadSize(0);
        File file = new File(downLoadFile.getSavePath());
        file.delete();
        try {
            x.getDb(mDaoConfig).update(downLoadFile, "downloadSize", "downloadSizeFormat");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public DownLoadFile getTaskWait() {
        try {
            return x.getDb(mDaoConfig).selector(DownLoadFile.class).where("state", "=", DownloadState.STATE_WAITING).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean taskExist(DownLoadFile downLoadFile) {
        try {
            DownLoadFile task = x.getDb(mDaoConfig).selector(DownLoadFile.class).where("state", "=", DownloadState.STATE_DONE).and("fid", "=", downLoadFile.getFid()).findFirst();
            if (task != null) {
                x.getDb(mDaoConfig).delete(task);
                return true;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<DownLoadFile> getAllTaskNotDone() {
        try {
            return x.getDb(mDaoConfig).selector(DownLoadFile.class).where("state", "<>", DownloadState.STATE_DONE).and("state", "<>", DownloadState.STATE_CANCLE).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int syncNowState(DownLoadFile downLoadFile) {
        try {
            DownLoadFile downLoadFileDB = x.getDb(mDaoConfig).selector(DownLoadFile.class).where("id", "=", downLoadFile.getId()).findFirst();
            downLoadFile.setState(downLoadFileDB.getState());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return DownloadState.STATE_DONE;
    }

    public void cancelTask(DownLoadFile downLoadFile) {
        clearDownload(downLoadFile);
        updateState(downLoadFile, DownloadState.STATE_CANCLE);
    }

    public long getAllIngTaskNeedSize() {
        long total = 0;
        try {
            List<DownLoadFile> allIngTasks = x.getDb(mDaoConfig).selector(DownLoadFile.class).where("state", "=", DownloadState.STATE_DOWNLOADING).findAll();
            for (DownLoadFile downLoadFile : allIngTasks) {
                total += (downLoadFile.getTotalSize() - downLoadFile.getDownloadSize());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return total;
    }


}
