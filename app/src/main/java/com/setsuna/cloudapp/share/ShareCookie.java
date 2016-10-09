package com.setsuna.cloudapp.share;

import android.content.Context;

import com.example.mylib.util.SharedPrefrencesUtil;

/**
 * Created by setsuna on 2016/9/26.
 */
public class ShareCookie {
    private Context mContext;
    public ShareCookie(Context context) {
        mContext=context;
    }



    public void saveFuid(String fuid){
        SharedPrefrencesUtil.saveData(mContext,"cookie","fuid",fuid);
    }
    public String getFuid(){
        return SharedPrefrencesUtil.getData(mContext,"cookie","fuid","shj");
    }
    public void saveToken(String token){
        SharedPrefrencesUtil.saveData(mContext,"cookie","token",token);
    }
    public String getToken(){
        return SharedPrefrencesUtil.getData(mContext,"cookie","token","shj");
    }

}
