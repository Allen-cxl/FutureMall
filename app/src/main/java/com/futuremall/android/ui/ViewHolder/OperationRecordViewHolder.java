package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OperationRecordViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_date)
    public TextView mTvDate;
    @BindView(R.id.tv_reason)
    public TextView mTvReason;
    @BindView(R.id.tv_income_expenses)
    public TextView mTvIncomeExpenses;
    @BindView(R.id.tv_income_integral)
    public TextView mTvIncomeIntegral;
    @BindView(R.id.tv_cumulative_consume)
    public TextView mTvCumulativeConsume;
    @BindView(R.id.tv_promotion_limit)
    public TextView mTvPromotionLimit;
    @BindView(R.id.tv_promotion_give)
    public TextView mTvPromotionGive;

    public OperationRecordViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);

    }
}
