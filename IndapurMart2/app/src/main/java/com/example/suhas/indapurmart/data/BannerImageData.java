package com.example.suhas.indapurmart.data;

import com.example.suhas.indapurmart.beans.BannerImage;
import com.example.suhas.indapurmart.beans.Category;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 20/12/17.
 */

public class BannerImageData {
    private BannerImage[] result;

    public BannerImage[] getResult() {
        return result;
    }

    public void setResult(BannerImage[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BannerImageData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
