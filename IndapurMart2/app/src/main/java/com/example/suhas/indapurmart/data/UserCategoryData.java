package com.example.suhas.indapurmart.data;

import com.example.suhas.indapurmart.beans.Default;
import com.example.suhas.indapurmart.beans.UserCategory;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class UserCategoryData  extends Default {
    private UserCategory[] result;

    public UserCategory[] getResult() {
        return result;
    }

    public void setResult(UserCategory[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserCategoryData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
