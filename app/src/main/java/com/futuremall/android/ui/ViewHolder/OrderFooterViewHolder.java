package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_total_count)
    public TextView mTvTotalCount;
    @BindView(R.id.tv_total_price)
    public TextView mTvTotalPrice;

    public OrderFooterViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);
    }
}
