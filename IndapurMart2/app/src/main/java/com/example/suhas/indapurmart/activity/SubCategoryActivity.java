package com.example.suhas.indapurmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.adapter.SubCategoryAdapter;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String TAG = SubCategoryActivity.class.getSimpleName();
    private RecyclerView rvMainCategory;
    private ArrayList<SubCategory> subCategories;
    private String catID;
    private String categoryImgURL;
    private ImageView ivBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ivBanner = findViewById(R.id.iv_banner);
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
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_CATEGORY_URL)) {
            categoryImgURL = intent.getStringExtra(ICommonConstants.KEY_CATEGORY_URL);
        }
        rvMainCategory = findViewById(R.id.rv_categories);
        rvMainCategory.setLayoutManager(new GridLayoutManager(this, 2));

        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, catID);
        rvMainCategory.setAdapter(adapter);

        if (!TextUtils.isEmpty(categoryImgURL)) {
            Picasso.with(this)
                    .load(IWebServices.DOMAIN_NAME + categoryImgURL)
                    .into(ivBanner);
        }
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
