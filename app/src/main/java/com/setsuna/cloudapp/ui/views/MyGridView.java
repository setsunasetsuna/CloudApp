package com.setsuna.cloudapp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by setsuna on 2016/9/30.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);

    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
//        int expandSpec =( MyGridView.this.getHeight()- DensityUtil.dip2px(50))/3;
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
