package com.example.suhas.indapurmart.data;

import com.example.suhas.indapurmart.beans.Default;
import com.example.suhas.indapurmart.beans.Village;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class VillageData  extends Default {
    private Village[] result;

    public Village[] getResult() {
        return result;
    }

    public void setResult(Village[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "VillageData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
