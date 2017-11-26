package com.example.suhas.indapurmart.data;

import com.example.suhas.indapurmart.beans.Default;
import com.example.suhas.indapurmart.beans.UserDetails;

import java.util.Arrays;

/**
 * Created by dcpl-android on 18/8/17.
 *
 * class to hold UserData details.
 */

public class UserDetailsData  extends Default {

    private UserDetails[] result;

    public UserDetails[] getResult() {
        return result;
    }

    public void setResult(UserDetails[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserDetailsData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
