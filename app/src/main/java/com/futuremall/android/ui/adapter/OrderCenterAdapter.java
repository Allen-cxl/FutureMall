package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.OrderProduct;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.ui.ViewHolder.OrderFooterViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderHeadViewHolder;
import com.futuremall.android.ui.ViewHolder.OrderItemViewHolder;
import com.futuremall.android.ui.ViewHolder.ShoppingCartChildViewHolder;
import com.futuremall.android.ui.ViewHolder.ShoppingCartGroupViewHolder;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.ui.listener.OnShopCartChangeListener;
import com.futuremall.android.util.DecimalUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.weight.AddReduceDialogFragment;

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

        return sectionObject.getData().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, final int section) {

        OrderList orderList = dataList.get(section);
        OrderHeadViewHolder headerViewHolder = (OrderHeadViewHolder) holder;
        headerViewHolder.mTvShopName.setText(orderList.getShopName());
        headerViewHolder.mTvOrderExpress_status.setText("已发货");
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        OrderList orderList = dataList.get(section);
        OrderFooterViewHolder footerViewHolder = (OrderFooterViewHolder) holder;
        String count = count();
        footerViewHolder.mTvTotalCount.setText(String.format(mContext.getString(R.string.total_count), count));
        String price = StringUtil.getPrice(mContext, price()).toString();
        String integral = String.format(mContext.getString(R.string.total_integral), orderList.getIntegral());
        footerViewHolder.mTvTotalPrice.setText(price + integral);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int section, final int relativePosition, int absolutePosition) {

        final OrderProduct order = dataList.get(section).getData().get(relativePosition);
        OrderItemViewHolder itemViewHolder = (OrderItemViewHolder) holder;
        Glide.with(mContext.getApplicationContext())
                .load(order.getProductPic())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemViewHolder.mIvProductPic);
        itemViewHolder.mTvProductName.setText(order.getProductName());
        String price = String.format(mContext.getString(R.string.price), order.getProductPrice());
        itemViewHolder.mTvProductPrice.setText(StringUtil.getPrice(mContext, price));
        itemViewHolder.mTvCount.setText("x"+String.valueOf(order.getProductCount()));

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

    public List<OrderList> getCurrentList() {
        return dataList;
    }

    public String price() {
        String price = "0";
        for (OrderList orderList:dataList) {
            List<OrderProduct>  list = orderList.getData();
            for (OrderProduct order: list) {
                String tempPrece = String.valueOf(order.getProductPrice());
                String count = String.valueOf(order.getProductCount());
                String priceMultiply = DecimalUtil.multiplyWithScale(tempPrece, count, 2);
                price = DecimalUtil.add(price, priceMultiply);
            }
        }
        return price;
    }

    public String count() {
        int count = 0;
        for (OrderList orderList:dataList) {
            List<OrderProduct>  list = orderList.getData();
            for (OrderProduct order: list) {
                int countTemp = order.getProductCount();
                count = countTemp + count;
            }
        }
        return String.valueOf(count);
    }

    private void clear() {
        dataList.clear();
        this.notifyDataSetChanged();
    }
}
