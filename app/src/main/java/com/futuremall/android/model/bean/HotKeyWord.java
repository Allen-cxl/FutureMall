package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class HotKeyWord implements Parcelable {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keyword);
    }

    public HotKeyWord() {
    }

    protected HotKeyWord(Parcel in) {
        this.keyword = in.readString();
    }

    public static final Creator<HotKeyWord> CREATOR = new Creator<HotKeyWord>() {
        @Override
        public HotKeyWord createFromParcel(Parcel source) {
            return new HotKeyWord(source);
        }

        @Override
        public HotKeyWord[] newArray(int size) {
            return new HotKeyWord[size];
        }
    };
}
