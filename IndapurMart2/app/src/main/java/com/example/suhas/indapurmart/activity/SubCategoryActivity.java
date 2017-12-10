package com.example.suhas.indapurmart.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.suhas.indapurmart.BuildConfig;
import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.adapter.MainCategoryAdapter;
import com.example.suhas.indapurmart.adapter.SubCategoryAdapter;
import com.example.suhas.indapurmart.adapter.VillagesListAdapter;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.CategoryData;
import com.example.suhas.indapurmart.data.VillageData;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvMainCategory;
    private ArrayList<SubCategory> subCategories;
    private String catID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID)) {
            catID = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID);
        }
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY)) {
            subCategories = intent.getParcelableArrayListExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY);
        }
        rvMainCategory = findViewById(R.id.rv_categories);
        rvMainCategory.setLayoutManager(new GridLayoutManager(this, 2));

        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, catID);
        rvMainCategory.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

}
