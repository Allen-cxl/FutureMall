package com.futuremall.android.model.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class OrderProduct implements Parcelable {

    private String goods_id;
    private String goods_name;
    private String goods_img;
    private float goods_price;
    private int goods_num;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_id);
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_img);
        dest.writeFloat(this.goods_price);
        dest.writeInt(this.goods_num);
    }

    public OrderProduct() {
    }

    protected OrderProduct(Parcel in) {
        this.goods_id = in.readString();
        this.goods_name = in.readString();
        this.goods_img = in.readString();
        this.goods_price = in.readFloat();
        this.goods_num = in.readInt();
    }

    public static final Parcelable.Creator<OrderProduct> CREATOR = new Parcelable.Creator<OrderProduct>() {
        @Override
        public OrderProduct createFromParcel(Parcel source) {
            return new OrderProduct(source);
        }

        @Override
        public OrderProduct[] newArray(int size) {
            return new OrderProduct[size];
        }
    };
}
