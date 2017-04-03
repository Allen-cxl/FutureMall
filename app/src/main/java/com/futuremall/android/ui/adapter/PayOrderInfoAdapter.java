package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.model.bean.AddressBean;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.ui.ViewHolder.OrderFirstViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderFooterViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderHeadViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderItemViewHolder;

import java.util.ArrayList;
import java.util.List;


public class PayOrderInfoAdapter extends SectionRecyclerAdapter<RecyclerView.ViewHolder> {


    private List<ShoppingCartBean> dataList;
    private Context mContext;
    private OrderFirstViewHolder  mFirstHolder;

    public PayOrderInfoAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
    }

    @Override
    public int getSectionCount() {
        return dataList.size();
    }

    @Override
    public int getItemCount(int section) {
        ShoppingCartBean sectionObject = dataList.get(section);

        return sectionObject.getCart_goods().size();
    }

    @Override
    public void onBindFirstViewHolder(RecyclerView.ViewHolder holder, final int section) {

        mFirstHolder = (OrderFirstViewHolder) holder;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, final int section) {

        final ShoppingCartBean orderList = dataList.get(section);
        OrderHeadViewHolder headerViewHolder = (OrderHeadViewHolder) holder;
        headerViewHolder.mTvShopName.setText(orderList.getShop_name());
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        ShoppingCartBean orderList = dataList.get(section);
        OrderFooterViewHolder footerViewHolder = (OrderFooterViewHolder) holder;
        footerViewHolder.mTvTotalCount.setText(String.format(mContext.getString(R.string.total_count), orderList.getShop_num()));
        String price = String.format(mContext.getString(R.string.price), orderList.getShop_price());
        footerViewHolder.mTvTotalPrice.setText(String.format(mContext.getString(R.string.amount_price_integral), price));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int section, final int relativePosition, int absolutePosition) {

        final ShoppingCartBean.ShoppingCartProductBean productBean = dataList.get(section).getCart_goods().get(relativePosition);
        OrderItemViewHolder itemViewHolder = (OrderItemViewHolder) holder;
        Glide.with(mContext.getApplicationContext())
                .load(productBean.getGoods_img())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemViewHolder.mIvProductPic);
        itemViewHolder.mTvProductName.setText(productBean.getGoods_name());
        itemViewHolder.mTvCount.setText("x"+String.valueOf(productBean.getGoods_num()));

    }

    @Override
    public boolean isNeedFooter(int sectionPosition) {
        return true;
    }

    @Override
    public boolean setFullSpanItem(int section, int relativePositonInSection) {

        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder myHolder = null;
        switch (viewType) {

            case SectionRecyclerAdapter.VIEW_TYPE_FIRST:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_pay_order_head, parent, false);
                myHolder = new OrderFirstViewHolder(view);
                break;

            case SectionRecyclerAdapter.VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_order_center_head, parent, false);
                myHolder = new OrderHeadViewHolder(view);
                break;

            case SectionRecyclerAdapter.VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_order_center, parent, false);
                myHolder = new OrderItemViewHolder(view);
                break;

            case SectionRecyclerAdapter.VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_order_center_foot, parent, false);
                myHolder = new OrderFooterViewHolder(view);
                break;
        }

        return myHolder;
    }

    public void setData(List<ShoppingCartBean> data) {
        if(data != null) {
            this.dataList = data;
        }else {
            this.clear();
        }
        this.notifyDataSetChanged();
    }

    public void setFirstViewData(AddressBean data) {

        if(null != data && null != mFirstHolder){
            mFirstHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mFirstHolder.mTvUserName.setText(data.getConsignee());
            mFirstHolder.mTvPhone.setText(data.getMobile());
            mFirstHolder.mTvAddress.setText(data.getAddress());
            this.notifyDataSetChanged();
        }
    }

    private void clear() {
        dataList.clear();
        this.notifyDataSetChanged();
    }
}
