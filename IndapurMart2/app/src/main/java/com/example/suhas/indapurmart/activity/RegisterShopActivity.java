package com.example.suhas.indapurmart.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.beans.Category;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.CategoryData;
import com.example.suhas.indapurmart.data.VillageData;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;

public class RegisterShopActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IResponseListener {//}, View.OnFocusChangeListener,   View.OnTouchListener {
    private static final String TAG = RegisterShopActivity.class.getSimpleName();

    private EditText etEngName;
    private EditText etMarName;
    private EditText etEngShopName;
    private EditText etMarShopName;
    private EditText etMobileNumber;
    private EditText etShopAddress;
    private EditText etShopTime;
    private Spinner spinnerCategory;
    private Spinner spinnerSubCategory;
    private Spinner spinnerVillage;
    private Category[] mCategoryData;
    private List<String> mCategoryList;
    private List<String> mSubCategoryList;
    private VillageData villageData;
    private Context mContext;
    private static final String LI_CATEGORY = "Select category";
    private static final String LI_SUB_CATEGORY = "Select sub category";
    private static final String LI_SELECT_VILLAGE = "Select Village";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        mContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        etEngName = findViewById(R.id.et_eng_name);
        etMarName = findViewById(R.id.et_mar_name);
        etEngShopName = findViewById(R.id.et_eng_shop_name);
        etMarShopName = findViewById(R.id.et_mar_shop_name);
        etMobileNumber = findViewById(R.id.et_mobile_number);
        etShopAddress = findViewById(R.id.et_shop_address);
        etShopTime = findViewById(R.id.et_shop_time);
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerSubCategory = findViewById(R.id.spinner_sub_category);
        spinnerVillage = findViewById(R.id.spinner_village);
        Button btnUpdate = findViewById(R.id.btn_update);

