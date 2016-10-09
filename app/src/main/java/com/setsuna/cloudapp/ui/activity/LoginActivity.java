package com.setsuna.cloudapp.ui.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.mylib.net.JsonGet;
import com.example.mylib.net.NetCallback;
import com.setsuna.cloudapp.Constant;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.base.CloudActivity;
import com.setsuna.cloudapp.entity.LoginEntity;
import com.setsuna.cloudapp.share.ShareCheckedPwd;
import com.setsuna.cloudapp.share.ShareCookie;
import com.setsuna.cloudapp.share.ShareLogState;

public class LoginActivity extends CloudActivity implements View.OnClickListener {

    private EditText mEdtServer;
    private EditText mEdtUsername;
    private EditText mEdtUserpwd;
    private Button mBtShowpwd;
    private CheckBox mCbLogin;
    private ActionProcessButton mBtLogin;
    private ShareCheckedPwd mShareCheckedPwd;
    private String mUserName;
    private String mUserPwd;
    private boolean isChecked=false;
    private JsonGet mJsonGet;
    private boolean isVerrify=false;
    private boolean isHome=false;
    private ShareCookie mShareCookie;
    private ShareLogState mShareLogState;
    private LoginEntity mLoginEntity;
    private int mVerifyState;
    public static final int VERIFY_ING = 120;
    public static final int VERIFY_SUCCESS = 140;
    public static final int VERIFY_FAIL = 150;
    public static final int VERIFY_ERROR = 180;
    @Override
    public int setRootView() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {

        mShareLogState=new ShareLogState(mSelf);
        if (mShareLogState.getLogState()){
            startActivity(new Intent(mSelf, MainActivity.class));
        }

        mEdtServer = (EditText) findViewById(R.id.edt_server);
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtUserpwd = (EditText) findViewById(R.id.edt_userpwd);
        mBtShowpwd = (Button) findViewById(R.id.bt_showpwd);
        mCbLogin = (CheckBox) findViewById(R.id.cb_login);
        mBtLogin = (ActionProcessButton) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);

        mBtLogin.setMode(ActionProcessButton.Mode.ENDLESS);


        mBtShowpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bt_name=mBtShowpwd.getText().toString();
                if ("显示密码".equals(bt_name)){
                    mBtShowpwd.setText("隐藏密码");
                    mEdtUserpwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    mBtShowpwd.setText("显示密码");
                    mEdtUserpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                }
                mEdtUserpwd.setSelection(mEdtUserpwd.getText().length());
            }
        });
    }

    @Override
    public void initDatas() {
        mShareCookie=new ShareCookie(this);
        mShareLogState=new ShareLogState(mSelf);
        mShareCheckedPwd = new ShareCheckedPwd(this);
        isChecked = mShareCheckedPwd.getCheckedState();
        if (isChecked) {
            mEdtUsername.setText(mShareCheckedPwd.getName());
            mEdtUserpwd.setText(mShareCheckedPwd.getPwd());
            mCbLogin.setChecked(isChecked);
        }


    }


    //uid=test&passwd=test
    public class LoginCallBack extends NetCallback<LoginEntity>{

        @Override
        public void success(LoginEntity entity) {
            mLoginEntity=entity;
            if (entity.isState()){
                mVerifyState=VERIFY_SUCCESS;
                if (isHome){
                    return;
                }
                verifySuccess(entity);
            }else {
                mVerifyState=VERIFY_FAIL;
                if (isHome){
                    return;
                }
                verifyFail();
            }



        }

        @Override
        public void fail() {
            mVerifyState=VERIFY_ERROR;
            if (isHome){
                return;
            }
            verifyError();
        }

        @Override
        public void finish() {
            mBtLogin.setEnabled(true);
            isVerrify=false;
        }
    }

    private void verifySuccess(LoginEntity entity){
        mBtLogin.setProgress(100);

        mShareCheckedPwd.saveName(mEdtUsername.getText().toString());
        mShareCheckedPwd.savePwd(mEdtUserpwd.getText().toString());

        mShareCookie.saveFuid(entity.getId());
        mShareCookie.saveToken(entity.getToken());

        mShareLogState.setLogState(true);

        goToActivity(MainActivity.class);

        killSelf();
    }
    private void verifyFail(){
        Toast.makeText(mSelf, "用户或密码错误!", Toast.LENGTH_SHORT).show();
        mBtLogin.setProgress(-1);
        mBtLogin.setText("用户或密码错误");
    }
    private void verifyError(){
        Toast.makeText(mSelf, "网络错误!", Toast.LENGTH_SHORT).show();
        mBtLogin.setProgress(-1);
        mBtLogin.setText("网络错误");
    }


    @Override
    public void onClick(View v) {
        mUserName = mEdtUsername.getText().toString();
        mUserPwd = mEdtUserpwd.getText().toString();

        if (mCbLogin.isChecked()) {
            isChecked = true;
            mShareCheckedPwd.saveCheckedState(true);
        }
        if (TextUtils.isEmpty(mUserName)) {
            Toast.makeText(mSelf, "请输入正确的用户名！", Toast.LENGTH_SHORT).show();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEdtUsername.startAnimation(shake);
            return;
        }
        if (TextUtils.isEmpty(mUserPwd)) {
            Toast.makeText(mSelf, "请输入正确的密码！", Toast.LENGTH_SHORT).show();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEdtUserpwd.startAnimation(shake);
            return;
        }

        mBtLogin.setEnabled(false);
        isVerrify=true;
        mJsonGet = new JsonGet(Constant.LOGIN,new LoginCallBack());
        mJsonGet.addParams("uid",mUserName);
        mJsonGet.addParams("passwd",mUserPwd);
        mJsonGet.excute();

        mBtLogin.setProgress(50);

        mVerifyState=VERIFY_ING;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isHome=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isHome=false;
        switch (mVerifyState){

            case VERIFY_FAIL:
                verifyFail();
            break;
            case VERIFY_ERROR:
                verifyError();
            break;
            case VERIFY_SUCCESS:
                verifySuccess(mLoginEntity);
            break;

        }
    }

    @Override
    public void onBackPressed() {
        if (isVerrify){
            mJsonGet.cancle();
            isVerrify=false;
            return;
        }
        super.onBackPressed();
    }
}
