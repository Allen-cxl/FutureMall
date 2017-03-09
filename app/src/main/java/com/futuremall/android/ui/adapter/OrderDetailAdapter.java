package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.model.bean.OrderProduct;
import com.futuremall.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    protected Context mContext;
    private View mHeadView, mFootView;
    private List<OrderProduct> mOrderProducts;
    public final static int VIEW_TYPE_HEAD = 0x1;
    public final static int VIEW_TYPE_NORMAL = 0x2;
    public final static int VIEW_TYPE_FOOT = 0x3;

    public OrderDetailAdapter(Context context){
        this.mContext = context;
        mOrderProducts = new ArrayList<>();
    }

    public void setData(List<OrderProduct> list) {

        if(list != null){
            mOrderProducts=list;
            notifyDataSetChanged();
        }
    }

    public void addHeadView(View headView){
        mHeadView = headView;
    }

    public void addFootView(View footView){

        mFootView = footView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){

            case VIEW_TYPE_HEAD:
                return new ViewHolder(mHeadView);
            case VIEW_TYPE_NORMAL:
                View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_center, parent, false);
                return new OrderDetailAdapter.OrderDetailViewHolder(v);
            case VIEW_TYPE_FOOT:
                return new ViewHolder(mFootView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) ==VIEW_TYPE_HEAD)return;
        if(getItemViewType(position) ==VIEW_TYPE_FOOT)return;

        final OrderProduct order = mOrderProducts.get(position-1);
        OrderDetailViewHolder itemViewHolder = (OrderDetailViewHolder) holder;
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
        }
    }
}
