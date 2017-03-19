package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderList implements Parcelable {

    private String shop_id;
    private String shop_name;
    private String order_id;
    private String goods_num;
    private String goods_price;
    private int state;
    private static int NOSEND = 0; //0待发货
    private static int ACCEPT; //1待收货
    private static int ACCEPTED; //2已收货
    private int integral;
    private List<OrderProduct> order_goods;


    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public List<OrderProduct> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderProduct> order_goods) {
        this.order_goods = order_goods;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.order_id);
        dest.writeString(this.goods_num);
        dest.writeString(this.goods_price);
        dest.writeInt(this.state);
        dest.writeInt(this.integral);
        dest.writeList(this.order_goods);
    }

    public OrderList() {
    }

    protected OrderList(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.order_id = in.readString();
        this.goods_num = in.readString();
        this.goods_price = in.readString();
        this.state = in.readInt();
        this.integral = in.readInt();
        this.order_goods = new ArrayList<OrderProduct>();
        in.readList(this.order_goods, OrderProduct.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderList> CREATOR = new Parcelable.Creator<OrderList>() {
        @Override
        public OrderList createFromParcel(Parcel source) {
            return new OrderList(source);
        }

        @Override
        public OrderList[] newArray(int size) {
            return new OrderList[size];
        }
    };

    public String expressStatus(int state){

        if(state == NOSEND){
            return "待发货";
        }else if(state == ACCEPT){
            return "待收货";
        }else if(state == ACCEPTED){
            return "已收货";
        }

        return "";
    }
}
