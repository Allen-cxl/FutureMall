package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderFirstViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_layout)
    public LinearLayout mLayout;
    @BindView(R.id.tv_user_name)
    public TextView mTvUserName;
    @BindView(R.id.tv_phone)
    public TextView mTvPhone;
    @BindView(R.id.tv_address)
    public TextView mTvAddress;

    public OrderFirstViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);

    }

}
