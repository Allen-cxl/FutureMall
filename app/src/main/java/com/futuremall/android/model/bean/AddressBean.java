package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PVer on 2017/4/2.
 */

public class AddressBean implements Parcelable {

    private String address;
    private String address_id;
    private String consignee;
    private String mobile;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.address_id);
        dest.writeString(this.consignee);
        dest.writeString(this.mobile);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.address = in.readString();
        this.address_id = in.readString();
        this.consignee = in.readString();
        this.mobile = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
