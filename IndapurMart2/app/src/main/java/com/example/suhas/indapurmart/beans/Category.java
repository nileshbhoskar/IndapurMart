package com.example.suhas.indapurmart.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 18/8/17.
 */

public class Category implements Parcelable{

    private String categoryID;
    private String enCategoryName;
    private String marCategoryName;
    private String isActive;
    private String imgURL;
    private SubCategory[] subCategory;

    public Category() {
    }

    protected Category(Parcel in) {
        categoryID = in.readString();
        enCategoryName = in.readString();
        marCategoryName = in.readString();
        isActive = in.readString();
        imgURL = in.readString();
        subCategory = in.createTypedArray(SubCategory.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getEnCategoryName() {
        return enCategoryName;
    }

    public void setEnCategoryName(String enCategoryName) {
        this.enCategoryName = enCategoryName;
    }

    public String getMarCategoryName() {
        return marCategoryName;
    }

    public void setMarCategoryName(String marCategoryName) {
        this.marCategoryName = marCategoryName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public SubCategory[] getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory[] subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public String toString() {
        return "CategoryData{" +
                "categoryID='" + categoryID + '\'' +
                ", enCategoryName='" + enCategoryName + '\'' +
                ", marCategoryName='" + marCategoryName + '\'' +
                ", isActive='" + isActive + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", subCategory=" + Arrays.toString(subCategory) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryID);
        parcel.writeString(enCategoryName);
        parcel.writeString(marCategoryName);
        parcel.writeString(isActive);
        parcel.writeString(imgURL);
        parcel.writeTypedArray(subCategory, i);
    }
}
