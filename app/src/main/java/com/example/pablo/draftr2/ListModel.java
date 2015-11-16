package com.example.pablo.draftr2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by Pablo on 10/17/2015.
 */
public class ListModel implements Parcelable {
    public String cname =null;
    public Integer quantity =null;
    public String color =null;
    public ArrayList<String> types;
    public String manaCost;
    public Integer cmc;
    public String text;

    public ListModel(String text, String cname, Integer quantity, String color, ArrayList<String> types, String manaCost, Integer cmc) {
        this.text = text;
        this.cname = cname;
        this.quantity = quantity;
        this.color = color;
        this.types = types;
        this.manaCost = manaCost;
        this.cmc = cmc;
    }


    protected ListModel(Parcel in) {
        cname = in.readString();
        quantity = in.readByte() == 0x00 ? null : in.readInt();
        color = in.readString();
        if (in.readByte() == 0x01) {
            types = new ArrayList<String>();
            in.readList(types, String.class.getClassLoader());
        } else {
            types = null;
        }
        manaCost = in.readString();
        cmc = in.readByte() == 0x00 ? null : in.readInt();
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        if (quantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(quantity);
        }
        dest.writeString(color);
        if (types == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(types);
        }
        dest.writeString(manaCost);
        if (cmc == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(cmc);
        }
        dest.writeString(text);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListModel> CREATOR = new Parcelable.Creator<ListModel>() {
        @Override
        public ListModel createFromParcel(Parcel in) {
            return new ListModel(in);
        }

        @Override
        public ListModel[] newArray(int size) {
            return new ListModel[size];
        }
    };
}