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
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.ui.ViewHolder.ShoppingCartChildViewHolder;
import com.futuremall.android.ui.ViewHolder.ShoppingCartGroupViewHolder;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.ui.listener.OnShopCartChangeListener;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.widget.AddReduceDialogFragment;

import java.util.ArrayList;
import java.util.List;



public class ShoppingCarAdapter extends SectionRecyclerAdapter<RecyclerView.ViewHolder> {



    private List<ShoppingCartBean> dataList;
    private Context mContext;
    private OnShopCartChangeListener mChangeListener;
    private boolean isSelectAll = false;
    private boolean isGroupChecked = false;
    private boolean isChildChecked = false;


    public ShoppingCarAdapter( Context context) {
        mContext = context;
        this.dataList = new ArrayList<>();
    }

    public void setOnShopCartChangeListener(OnShopCartChangeListener changeListener) {
        this.mChangeListener = changeListener;
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
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, final int section) {

        ShoppingCartBean sectionObject = dataList.get(section);
        ShoppingCartGroupViewHolder headerViewHolder = (ShoppingCartGroupViewHolder) holder;
        headerViewHolder.mCheckBoxShopName.setText(sectionObject.getShop_name());
        headerViewHolder.mCheckBoxShopName.setChecked(sectionObject.isCheckEd());
        headerViewHolder.mCheckBoxShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = ShoppingCartHepler.selectGroup(dataList, section, isGroupChecked);
                mChangeListener.onSelectItem(isSelectAll);
                mChangeListener.onTotalPrice(dataList);
                notifyDataSetChanged();
            }
        });
        headerViewHolder.mCheckBoxShopName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGroupChecked = isChecked;
            }
        });
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int section, final int relativePosition, int absolutePosition) {

        final ShoppingCartBean.ShoppingCartProductBean productBean = dataList.get(section).getCart_goods().get(relativePosition);

        final ShoppingCartChildViewHolder itemViewHolder = (ShoppingCartChildViewHolder) holder;
        itemViewHolder.mCheckBoxChild.setChecked(productBean.isCheckEd());
        itemViewHolder.mCheckBoxChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = ShoppingCartHepler.selectOne(dataList, section, relativePosition, isChildChecked);
                mChangeListener.onSelectItem(isSelectAll);
                mChangeListener.onTotalPrice(dataList);
                notifyDataSetChanged();
            }
        });
        itemViewHolder.mCheckBoxChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChildChecked = isChecked;
            }
        });
        Glide.with(mContext.getApplicationContext())
                .load(productBean.getGoods_img())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemViewHolder.mIvProductPic);
        itemViewHolder.mTvProductName.setText(productBean.getGoods_name());
        String price = String.format(mContext.getString(R.string.price), productBean.getGoods_price());
        itemViewHolder.mTvProductPrice.setText(StringUtil.getPrice(mContext, price));
        itemViewHolder.mTvCount.setText(String.valueOf(productBean.getGoods_num()));
        itemViewHolder.mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = itemViewHolder.mTvCount.getText().toString().trim();
                String addCount = ShoppingCartHepler.addOrReduceGoodsNum(true, count);

                mChangeListener.onDataChange(productBean.getRec_id(), addCount);
            }
        });
        itemViewHolder.mTvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = itemViewHolder.mTvCount.getText().toString().trim();

                if(Integer.valueOf(count) <= 1){
                    SnackbarUtil.show(itemViewHolder.mTvCount, "商品数量不能小于1！");
                    return;
                }
                String reduceCount = ShoppingCartHepler.addOrReduceGoodsNum(false, count);

                mChangeListener.onDataChange(productBean.getRec_id(), reduceCount);
            }
        });
        itemViewHolder.mTvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String count = itemViewHolder.mTvCount.getText().toString().trim();
                new AddReduceDialogFragment.Builder()
                        .context(mContext)
                        .count(count)
                        .ID(productBean.getRec_id())
                        .listener(mChangeListener)
                        .build()
                        .show(((FragmentActivity) mContext).getSupportFragmentManager(), "AddReduceDialog");
            }
        });
    }

    @Override
    public boolean isNeedFooter(int sectionPosition) {
        return false;
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
                        .inflate(R.layout.item_shoppingcart_group, parent, false);
                myHolder = new ShoppingCartGroupViewHolder(view);
                break;

            case SectionRecyclerAdapter.VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_shoppingcart_child, parent, false);
                myHolder = new ShoppingCartChildViewHolder(view);
                break;
        }

        return myHolder;
    }

    public void setData(List<ShoppingCartBean> data) {
        if(data != null) {
            this.dataList = data;
        }else {
            this.dataList.clear();
        }
        this.notifyDataSetChanged();
    }

    public List<ShoppingCartBean> getCurrentList() {
        return dataList;
    }

    private void clear() {
        dataList.clear();
        this.notifyDataSetChanged();
    }
}
