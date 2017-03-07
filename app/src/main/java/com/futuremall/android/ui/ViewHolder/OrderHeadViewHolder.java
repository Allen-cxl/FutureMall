package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderHeadViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_shop_name)
    public TextView mTvShopName;
    @BindView(R.id.tv_order_express_status)
    public TextView mTvOrderExpress_status;

    public OrderHeadViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);

    }

}
