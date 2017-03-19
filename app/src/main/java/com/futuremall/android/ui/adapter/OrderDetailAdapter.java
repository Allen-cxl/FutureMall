package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderProduct;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<OrderProduct> mOrderProducts;
    private OrderDetail mOrderDetail;
    private final static int VIEW_TYPE_HEAD = 0x1;
    private final static int VIEW_TYPE_NORMAL = 0x2;
    private final static int VIEW_TYPE_FOOT = 0x3;

    public OrderDetailAdapter(Context context){
        this.mContext = context;
        mOrderProducts = new ArrayList<>();
    }

    public void setData(OrderDetail orderDetail) {

        if(orderDetail != null){
            mOrderDetail = orderDetail;
            mOrderProducts = orderDetail.getOrder_goods();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v;
        switch (viewType){

            case VIEW_TYPE_HEAD:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_detail_head, parent, false);
                return new OrderDetailHeadHolder(v);
            case VIEW_TYPE_NORMAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_center, parent, false);
                return new OrderDetailAdapter.OrderDetailViewHolder(v);
            case VIEW_TYPE_FOOT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_detail_foot, parent, false);
                return new OrderDetailFootHolder(v);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) ==VIEW_TYPE_HEAD){
            intValueHeadView((OrderDetailHeadHolder)(holder), mOrderDetail);
            return;
        }
        if(getItemViewType(position) ==VIEW_TYPE_FOOT){
            intValueFootView((OrderDetailFootHolder)(holder), mOrderDetail);
            return;
        }

        final OrderProduct order = mOrderProducts.get(position-1);
        OrderDetailViewHolder itemViewHolder = (OrderDetailViewHolder) holder;
        Glide.with(mContext.getApplicationContext())
                .load(order.getGoods_img())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemViewHolder.mIvProductPic);
        itemViewHolder.mTvProductName.setText(order.getGoods_name());
        String price = String.format(mContext.getString(R.string.price), order.getGoods_price());
        itemViewHolder.mTvProductPrice.setText(StringUtil.getPrice(mContext, price));
        itemViewHolder.mTvCount.setText(String.format(mContext.getString(R.string.count_format), String.valueOf(order.getGoods_num())));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_HEAD;
        }
        if(position == mOrderProducts.size()+1){
            return VIEW_TYPE_FOOT;
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mOrderProducts.size()+2;
    }

    private void intValueHeadView(OrderDetailHeadHolder viewHolder, OrderDetail orderDetail){

        viewHolder.mTvOrderNo.setText(String.format(mContext.getString(R.string.order_no),orderDetail.getOrder_sn()));
        viewHolder.mTvOrderDate.setText(String.format(mContext.getString(R.string.order_data), orderDetail.getAdd_time()));
        viewHolder.mTvUserName.setText(orderDetail.getUser_name() +"   "+ orderDetail.getMobile_phone());
        viewHolder.mTvAddress.setText(orderDetail.getAddress());
        viewHolder.mTvOrderStatus.setText(orderDetail.expressStatus(orderDetail.getState()));
        viewHolder.mTvShopName.setText(orderDetail.getShop_name());
    }

    private void intValueFootView(final OrderDetailFootHolder viewHolder, OrderDetail orderDetail){

        viewHolder.mTvTotalCount.setText(String.format(mContext.getString(R.string.total_count), orderDetail.getGoods_num()));
        String price = StringUtil.getPrice(mContext, orderDetail.getGoods_price()+"").toString();
        String integral = String.format(mContext.getString(R.string.total_integral), orderDetail.getIntegral());
        viewHolder.mTvTotalPrice.setText(price + integral);

        viewHolder.mTvExpressType.setText(String.format(mContext.getString(R.string.express_type), orderDetail.getShipping_name()));
        viewHolder.mTvExpressNo.setText(String.format(mContext.getString(R.string.express_no), orderDetail.getInvoice_no()));
        viewHolder.mLayoutExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackbarUtil.show(viewHolder.itemView,"物流信息");
            }
        });

    }

    static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_pic)
        ImageView mIvProductPic;
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_product_price)
        TextView mTvProductPrice;
        @BindView(R.id.tv_count)
        TextView mTvCount;

        OrderDetailViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }

    static class OrderDetailHeadHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_orderNo)
        TextView mTvOrderNo;
        @BindView(R.id.tv_orderStatus)
        TextView mTvOrderStatus;
        @BindView(R.id.tv_date)
        TextView mTvOrderDate;
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;

        OrderDetailHeadHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }

    static class OrderDetailFootHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_total_count)
        TextView mTvTotalCount;
        @BindView(R.id.tv_total_price)
        TextView mTvTotalPrice;
        @BindView(R.id.tv_express_type)
        TextView mTvExpressType;
        @BindView(R.id.tv_express_no)
        TextView mTvExpressNo;
        @BindView(R.id.layout_express)
        LinearLayout mLayoutExpress;

        OrderDetailFootHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
