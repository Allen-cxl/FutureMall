package com.futuremall.android.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.futuremall.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShoppingCartGroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cb_shop_name)
    public CheckBox mCheckBoxShopName;

    public ShoppingCartGroupViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);

    }

}
