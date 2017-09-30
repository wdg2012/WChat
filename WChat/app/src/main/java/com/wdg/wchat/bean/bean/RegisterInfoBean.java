package com.wdg.wchat.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */

public class RegisterInfoBean implements Parcelable {
    private String mHeadPhoto;
    private String mPhone;
    private String mPassword;
    private String mCountry;
    private String mNick;
    private String verCode;
    private File mFile;

    public File getFile() {
        return mFile;
    }

    public void setFile(final File file) {
        this.mFile = file;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(final String verCode) {
        this.verCode = verCode;
    }

    public String getHeadPhoto() {
        return mHeadPhoto;
    }

    public void setHeadPhoto(final String headPhoto) {
        mHeadPhoto = headPhoto;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(final String phone) {
        mPhone = phone;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(final String password) {
        mPassword = password;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(final String country) {
        mCountry = country;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(final String nick) {
        mNick = nick;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHeadPhoto);
        dest.writeString(this.mPhone);
        dest.writeString(this.mPassword);
        dest.writeString(this.mCountry);
        dest.writeString(this.mNick);
        dest.writeString(this.verCode);
    }

    public RegisterInfoBean() {
    }

    protected RegisterInfoBean(Parcel in) {
        this.mHeadPhoto = in.readString();
        this.mPhone = in.readString();
        this.mPassword = in.readString();
        this.mCountry = in.readString();
        this.mNick = in.readString();
        this.verCode = in.readString();
    }

    public static final Parcelable.Creator<RegisterInfoBean> CREATOR = new Parcelable.Creator<RegisterInfoBean>() {
        public RegisterInfoBean createFromParcel(Parcel source) {
            return new RegisterInfoBean(source);
        }

        public RegisterInfoBean[] newArray(int size) {
            return new RegisterInfoBean[size];
        }
    };
}
