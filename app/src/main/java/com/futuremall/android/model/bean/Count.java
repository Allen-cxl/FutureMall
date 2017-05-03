package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class Count implements Parcelable {

    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String Keywords) {
        this.num = Keywords;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.num);
    }

    public Count() {
    }

    protected Count(Parcel in) {
        this.num = in.readString();
    }

    public static final Creator<Count> CREATOR = new Creator<Count>() {
        @Override
        public Count createFromParcel(Parcel source) {
            return new Count(source);
        }

        @Override
        public Count[] newArray(int size) {
            return new Count[size];
        }
    };

    @Override
    public String toString() {
        return "HotKeyWord{" +
                "num='" + num + '\'' +
                '}';
    }
}
