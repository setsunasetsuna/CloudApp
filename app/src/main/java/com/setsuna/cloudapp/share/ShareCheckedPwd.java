package com.setsuna.cloudapp.share;

import android.content.Context;

import com.example.mylib.util.SharedPrefrencesUtil;

/**
 * Created by setsuna on 2016/9/20.
 */
public class ShareCheckedPwd {
    private Context mContext;

    public ShareCheckedPwd(Context context) {
        mContext = context;

    }
    public void saveName(String username){
        SharedPrefrencesUtil.saveData(mContext,"userName","name",username);
    }
    public String getName(){
        return SharedPrefrencesUtil.getData(mContext,"userName","name","shj");
    }
    public void savePwd(String userPwd){
        SharedPrefrencesUtil.saveData(mContext,"userPwd","pwd",userPwd);
    }
    public String getPwd(){
        return SharedPrefrencesUtil.getData(mContext,"userPwd","pwd","shj");
    }
    public void saveCheckedState(boolean ischecked){
        SharedPrefrencesUtil.saveData(mContext,"checkbox","ischecked",ischecked);
    }
    public boolean getCheckedState(){
        return SharedPrefrencesUtil.getData(mContext,"checkbox","ischecked",false);
    }
}
