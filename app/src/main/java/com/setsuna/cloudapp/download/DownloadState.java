package com.setsuna.cloudapp.download;

/**
 * Created by setsuna on 2016/9/24.
 */
public class DownloadState {
    public static final int STATE_WAITING=1001;
    public static final int STATE_DOWNLOADING=1002;
    public static final int STATE_PREPARING=1003;
    public static final int STATE_PAUSE=1004;
    public static final int STATE_CANCLE=1005;
    public static final int STATE_SHA1CHECKING=1006;
    public static final int STATE_DONE=1007;
    public static final int STATE_SDERROR=1008;
    public static final int STATE_SHA1ERROR=1009;
    public static final int STATE_NETERROR=1010;
    public static final int STATE_SERVER=1011;
}
