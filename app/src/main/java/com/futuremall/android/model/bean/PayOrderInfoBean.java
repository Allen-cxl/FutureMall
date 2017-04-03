package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/4/2.
 */

public class PayOrderInfoBean implements Parcelable {

    private float total_price;

    private AddressBean address;
    private List<ShoppingCartBean> cart;

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<ShoppingCartBean> getCart() {
        return cart;
    }

    public void setCart(List<ShoppingCartBean> cart) {
        this.cart = cart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.total_price);
        dest.writeParcelable(this.address, flags);
        dest.writeList(this.cart);
    }

    public PayOrderInfoBean() {
    }

    protected PayOrderInfoBean(Parcel in) {
        this.total_price = in.readFloat();
        this.address = in.readParcelable(AddressBean.class.getClassLoader());
        this.cart = new ArrayList<ShoppingCartBean>();
        in.readList(this.cart, ShoppingCartBean.class.getClassLoader());
    }

    public static final Creator<PayOrderInfoBean> CREATOR = new Creator<PayOrderInfoBean>() {
        @Override
        public PayOrderInfoBean createFromParcel(Parcel source) {
            return new PayOrderInfoBean(source);
        }

        @Override
        public PayOrderInfoBean[] newArray(int size) {
            return new PayOrderInfoBean[size];
        }
    };
}
