package com.futuremall.android.ui.listener;

import com.futuremall.android.model.bean.ShoppingCartBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public interface OnShopCartChangeListener {

    void onDataChange(String id, String count, int type);

    void onTotalPrice(List<ShoppingCartBean> list);

    void onSelectItem(boolean isSelectedAll);
}
