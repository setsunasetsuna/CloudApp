package com.setsuna.cloudapp.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {
    private DownloadThread[] mDownloadThread=new DownloadThread[DownloadConfig.DOWNLOAD_THREAD_COUNT];
    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (int i=0;i<DownloadConfig.DOWNLOAD_THREAD_COUNT;i++){
            if (mDownloadThread[i]==null||!mDownloadThread[i].isAlive()){
                mDownloadThread[i]=new DownloadThread();
                mDownloadThread[i].start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
