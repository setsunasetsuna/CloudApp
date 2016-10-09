package com.setsuna.cloudapp.share;

import android.content.Context;

import com.example.mylib.util.SharedPrefrencesUtil;

/**
 * Created by setsuna on 2016/10/4.
 */
public class ShareLogState {
    private boolean logState;
    private Context mContext;

    public ShareLogState(Context context) {
        mContext = context;
    }

    public void setLogState(boolean logState){
        SharedPrefrencesUtil.saveData(mContext,"logState","state",logState);
    }
    public boolean getLogState(){
        return SharedPrefrencesUtil.getData(mContext,"logState","state",false);
    }
}
