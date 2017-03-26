package com.futuremall.android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class Tag implements Parcelable {

    private String Keywords;

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String Keywords) {
        this.Keywords = Keywords;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Keywords);
    }

    public Tag() {
    }

    protected Tag(Parcel in) {
        this.Keywords = in.readString();
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public String toString() {
        return "Tag{" +
                "Keywords='" + Keywords + '\'' +
                '}';
    }
}
