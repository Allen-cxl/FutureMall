package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PVer on 2017/3/19.
 */

public class BalanceBean implements Parcelable {

    String  user_money;
    String  pay_ratio;
    int     pay_pass;
    int       is_pay;

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getPay_ratio() {
        return pay_ratio;
    }

    public void setPay_ratio(String pay_ratio) {
        this.pay_ratio = pay_ratio;
    }

    public int getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(int pay_pass) {
        this.pay_pass = pay_pass;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_money);
        dest.writeString(this.pay_ratio);
        dest.writeInt(this.pay_pass);
        dest.writeInt(this.is_pay);
    }

    public BalanceBean() {
    }

    protected BalanceBean(Parcel in) {
        this.user_money = in.readString();
        this.pay_ratio = in.readString();
        this.pay_pass = in.readInt();
        this.is_pay = in.readInt();
    }

    public static final Parcelable.Creator<BalanceBean> CREATOR = new Parcelable.Creator<BalanceBean>() {
        @Override
        public BalanceBean createFromParcel(Parcel source) {
            return new BalanceBean(source);
        }

        @Override
        public BalanceBean[] newArray(int size) {
            return new BalanceBean[size];
        }
    };
}
