package com.setsuna.cloudapp.download;

/**
 * Created by setsuna on 2016/9/28.
 */
public class DownloadMSG {
    private DownLoadFile mDownLoadFile;
    private int change;

    public static final int CHANGE_STATE=1112;
    public static final int CHANGE_SPEED=1113;
    public static final int CHANGE_PROGRESS=1114;
    public static final int TASK_OVER=1115;

    public DownLoadFile getDownLoadFile() {
        return mDownLoadFile;
    }

    public void setDownLoadFile(DownLoadFile downLoadFile) {
        mDownLoadFile = downLoadFile;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}
