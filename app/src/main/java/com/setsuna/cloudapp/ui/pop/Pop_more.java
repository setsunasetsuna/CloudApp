package com.setsuna.cloudapp.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.setsuna.cloudapp.R;

import org.xutils.common.util.DensityUtil;

/**
 * Created by setsuna on 2016/9/27.
 */
public class Pop_more extends PopupWindow {
    private FrameLayout mFlUploadedDiskFrag;
    private FrameLayout mFlDownloadedDiskFrag;
    private FrameLayout mFlNewfloderDiskFrag;
    private FrameLayout mFlSortDiskFrag;
    private FrameLayout mFlSearchDiskFrag;
    private FrameLayout mFlRefreshDiskFrag;
    private LayoutInflater mLayoutInflater;

    private View.OnClickListener mOnClickListener;
    public void setOnclickListenner(View.OnClickListener onclickListenner){
        mOnClickListener=onclickListenner;
        mFlNewfloderDiskFrag.setOnClickListener(onclickListenner);
        mFlSortDiskFrag.setOnClickListener(onclickListenner);
        mFlSearchDiskFrag.setOnClickListener(onclickListenner);
        mFlRefreshDiskFrag.setOnClickListener(onclickListenner);
        mFlUploadedDiskFrag.setOnClickListener(onclickListenner);
        mFlDownloadedDiskFrag.setOnClickListener(onclickListenner);
    }

    public Pop_more(Context context) {
        super(context);
        mLayoutInflater=LayoutInflater.from(context);
        this.setWidth(DensityUtil.getScreenWidth());
        this.setHeight(DensityUtil.dip2px(121));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        View contentView = mLayoutInflater.inflate(R.layout.pop_more, null);
        this.setContentView(contentView);
        mFlUploadedDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_uploaded_disk_frag);
        mFlDownloadedDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_downloaded_disk_frag);
        mFlNewfloderDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_newfloder_disk_frag);
        mFlSortDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_sort_disk_frag);
        mFlSearchDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_search_disk_frag);
        mFlRefreshDiskFrag = (FrameLayout) contentView.findViewById(R.id.fl_refresh_disk_frag);
    }

}
