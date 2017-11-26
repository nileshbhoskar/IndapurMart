package com.example.suhas.indapurmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.adapter.SubSubCategoryAdapter;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.VillageData;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;

public class VillagesActivity extends AppCompatActivity implements IResponseListener {
    private static final String TAG = VillagesActivity.class.getSimpleName();
    private RecyclerView rvMainCategory;
    private ArrayList<VillageData> villageData;
    private String catID;
    private String subCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMainCategory = findViewById(R.id.rv_categories);
        rvMainCategory.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID)) {
            subCategories = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID);
        }
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID)) {
            catID = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID);
        }

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

        Log.e(TAG, "networkResponse::Received response :: " + resp);
        VillageData categoryData = new Gson().fromJson(resp, VillageData.class);

        if (categoryData.getStatusCode().equals("200")) {
            Village[] categories = categoryData.getResult();

            Log.i(TAG, "networkResponse :: InitializeMain CategoryData");
            Log.i(TAG, "networkResponse :: array length::" + categories.length);
            List list = Arrays.asList(categories);
            Log.i(TAG, "networkResponse :: list size::" + list.size());

            SubSubCategoryAdapter adapter = new SubSubCategoryAdapter(this, list, catID, subCategories);
            rvMainCategory.setAdapter(adapter);
        }
    }
}
