package com.example.suhas.indapurmart.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.suhas.indapurmart.beans.Default;
import com.example.suhas.indapurmart.beans.SubCategory;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 18/8/17.
 */

public class SubCategoryData extends Default {

    private SubCategory[] result;

    public SubCategory[] getResult() {
        return result;
    }

    public void setResult(SubCategory[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SubCategoryData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
