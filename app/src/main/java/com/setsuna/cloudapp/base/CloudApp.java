package com.setsuna.cloudapp.base;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mylib.base.BaseApplication;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.download.DownloadConfig;
import com.setsuna.cloudapp.share.ShareLogState;
import com.setsuna.cloudapp.ui.activity.MainActivity;

/**
 * Created by setsuna on 2016/9/19.
 */
public class CloudApp extends BaseApplication {
    public static Context mContext;

    @Override
    public void initOthers() {
        mContext=this;
        if (!DownloadConfig.inti()){
            Toast.makeText(mContext, "DataBase inti error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected int initTitle() {
        return R.layout.title_bar;
    }

    @Override
    protected boolean isDebug() {
        return false;
    }
}
