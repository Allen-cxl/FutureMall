package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderItemViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.iv_product_pic)
    public ImageView mIvProductPic;
    @BindView(R.id.tv_product_name)
    public TextView mTvProductName;
    @BindView(R.id.tv_product_price)
    public TextView mTvProductPrice;
    @BindView(R.id.tv_count)
    public TextView mTvCount;

    public OrderItemViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);

    }
}
