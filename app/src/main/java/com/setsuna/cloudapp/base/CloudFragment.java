package com.setsuna.cloudapp.base;

import com.example.mylib.base.BaseFragment;
import com.example.mylib.net.JsonGet;
import com.setsuna.cloudapp.share.ShareCookie;

/**
 * Created by setsuna on 2016/9/19.
 */
public abstract class CloudFragment extends BaseFragment {
    private ShareCookie mShareCookie;
    public void intiCookie(JsonGet jsonGet){
        if (mShareCookie==null){
            mShareCookie=new ShareCookie(mActivity);
            jsonGet.addCookie("fuid",mShareCookie.getFuid());
            jsonGet.addCookie("token",mShareCookie.getToken());

        }
    }
}
