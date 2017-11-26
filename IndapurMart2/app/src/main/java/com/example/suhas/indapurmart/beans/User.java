package com.example.suhas.indapurmart.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class User implements Parcelable{

    private String _id;
    private String userID;
    private String enUserName;
    private String marUserName;
    private String enShopName;
    private String marShopName;
    private String mobileNo;
    private String address;
    private String shopTiming;
    private UserCategory category;
    private Village village;
    private String isActive;
    private String isVarified;
    private String isPublished;

    public User() {
    }

    protected User(Parcel in) {
        _id = in.readString();
        userID = in.readString();
        enUserName = in.readString();
        marUserName = in.readString();
        enShopName = in.readString();
        marShopName = in.readString();
        mobileNo = in.readString();
        address = in.readString();
        shopTiming = in.readString();
        isActive = in.readString();
        isVarified = in.readString();
        isPublished = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEnUserName() {
        return enUserName;
    }

    public void setEnUserName(String enUserName) {
        this.enUserName = enUserName;
    }

    public String getMarUserName() {
        return marUserName;
    }

    public void setMarUserName(String marUserName) {
        this.marUserName = marUserName;
    }

    public String getEnShopName() {
        return enShopName;
    }

    public void setEnShopName(String enShopName) {
        this.enShopName = enShopName;
    }

    public String getMarShopName() {
        return marShopName;
    }

    public void setMarShopName(String marShopName) {
        this.marShopName = marShopName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopTiming() {
        return shopTiming;
    }

    public void setShopTiming(String shopTiming) {
        this.shopTiming = shopTiming;
    }

    public UserCategory getCategory() {
        return category;
    }

    public void setCategory(UserCategory category) {
        this.category = category;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsVarified() {
        return isVarified;
    }

    public void setIsVarified(String isVarified) {
        this.isVarified = isVarified;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "_id='" + _id + '\'' +
                ", userID='" + userID + '\'' +
                ", enUserName='" + enUserName + '\'' +
                ", marUserName='" + marUserName + '\'' +
                ", enShopName='" + enShopName + '\'' +
                ", marShopName='" + marShopName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", address='" + address + '\'' +
                ", shopTiming='" + shopTiming + '\'' +
                ", category=" + category +
                ", village=" + village +
                ", isActive='" + isActive + '\'' +
                ", isVarified='" + isVarified + '\'' +
                ", isPublished='" + isPublished + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(userID);
        parcel.writeString(enUserName);
        parcel.writeString(marUserName);
        parcel.writeString(enShopName);
        parcel.writeString(marShopName);
        parcel.writeString(mobileNo);
        parcel.writeString(address);
        parcel.writeString(shopTiming);
        parcel.writeString(isActive);
        parcel.writeString(isVarified);
        parcel.writeString(isPublished);
    }
}
