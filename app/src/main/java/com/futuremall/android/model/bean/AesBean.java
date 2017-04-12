package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PVer on 2017/4/12.
 */

public class AesBean implements Parcelable {

    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
    }

    public AesBean() {
    }

    protected AesBean(Parcel in) {
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<AesBean> CREATOR = new Parcelable.Creator<AesBean>() {
        @Override
        public AesBean createFromParcel(Parcel source) {
            return new AesBean(source);
        }

        @Override
        public AesBean[] newArray(int size) {
            return new AesBean[size];
        }
    };
}
