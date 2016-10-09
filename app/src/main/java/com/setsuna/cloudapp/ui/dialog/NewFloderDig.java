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
public class NewFloderDig extends Dialog {
    private EditText mEdtNewfloderDig;
    private Button mBtCancelNewfloderDig;
    private Button mBtCreateNewfloderDig;
    private OnFileNameConfirm mOnFileNameConfirm;

    public void setOnFileNameConfirm(OnFileNameConfirm onFileNameConfirm) {
        mOnFileNameConfirm = onFileNameConfirm;
    }

    public NewFloderDig(final Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.newfloder_dig);
        mEdtNewfloderDig = (EditText) findViewById(R.id.edt_newfloder_dig);
        mBtCancelNewfloderDig = (Button) findViewById(R.id.bt_cancel_newfloder_dig);
        mBtCreateNewfloderDig = (Button) findViewById(R.id.bt_create_newfloder_dig);

        mBtCancelNewfloderDig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewFloderDig.this.cancel();
            }
        });
        mBtCreateNewfloderDig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdtUtil.isEdtEmpty(mEdtNewfloderDig)){
                    Toast.makeText(context, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mOnFileNameConfirm.onConfirm(EdtUtil.getEdtText(mEdtNewfloderDig));

                NewFloderDig.this.cancel();
            }
        });
    }


    public interface  OnFileNameConfirm{
        public void onConfirm(String fileName);
    }

}
