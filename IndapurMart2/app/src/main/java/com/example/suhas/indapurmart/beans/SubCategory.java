package com.example.suhas.indapurmart.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bhoskar1 on 18/8/17.
 */

public class SubCategory implements Parcelable {

    private String subCatID;
    private String enSubCatName;
    private String marSubCatName;
    private String isActive;
    private String imgURL;

    public SubCategory() {
    }

    protected SubCategory(Parcel in) {
        subCatID = in.readString();
        enSubCatName = in.readString();
        marSubCatName = in.readString();
        isActive = in.readString();
        imgURL = in.readString();
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    public String getSubCatID() {
        return subCatID;
    }

    public void setSubCatID(String subCatID) {
        this.subCatID = subCatID;
    }

    public String getEnSubCatName() {
        return enSubCatName;
    }

    public void setEnSubCatName(String enSubCatName) {
        this.enSubCatName = enSubCatName;
    }

    public String getMarSubCatName() {
        return marSubCatName;
    }

    public void setMarSubCatName(String marSubCatName) {
        this.marSubCatName = marSubCatName;
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

    @Override
    public String toString() {
        return "SubCategoryData{" +
                "subCatID='" + subCatID + '\'' +
                ", enSubCatName='" + enSubCatName + '\'' +
                ", marSubCatName='" + marSubCatName + '\'' +
                ", isActive='" + isActive + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subCatID);
        parcel.writeString(enSubCatName);
        parcel.writeString(marSubCatName);
        parcel.writeString(isActive);
        parcel.writeString(imgURL);
    }
}
