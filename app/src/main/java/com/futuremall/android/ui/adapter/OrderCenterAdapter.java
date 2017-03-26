package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.OrderProduct;
import com.futuremall.android.ui.ViewHolder.OrderFooterViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderHeadViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderItemViewHolder;
import com.futuremall.android.ui.activity.OrderDetailActivity;
import com.futuremall.android.util.DecimalUtil;
import com.futuremall.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class OrderCenterAdapter extends SectionRecyclerAdapter<RecyclerView.ViewHolder> {



    private List<OrderList> dataList;
    private Context mContext;

    public OrderCenterAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
    }

    @Override
    public int getSectionCount() {
        return dataList.size();
    }

    @Override
    public int getItemCount(int section) {
        OrderList sectionObject = dataList.get(section);

        return sectionObject.getOrder_goods().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, final int section) {

        final OrderList orderList = dataList.get(section);
        OrderHeadViewHolder headerViewHolder = (OrderHeadViewHolder) holder;
        headerViewHolder.mTvShopName.setText(orderList.getShop_name());
        headerViewHolder.mTvOrderExpress_status.setText(orderList.expressStatus(orderList.getState()));
        headerViewHolder.mTvShopName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                OrderDetailActivity.enter(mContext, orderList.getOrder_id());
            }
        });
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        OrderList orderList = dataList.get(section);
        OrderFooterViewHolder footerViewHolder = (OrderFooterViewHolder) holder;
        footerViewHolder.mTvTotalCount.setText(String.format(mContext.getString(R.string.total_count), orderList.getGoods_num()));
        String price = StringUtil.getPrice(mContext, orderList.getGoods_price()).toString();
        String integral = String.format(mContext.getString(R.string.total_integral), orderList.getIntegral());
        footerViewHolder.mTvTotalPrice.setText(price + integral);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int section, final int relativePosition, int absolutePosition) {

        final OrderProduct order = dataList.get(section).getOrder_goods().get(relativePosition);
        OrderItemViewHolder itemViewHolder = (OrderItemViewHolder) holder;
        Glide.with(mContext.getApplicationContext())
                .load(order.getGoods_img())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemViewHolder.mIvProductPic);
        itemViewHolder.mTvProductName.setText(order.getGoods_name());
        String price = String.format(mContext.getString(R.string.price), order.getGoods_price());
        itemViewHolder.mTvProductPrice.setText(StringUtil.getPrice(mContext, price));
        itemViewHolder.mTvCount.setText("x"+String.valueOf(order.getGoods_num()));

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

    public void setData(List<OrderList> data) {
        if(data != null) {
            this.dataList = data;
        }else {
            this.clear();
        }
        this.notifyDataSetChanged();
    }

    private void clear() {
        dataList.clear();
        this.notifyDataSetChanged();
    }
}
