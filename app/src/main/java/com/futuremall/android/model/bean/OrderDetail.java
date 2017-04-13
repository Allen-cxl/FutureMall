package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderDetail implements Parcelable {

    public static int NOSEND = 0; //0待发货
    public static int ACCEPT = 1; //1待收货
    public static int ACCEPTED = 2; //2已收货

    private String shop_id;
    private String shop_name;
    private String order_id;
    private String order_sn;
    private String goods_num;
    private String goods_price;
    private int state;
    private String add_time;
    private String user_name;
    private String mobile_phone;
    private String address;
    private String shipping_name;
    private String invoice_no;
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

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
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
        dest.writeString(this.order_sn);
        dest.writeString(this.goods_num);
        dest.writeString(this.goods_price);
        dest.writeInt(this.state);
        dest.writeString(this.add_time);
        dest.writeString(this.user_name);
        dest.writeString(this.mobile_phone);
        dest.writeString(this.address);
        dest.writeString(this.shipping_name);
        dest.writeString(this.invoice_no);
        dest.writeTypedList(this.order_goods);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.order_id = in.readString();
        this.order_sn = in.readString();
        this.goods_num = in.readString();
        this.goods_price = in.readString();
        this.state = in.readInt();
        this.add_time = in.readString();
        this.user_name = in.readString();
        this.mobile_phone = in.readString();
        this.address = in.readString();
        this.shipping_name = in.readString();
        this.invoice_no = in.readString();
        this.order_goods = in.createTypedArrayList(OrderProduct.CREATOR);
    }

    public static final Parcelable.Creator<OrderDetail> CREATOR = new Parcelable.Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
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
