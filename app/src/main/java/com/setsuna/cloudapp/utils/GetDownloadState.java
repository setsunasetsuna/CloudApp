package com.setsuna.cloudapp.utils;

import com.setsuna.cloudapp.download.DownloadState;

/**
 * Created by setsuna on 2016/9/28.
 */
public class GetDownloadState {
    public static  String getMsg(int state){
        switch (state){
            case DownloadState.STATE_WAITING:

                return "请稍等，正在排队";
            case DownloadState.STATE_PREPARING:

                return "任务准备中.......";
            case DownloadState.STATE_PAUSE:

                return "已暂停";
            case DownloadState.STATE_CANCLE:

                return "已取消";
            case DownloadState.STATE_SHA1CHECKING:

                return "正在做最后校验......";
            case DownloadState.STATE_NETERROR:

                return "你的网好渣呦";
            case DownloadState.STATE_SDERROR:

                return "手机存储不足";
            case DownloadState.STATE_SERVER:

                return "你的文件被外星人偷走了";
            case DownloadState.STATE_SHA1ERROR:

                return "校验失败，重新下载";

        }
        return "";
    }
}
