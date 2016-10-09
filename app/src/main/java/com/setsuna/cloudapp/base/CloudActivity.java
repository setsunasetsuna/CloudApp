package com.setsuna.cloudapp.base;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.mylib.base.BaseActivity;
import com.example.mylib.net.JsonGet;
import com.setsuna.cloudapp.share.ShareCookie;

/**
 * Created by setsuna on 2016/9/19.
 */
public abstract class CloudActivity extends BaseActivity {
    private ShareCookie mShareCookie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleCenter("迷之云盘");
        setTitleLeft("返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mSelf, "返回", Toast.LENGTH_SHORT).show();
                killSelf();
            }
        });

    }

    public void intiCookie(JsonGet jsonGet){
        if (mShareCookie==null){
            mShareCookie=new ShareCookie(mSelf);
            jsonGet.addCookie("fuid",mShareCookie.getFuid());
            jsonGet.addCookie("token",mShareCookie.getToken());

        }
    }
}
