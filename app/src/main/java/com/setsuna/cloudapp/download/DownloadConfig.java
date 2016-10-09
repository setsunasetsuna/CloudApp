package com.setsuna.cloudapp.download;

import android.os.Environment;

import org.xutils.DbManager;

/**
 * Created by setsuna on 2016/9/24.
 */
public class DownloadConfig {
    public static DbManager.DaoConfig mDaoConfig;
    public static String ROOT_PATH;
    public static final int DOWNLOAD_THREAD_COUNT=2;
    public static boolean inti(){
        try {
            if (mDaoConfig==null){
                mDaoConfig=new DbManager.DaoConfig();
                mDaoConfig.setDbName("cloudDB");
                mDaoConfig.setDbVersion(1);
                ROOT_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/cloud";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
