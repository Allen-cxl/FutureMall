package com.futuremall.android.model.bean;

import java.util.List;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderList {

    private String orderID;
    private String shopName;
    private int orderStatus;
    private int expressStatus;
    private int integral;
    private List<OrderProduct> data;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(int expressStatus) {
        this.expressStatus = expressStatus;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public List<OrderProduct> getData() {
        return data;
    }

    public void setData(List<OrderProduct> data) {
        this.data = data;
    }


}
