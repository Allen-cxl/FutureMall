package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremall.android.R;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.ui.ViewHolder.OperationRecordViewHolder;
import com.futuremall.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class OperationRecordAdapter extends RecyclerView.Adapter<OperationRecordViewHolder> {

    private List<OperationRecordBean> dataList;
    private Context mContext;

    public OperationRecordAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
    }

    public void addMoreDatas(List<OperationRecordBean> datas) {

        if(datas != null) {
            this.dataList.addAll(this.dataList.size(), datas);
            this.notifyItemRangeInserted(this.dataList.size(), datas.size());
        }
    }

    public void addDatas(List<OperationRecordBean> datas) {

        if(datas != null) {
            clear();
            dataList = datas;
            this.notifyDataSetChanged();
        }
    }

    private void clear() {
        dataList.clear();
    }

    @Override
    public OperationRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opreation_record, parent, false);
        return new OperationRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OperationRecordViewHolder holder, int position) {

        OperationRecordBean bean = dataList.get(position);
        holder.mTvDate.setText(bean.getChange_time());
        holder.mTvReason.setText(bean.getChange_desc());
        String money = String.format(mContext.getString(R.string.price), Float.valueOf(bean.getUser_money()));
        StringUtil.setOpreationRecordTextColor(money, holder.mTvIncomeExpenses, mContext);
        StringUtil.setOpreationRecordTextColor(bean.getRebate(), holder.mTvIncomeIntegral, mContext);

        String consume = String.format(mContext.getString(R.string.price), Float.valueOf(bean.getPay_points()));
        StringUtil.setOpreationRecordTextColor(consume, holder.mTvCumulativeConsume, mContext);
        StringUtil.setOpreationRecordTextColor(bean.getHighreward(), holder.mTvPromotionLimit, mContext);
        StringUtil.setOpreationRecordTextColor(bean.getPayin(), holder.mTvPromotionGive, mContext);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
