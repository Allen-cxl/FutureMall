package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PVer on 2017/3/18.
 */

public class UserInfo implements Parcelable {

    public static final  int SEX_MAN = 0; //男
    public static final  int SEX_WOMAN = 1; //女

    String user_id;
    String real_name;
    String access_token;
    String user_name;
    String mobile_phone;
    String email;
    int sex;
    String birthday;
    String user_pic;
    float user_money;
    float rebate;
    float payin;
    float highreward;
    float pay_points;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public float getUser_money() {
        return user_money;
    }

    public void setUser_money(float user_money) {
        this.user_money = user_money;
    }

    public float getRebate() {
        return rebate;
    }

    public void setRebate(float rebate) {
        this.rebate = rebate;
    }

    public float getPayin() {
        return payin;
    }

    public void setPayin(float payin) {
        this.payin = payin;
    }

    public float getHighreward() {
        return highreward;
    }

    public void setHighreward(float highreward) {
        this.highreward = highreward;
    }

    public float getPay_points() {
        return pay_points;
    }

    public void setPay_points(float pay_points) {
        this.pay_points = pay_points;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.real_name);
        dest.writeString(this.access_token);
        dest.writeString(this.user_name);
        dest.writeString(this.mobile_phone);
        dest.writeString(this.email);
        dest.writeInt(this.sex);
        dest.writeString(this.birthday);
        dest.writeString(this.user_pic);
        dest.writeFloat(this.user_money);
        dest.writeFloat(this.rebate);
        dest.writeFloat(this.payin);
        dest.writeFloat(this.highreward);
        dest.writeFloat(this.pay_points);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.user_id = in.readString();
        this.real_name = in.readString();
        this.access_token = in.readString();
        this.user_name = in.readString();
        this.mobile_phone = in.readString();
        this.email = in.readString();
        this.sex = in.readInt();
        this.birthday = in.readString();
        this.user_pic = in.readString();
        this.user_money = in.readFloat();
        this.rebate = in.readFloat();
        this.payin = in.readFloat();
        this.highreward = in.readFloat();
        this.pay_points = in.readFloat();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
