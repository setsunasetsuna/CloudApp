package com.setsuna.cloudapp.ui.activity;

import android.widget.ListView;

import com.setsuna.cloudapp.ui.adapter.ItemLvDownloadListAdapter;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.base.CloudActivity;
import com.setsuna.cloudapp.download.DownLoadFile;
import com.setsuna.cloudapp.download.DownloadControl;
import com.setsuna.cloudapp.download.DownloadMSG;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class DownloadActivity extends CloudActivity {
    private List<DownLoadFile> mDownLoadFiles;
    private ItemLvDownloadListAdapter mItemLvDownloadListAdapter;
    private ListView mLvDownloadActivity;
    private DownloadControl mDownloadControl;

    @Override
    public int setRootView() {
        return R.layout.activity_download;
    }

    @Override
    public boolean isUseTitle() {
        return true;
    }

    @Override
    public void initViews() {
        setTitleCenter("下载详情");
        mLvDownloadActivity = (ListView) findViewById(R.id.lv_download_activity);

    }

    @Override
    public void initDatas() {
        mDownloadControl = new DownloadControl();
        mDownLoadFiles = mDownloadControl.getAllTaskNotDone();
        mItemLvDownloadListAdapter = new ItemLvDownloadListAdapter(mSelf, mDownLoadFiles);
        mLvDownloadActivity.setAdapter(mItemLvDownloadListAdapter);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDownloadMsg(DownloadMSG downloadMsg) {

        switch (downloadMsg.getChange()) {
            case DownloadMSG.CHANGE_PROGRESS:
                mItemLvDownloadListAdapter.refreshProgress(downloadMsg.getDownLoadFile());
                break;
            case DownloadMSG.CHANGE_SPEED:
                mItemLvDownloadListAdapter.refreshSpeed(downloadMsg.getDownLoadFile());
                break;
            case DownloadMSG.CHANGE_STATE:
                mItemLvDownloadListAdapter.refreshState(downloadMsg.getDownLoadFile());
                break;
            case DownloadMSG.TASK_OVER:
                for (int i = 0; i < mDownLoadFiles.size(); i++) {
                    if (mDownLoadFiles.get(i).getId() == downloadMsg.getDownLoadFile().getId()) {
                        mDownLoadFiles.remove(i);
                        mItemLvDownloadListAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                break;

        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
