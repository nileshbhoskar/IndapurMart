package com.example.suhas.indapurmart.common;

/**
 * Created by bhoskar1 on 25/8/17.
 *
 */

public interface IWebServices {

    //String DOMAIN_NAME = "http://192.168.1.105";
    String DOMAIN_NAME = "https://indapurmart.herokuapp.com";
    //String PORT = "8000";
    String SUB_DIR_1 = "indapurmart";
    String SUB_DIR_2 = "app1";
    String URL_DIVIDER = "/";
    //String FINAL_URL = DOMAIN_NAME + ":" + PORT + URL_DIVIDER + SUB_DIR_1 + URL_DIVIDER + SUB_DIR_2 + URL_DIVIDER;
    String FINAL_URL = DOMAIN_NAME + URL_DIVIDER + SUB_DIR_1 + URL_DIVIDER + SUB_DIR_2 + URL_DIVIDER;
    String URL_USER_DATA = FINAL_URL + "userdata";
    String URL_VILLAGES = FINAL_URL + "villages";
    String URL_VILLAGES_USER_DATA = FINAL_URL + "villagesuserdata";
    String URL_IMAGE = FINAL_URL + "image" + URL_DIVIDER;
    String URL_SAVE_VILLAGES_USERDATA = FINAL_URL + "savevillagesuserdata";
    String URL_CATEGORIES = FINAL_URL + "categories";

}
