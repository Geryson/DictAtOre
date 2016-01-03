package com.ekfej.dictatore.Presenter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lloyd on 2015. 11. 27..
 */
public class Language implements Parcelable {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
         this.id = id;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public Language(String name) {
        this.name = name;
    }

    public Language(int id, String name) {
        this(name);
        this.id = id;
    }


    @Override
    public String toString() {
        return name;
    }


    protected Language(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}
