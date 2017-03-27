package com.futuremall.android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.ui.listener.OnShopCartChangeListener;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Allen on 2017/3/7.
 * dialog
 */

public class AddReduceDialogFragment extends AppCompatDialogFragment implements
        View.OnClickListener,
        DialogInterface.OnDismissListener,
        TextWatcher {

    @BindView(R.id.tv_reduce)
    TextView mTvReduce;
    @BindView(R.id.et_count)
    EditText mEtCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.bt_cancel)
    Button mBtCancel;
    @BindView(R.id.bt_sure)
    Button mBtSure;
    private OnShopCartChangeListener mChangeListener;
    private Context mContext;
    private String mID;
    private String mCount;
    private View mView;
    private int mType = -1;
    private Unbinder mBinder;

    public AddReduceDialogFragment() {

    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setListener(OnShopCartChangeListener listener) {
        this.mChangeListener = listener;
    }

    public void setID(String ID) {
        this.mID = ID;
    }

    public void setCount(String count) {
        this.mCount = count;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_reduce, null);
        mBinder = ButterKnife.bind(this, mView);
        initData();
        return mView;
    }

    private void initData() {

        mEtCount.setText(mCount);
        mTvAdd.setOnClickListener(this);
        mEtCount.addTextChangedListener(this);
        mTvReduce.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
        mBtSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                mCount = mEtCount.getText().toString().trim();
                mCount = ShoppingCartHepler.addOrReduceGoodsNum(true, mCount, mView);
                mEtCount.setText(mCount);
                mType = ShoppingCartHepler.TYPE_ADD;
                break;

            case R.id.tv_reduce:
                mCount = mEtCount.getText().toString().trim();
                mCount = ShoppingCartHepler.addOrReduceGoodsNum(false, mCount, mView);
                mEtCount.setText(mCount);
                mType = ShoppingCartHepler.TYPE_REDUCE;
                break;

            case R.id.bt_cancel:
                dismiss();
                break;

            case R.id.bt_sure:
                mChangeListener.onDataChange(mID, mCount);
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        SystemUtil.hideKeyboard((Activity) mContext);
        mBinder.unbind();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        if (StringUtil.isStartFromZero(str)) {
            String newStr = str.replaceAll("^(0+)", "1");
            mEtCount.setText(newStr);
        }
        mEtCount.setSelection(mEtCount.getText().toString().length());
    }

    public static class Builder {

        private Context mContext;
        private OnShopCartChangeListener mChangeListener;
        private String mID;
        private String mCount;

        public Builder context(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder listener(OnShopCartChangeListener listener) {
            this.mChangeListener = listener;
            return this;
        }

        public Builder ID(String ID) {
            this.mID = ID;
            return this;
        }

        public Builder count(String count) {
            this.mCount = count;
            return this;
        }

        public AddReduceDialogFragment build() {
            AddReduceDialogFragment dialog = new AddReduceDialogFragment();
            dialog.setContext(mContext);
            dialog.setListener(mChangeListener);
            dialog.setCount(mCount);
            dialog.setID(mID);
            return dialog;
        }
    }

}
