package com.example.suhas.indapurmart.beans;

/**
 * Created by bhoskar1 on 20/12/17.
 */

public class BannerImage {
    private String url;
    private String sequence;
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BannerImage{" +
                "url='" + url + '\'' +
                ", sequence='" + sequence + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

