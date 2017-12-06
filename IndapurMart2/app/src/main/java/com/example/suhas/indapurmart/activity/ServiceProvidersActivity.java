package com.example.suhas.indapurmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.adapter.SubSubCategoryAdapter;
import com.example.suhas.indapurmart.beans.User;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.UserData;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;

/**
 * Created by dcpl-android on 22/9/17.
 */

public class ServiceProvidersActivity extends AppCompatActivity implements IResponseListener {

    private static final String TAG = ServiceProvidersActivity.class.getSimpleName();
    private String catId;
    private String subCatId;
    private static final String HEADER_KEY_CAT_ID = "catID";
    private static final String HEADER_KEY_SUB_CAT_ID = "subCatID";
    private static final String HEADER_KEY_VILLAGE_ID = "villages";
    private RecyclerView rvVillages;
    private TextView tvNoRecordFound;
    private Button btnRegister;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villeges);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mContext = this;
        Bundle args = getIntent().getExtras();
        if (null != args) {
            Log.i(TAG, "args::" + args.toString());

            if (args.containsKey(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID)) {
                subCatId = args.getString(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID);
            }
            if (args.containsKey(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID)) {
                catId = args.getString(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID);
            }
            setSupportActionBar(toolbar);
            if (null != getSupportActionBar()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        btnRegister = findViewById(R.id.btn_register);
        tvNoRecordFound = findViewById(R.id.tv_no_record_found);
        rvVillages = findViewById(R.id.rv_categories);
        rvVillages.setLayoutManager(new LinearLayoutManager(mContext));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterShopActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String villageList = preferences.getString(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, "");
        if (TextUtils.isEmpty(villageList)) {
        }
        villageList = "[]";
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();
        Map<String, String> params = new HashMap<>();

        params.put(HEADER_KEY_CAT_ID, catId);
        params.put(HEADER_KEY_SUB_CAT_ID, subCatId);
        params.put(HEADER_KEY_VILLAGE_ID, villageList);
        networkRequests.webRequestPOSTString(this, IWebServices.URL_VILLAGES_USER_DATA, params, getString(R.string.dialog_msg_loading_data), true, this);
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
        UserData userData = new Gson().fromJson(resp, UserData.class);

        if (userData.getStatusCode().equals("200")) {
            User[] categories = userData.getResult();
            if (categories != null && categories.length != 0) {
                btnRegister.setVisibility(View.GONE);
                tvNoRecordFound.setVisibility(View.GONE);
                rvVillages.setVisibility(View.VISIBLE);
                Log.i(TAG, "networkResponse :: InitializeMain CategoryData");
                Log.i(TAG, "networkResponse :: array length::" + categories.length);
                List list = Arrays.asList(categories);
                Log.i(TAG, "networkResponse :: list size::" + list.size());

                SubSubCategoryAdapter adapter = new SubSubCategoryAdapter(this, list, null, null);
                rvVillages.setAdapter(adapter);
            } else {
                btnRegister.setVisibility(View.VISIBLE);
                tvNoRecordFound.setVisibility(View.VISIBLE);
                rvVillages.setVisibility(View.GONE);
            }
        }/* else {

        }*/
    }
}
