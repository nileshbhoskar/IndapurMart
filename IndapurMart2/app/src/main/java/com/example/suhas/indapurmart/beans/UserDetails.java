package com.example.suhas.indapurmart.beans;

/**
 * Created by dcpl-android on 18/8/17.
 *
 * class to hold UserData details.
 */

public class UserDetails {

    private String name;
    private String mobile1;
    private String mobile2;
    private String mailId;
    private String fullAddress;
    private String shopName;
    private String shopCategory;

    public UserDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    @Override
    public String toString() {
        return "UserDetailsData{" +
                "name='" + name + '\'' +
                ", mobile1='" + mobile1 + '\'' +
                ", mobile2='" + mobile2 + '\'' +
                ", mailId='" + mailId + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCategory='" + shopCategory + '\'' +
                '}';
    }
}
