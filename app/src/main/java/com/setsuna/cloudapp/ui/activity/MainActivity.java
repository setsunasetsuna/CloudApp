package com.setsuna.cloudapp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylib.util.AnimatorString;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.base.CloudActivity;
import com.setsuna.cloudapp.base.CloudFragment;
import com.setsuna.cloudapp.share.ShareLogState;
import com.setsuna.cloudapp.ui.fragment.AlbumFragment;
import com.setsuna.cloudapp.ui.fragment.DiskFragment;
import com.setsuna.cloudapp.ui.fragment.OtherFragment;

public class MainActivity extends CloudActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TextView mTvClouddiskLlMainActivity;
    private TextView mTvAlubmLlMainActivity;
    private TextView mTvOtherLlMainActivity;
    private TextView mTvMaskLlMainActivity;
    private ViewPager mVpMainActivity;
    private ShareLogState mShareLogState;
    private DiskFragment mDiskFragment=new DiskFragment();
    private AlbumFragment mAlbumFragment=new AlbumFragment();
    private OtherFragment mOtherFragment=new OtherFragment();
    private CloudFragment[] mCloudFragments={mDiskFragment,mAlbumFragment,mOtherFragment};
    private String mTextMove;
    private long backTime;
    private ObjectAnimator mObjectAnimator;
    @Override
    public int setRootView() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        mTvClouddiskLlMainActivity = (TextView) findViewById(R.id.tv_clouddisk_ll_main_activity);
        mTvAlubmLlMainActivity = (TextView) findViewById(R.id.tv_album_ll_main_activity);
        mTvOtherLlMainActivity = (TextView) findViewById(R.id.tv_other_ll_main_activity);
        mTvMaskLlMainActivity = (TextView) findViewById(R.id.tv_mask_ll_main_activity);
        mVpMainActivity = (ViewPager) findViewById(R.id.vp_main_activity);

        mVpMainActivity.addOnPageChangeListener(this);
        mVpMainActivity.setOffscreenPageLimit(2);
        mVpMainActivity.setAdapter(new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mCloudFragments[position];
            }

            @Override
            public int getCount() {
                return mCloudFragments.length;
            }
        });

        mTvAlubmLlMainActivity.setOnClickListener(this);
        mTvClouddiskLlMainActivity.setOnClickListener(this);
        mTvMaskLlMainActivity.setOnClickListener(this);
        mTvOtherLlMainActivity.setOnClickListener(this);


        intiAnim();
    }

    private void intiAnim() {
        mObjectAnimator=ObjectAnimator.ofFloat(mTvMaskLlMainActivity, AnimatorString.translationX,0f,0f);
        mObjectAnimator.setDuration(200);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvMaskLlMainActivity.setText(mTextMove);
            }
        });
    }

    @Override
    public boolean isUseTitle() {
        return false;
    }


    @Override
    public void initDatas() {
        mShareLogState=new ShareLogState(mSelf);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                mTvClouddiskLlMainActivity.performClick();
                break;
            case 1:
                mTvAlubmLlMainActivity.performClick();
                break;
            case 2:
                mTvOtherLlMainActivity.performClick();
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        float startX=0;
        float endX=0;
        startX=mTvMaskLlMainActivity.getX();
        endX=v.getX();
        switch (v.getId()){
            case R.id.tv_clouddisk_ll_main_activity:
                mVpMainActivity.setCurrentItem(0);
            break;
            case R.id.tv_album_ll_main_activity:
                mVpMainActivity.setCurrentItem(1);
            break;
            case R.id.tv_other_ll_main_activity:
                mVpMainActivity.setCurrentItem(2);
            break;
        }
        mObjectAnimator.setFloatValues(startX,endX);
        mObjectAnimator.start();
        TextView newText= (TextView) v;
        mTextMove=newText.getText().toString();
    }

    @Override
    public void onBackPressed() {
        if (mDiskFragment.onBackPressed()){
            return;
        }

        backTime=System.currentTimeMillis()-backTime;
        if (backTime>2000){
            Toast.makeText(mSelf, "再按一次退出", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
    public void logout(View view) {
        mShareLogState.setLogState(false);
        killSelf();
    }
}
