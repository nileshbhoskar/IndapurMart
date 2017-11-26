package com.example.suhas.indapurmart.data;

import com.example.suhas.indapurmart.beans.Default;
import com.example.suhas.indapurmart.beans.User;

import java.util.Arrays;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class UserData  extends Default {

    User[] result;

    public User[] getResult() {
        return result;
    }

    public void setResult(User[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
