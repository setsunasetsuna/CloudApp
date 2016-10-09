package com.setsuna.cloudapp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.entity.FileEntity;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by setsuna on 2016/9/26.
 */
public class NaviBar extends HorizontalScrollView implements View.OnClickListener {
    private RelativeLayout mRelativeLayout;
    private LayoutInflater mLayoutInflater;
    private List<TextView> mTextViews=new ArrayList<>();
    private TextView textView;
    private List<FileEntity.PathBean> mBeanListold;
    private OnNaviBarClickListenner mOnNaviBarClickListenner;
    public NaviBar(Context context) {
        super(context);

    }
public void setOnNaviBarClickListenner(OnNaviBarClickListenner onNaviBarClickListenner){
    mOnNaviBarClickListenner=onNaviBarClickListenner;
}
    public NaviBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRelativeLayout=new RelativeLayout(context);
        mLayoutInflater=LayoutInflater.from(context);
        mRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mRelativeLayout);
        this.setHorizontalScrollBarEnabled(false);

    }

    public void addItem(FileEntity.PathBean pathBean){
        textView= (TextView) mLayoutInflater.inflate(R.layout.item_tv_navibar,mRelativeLayout,false);
        mRelativeLayout.addView(textView,0);
        textView.setText(pathBean.getName());
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) textView.getLayoutParams();
        if (mTextViews.size()>0){
            layoutParams.leftMargin=mTextViews.get(mTextViews.size()-1).getRight()- DensityUtil.dip2px(30);
        }else {
            layoutParams.leftMargin=0-DensityUtil.dip2px(20);
        }
        textView.setLayoutParams(layoutParams);
        textView.setTag(pathBean);
        textView.setOnClickListener(this);
        mTextViews.add(textView);

        textView.post(new Runnable() {
            @Override
            public void run() {
                NaviBar.this.smoothScrollBy(textView.getRight(),0);
            }
        });
    }

    public void changeItem(List<FileEntity.PathBean> pathBeanListNew){
        if (mBeanListold==null){
            addItem(pathBeanListNew.get(pathBeanListNew.size()-1));
            mBeanListold=pathBeanListNew;
            return;
        }
        int cha=mBeanListold.size()-pathBeanListNew.size();
        if (cha<0){
            addItem(pathBeanListNew.get(pathBeanListNew.size()-1));
        }else {
            removeItem(cha);
        }
        mBeanListold=pathBeanListNew;
    }

    public void removeItem(int cha){
        mRelativeLayout.removeViews(0,cha);
        int size=mTextViews.size();
        for (int i=size-1;i>size-cha;i--){
            mTextViews.remove(i);
        }
    }

    public void searchItem(String keyword) {
        textView = (TextView) mLayoutInflater.inflate(R.layout.item_tv_navibar, mRelativeLayout, false);
        mRelativeLayout.addView(textView, 0);
        textView.setText("搜索 ："+keyword);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        if (mTextViews.size() > 0) {
            layoutParams.leftMargin = mTextViews.get(mTextViews.size() - 1).getRight() - DensityUtil.dip2px(30);
        } else {
            layoutParams.leftMargin = 0 - DensityUtil.dip2px(20);
        }
        textView.setLayoutParams(layoutParams);

        mTextViews.add(textView);
        textView.post(new Runnable() {
            @Override
            public void run() {
                NaviBar.this.smoothScrollBy(textView.getRight(), 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mOnNaviBarClickListenner.onItemClick(v, (FileEntity.PathBean) v.getTag());
    }


    public interface OnNaviBarClickListenner{
        public void onItemClick(View v, FileEntity.PathBean pathEntity);
    }

}
