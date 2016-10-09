package com.setsuna.cloudapp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylib.util.EdtUtil;
import com.setsuna.cloudapp.R;

/**
 * Created by setsuna on 2016/9/27.
 */
public class SearchDig extends Dialog {
    private EditText mEdtSearchDig;
    private Button mBtCancelSearchDig;
    private Button mBtCreateSearchDig;
    private OnSearchConfirm mOnSearchConfirm;

    public void setOnSearchConfirm(OnSearchConfirm onSearchConfirm) {
        mOnSearchConfirm = onSearchConfirm;
    }

    public SearchDig(final Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.search_dig);
        mEdtSearchDig = (EditText) findViewById(R.id.edt_search_dig);
        mBtCancelSearchDig = (Button) findViewById(R.id.bt_cancel_search_dig);
        mBtCreateSearchDig = (Button) findViewById(R.id.bt_create_search_dig);

        mBtCancelSearchDig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDig.this.cancel();
            }
        });
        mBtCreateSearchDig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdtUtil.isEdtEmpty(mEdtSearchDig)){
                    Toast.makeText(context, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mOnSearchConfirm.onSearch(EdtUtil.getEdtText(mEdtSearchDig));

                SearchDig.this.cancel();
            }
        });
    }


    public interface  OnSearchConfirm{
        public void onSearch(String fileName);
    }

}
