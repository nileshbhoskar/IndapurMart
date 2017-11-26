package com.example.suhas.indapurmart.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.suhas.indapurmart.beans.Category;
import com.example.suhas.indapurmart.beans.Default;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 18/8/17.
 */

public class CategoryData extends Default {

    private Category[] result;

    public Category[] getResult() {
        return result;
    }

    public void setResult(Category[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CategoryData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
