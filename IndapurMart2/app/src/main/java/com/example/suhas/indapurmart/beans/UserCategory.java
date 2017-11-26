package com.example.suhas.indapurmart.beans;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class UserCategory {
    private String categoryID;
    private String subCatID;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCatID() {
        return subCatID;
    }

    public void setSubCatID(String subCatID) {
        this.subCatID = subCatID;
    }

    @Override
    public String toString() {
        return "UserCategoryData{" +
                "categoryID='" + categoryID + '\'' +
                ", subCatID='" + subCatID + '\'' +
                '}';
    }
}
