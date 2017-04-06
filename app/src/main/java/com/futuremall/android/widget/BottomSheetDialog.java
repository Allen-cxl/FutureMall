package com.futuremall.android.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.ui.listener.OnTextDialogListener;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.SystemUtil;


public class BottomSheetDialog extends Dialog implements DialogInterface.OnDismissListener {

    private EditText mEtPassWord;
    private TextView mTvCancel, mTvSure;
    private Context mContext;
    private OnTextDialogListener mListener;

    public BottomSheetDialog(Context context, int themeResId, OnTextDialogListener listener) {
        super(context, themeResId);
        mContext = context;
        mListener = listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_sheet);

        setView();
    }

    private void setView(){

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.BottomSheetDialogStyle);

        mEtPassWord = (EditText) findViewById(R.id.et_password);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  paw = mEtPassWord.getText().toString().trim();
                if(StringUtil.isEmpty(paw)){
                    SnackbarUtil.show(mTvSure, mContext.getString(R.string.enter_password));
                }else{
                    mListener.onText(paw);
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        SystemUtil.hideKeyboard((Activity) mContext);
    }
}