        setSupportActionBar(toolbar);
        btnUpdate.setOnClickListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerSubCategory.setOnItemSelectedListener(this);
        spinnerVillage.setOnItemSelectedListener(this);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        loadCategoryData();
        loadVillageData();
        List<String> defaultList = new ArrayList();
        defaultList.add(LI_SUB_CATEGORY);
        ArrayAdapter<String> defaultAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, defaultList);
        spinnerSubCategory.setAdapter(defaultAdapter);

        defaultList = new ArrayList();
        defaultList.add(LI_CATEGORY);
        defaultAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, defaultList);
        spinnerCategory.setAdapter(defaultAdapter);

        defaultList = new ArrayList();
        defaultList.add(LI_SELECT_VILLAGE);
        defaultAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, defaultList);
        spinnerVillage.setAdapter(defaultAdapter);

        showDialogKeyboardLang();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                if (validateFields()) {
                    storeUserDetails();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void storeUserDetails() {
        Map<String, String> params = new HashMap<>();

        params.put("enUserName", etEngName.getText().toString());
        params.put("marUserName", etMarName.getText().toString());
        params.put("enShopName", etEngShopName.getText().toString());
        params.put("marShopName", etMarShopName.getText().toString());
        params.put("mobileNo", etMobileNumber.getText().toString());
        params.put("address", etShopAddress.getText().toString());
        params.put("shopTiming", etShopTime.getText().toString());
        String category = "[{" + "categoryID:" + spinnerCategory.getSelectedItemId() + ",  subCatID:" + spinnerSubCategory.getSelectedItemId() + "}]";
        params.put("category", category);
        String villageName = spinnerVillage.getSelectedItem().toString();
        Village village = null;
        for(Village village1 : villageData.getResult()){
            if (village1.getMarVillageName().equalsIgnoreCase(villageName)){
                village = village1;
            }
        }
        Gson gson = new Gson();
        if (null != village) {
            params.put("village", gson.toJson(village));
        } else {
            Toast.makeText(mContext, "Select village", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(TAG, "params::" + params);
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();
        networkRequests.webRequestPOSTString(mContext, IWebServices.URL_SAVE_VILLAGES_USERDATA, params, "Storing details", true, this);
    }

    private boolean validateFields() {

        if (spinnerCategory.getSelectedItem().toString().equals(LI_CATEGORY)) {
            spinnerCategory.requestFocus();
            Toast.makeText(this, "Select category", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spinnerVillage.getSelectedItem().toString().equals(LI_SUB_CATEGORY)) {
            spinnerVillage.requestFocus();
            Toast.makeText(this, "Select sub category", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(etEngName.getText().toString())) {
            etEngName.requestFocus();
            etEngName.setError("Enter Name in English");
            return false;
        }
        if (TextUtils.isEmpty(etMarName.getText().toString())) {
            etMarName.requestFocus();
            etMarName.setError("Enter Name in Marathi");
            return false;
        }
        if (TextUtils.isEmpty(etEngShopName.getText().toString())) {
            etEngShopName.requestFocus();
            etEngShopName.setError("Enter Shop Name in English");
            return false;
        }
        if (TextUtils.isEmpty(etMarShopName.getText().toString())) {
            etMarShopName.requestFocus();
            etMarShopName.setError("Enter Shop Name in Marathi");
            return false;
        }
        if (TextUtils.isEmpty(etMobileNumber.getText().toString())) {
            etMobileNumber.requestFocus();
            etMobileNumber.setError("Enter Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(etShopAddress.getText().toString())) {
            etShopAddress.requestFocus();
            etShopAddress.setError("Enter Shop Address");
            return false;
        }
        if (TextUtils.isEmpty(etShopTime.getText().toString())) {
            etShopTime.requestFocus();
            etShopTime.setError("Enter shop start and end timing");
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemSelected::");

        switch (adapterView.getId()) {
            case R.id.spinner_category:
                String categoryName = spinnerCategory.getSelectedItem().toString();
                if (!categoryName.equals(LI_CATEGORY)) {
                    Log.i(TAG, "onItemSelected::inside if");
                    Category category1 = null;
                    for (Category category : mCategoryData) {
                        if (category.getMarCategoryName().equals(categoryName)) {
                            category1 = category;
                            break;
                        }
                    }

                    if (category1 == null) {
                        Log.e(TAG, "onItemSelected:: null category1");
                        return;
                    }
                    if (mSubCategoryList == null) {
                        mSubCategoryList = new ArrayList<>();
                    }
                    for (SubCategory subCategory : category1.getSubCategory()) {
                        mSubCategoryList.add(subCategory.getMarSubCatName());
                    }
                    Log.i(TAG, "onItemSelected::mSubCategoryList::" + mSubCategoryList.toString());
                    Log.i(TAG, "onItemSelected::category1::" + category1.toString());
                    Log.i(TAG, "onItemSelected::");
                    mSubCategoryList.add(0, LI_SUB_CATEGORY);
                    spinnerSubCategory.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, mSubCategoryList));
                } else {
                    Log.i(TAG, "onItemSelected::inside else");
                }
                break;
            case R.id.spinner_sub_category:

                break;
            case R.id.spinner_village:

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void loadCategoryData() {
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();
        networkRequests.webRequestGETString(mContext, IWebServices.URL_CATEGORIES, getString(R.string.dialog_msg_loading_data), true, this);
    }

    private void loadVillageData() {
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();

        networkRequests.webRequestGETString(this, IWebServices.URL_VILLAGES, getString(R.string.dialog_msg_loading_data), true, this);
    }

    @Override
    public void networkResponse(Object response) {
        if (null == response) {
            Log.e(TAG, "Received null response");
            return;
        }

        if (!(response instanceof String)) {
            Log.e(TAG, "Received invalid response");
            return;
        }
        String resp = (String) response;
        response = null;

        Log.i(TAG, "networkResponse :: resp ::" + resp);
        if (resp.contains("enCategoryName") && resp.contains("marCategoryName")) {
            CategoryData categoryData = new Gson().fromJson(resp, CategoryData.class);
            //if (null == subCategories) {
            //Log.i(TAG, "networkResponse :: InitializeMain CategoryData::" + mCategoryData);
            mCategoryData = categoryData.getResult();
            if (null == mCategoryList) {
                mCategoryList = new ArrayList();
            }
            for (Category category : mCategoryData) {
                mCategoryList.add(category.getMarCategoryName());
            }
            mCategoryList.add(0, LI_CATEGORY);

            spinnerCategory.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, mCategoryList));
        } else if (resp.contains("VillageID")) {

            villageData = new Gson().fromJson(resp, VillageData.class);
            List<String> villages = new ArrayList<>();
            for (Village village : villageData.getResult()) {
                villages.add(village.getMarVillageName());
            }
            villages.add(0, "select Village");
            spinnerVillage.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, villages));
        }
    }

    private void showDialogKeyboardLang() {
        View promptsView = View.inflate(this, R.layout.dialog_marathi_input, null);

        final AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final TextView tvDescription = promptsView.findViewById(R.id.tv_description);
        final TextView tvCancel = promptsView.findViewById(R.id.tv_cancel);
        final TextView tvGoToSetting = promptsView.findViewById(R.id.tv_go_to_setting);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        tvDescription.setText(getString(R.string.marathi_input_inst));

        tvGoToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS);

                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
