package com.example.suhas.indapurmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.common.ICommonConstants;

import java.util.ArrayList;
import java.util.List;

public class SubSubCategoryActivity extends AppCompatActivity {

    private RecyclerView rvSubSubCategory;
    private String subCategory;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID)) {
            subCategory = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID);
        }
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID)) {
            category = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvSubSubCategory = (RecyclerView) findViewById(R.id.rv_categories);
        rvSubSubCategory.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = new ArrayList<String>();
        list.add("Restaurants");
        list.add("Entertainment");
        list.add("Doctors");
        list.add("Travel");
        list.add("Shops");
        list.add("Caterers");
        list.add("Jewellery");
        list.add("Pest Control");
        list.add("Shopping");
        list.add("Sports");

        //rvSubSubCategory.setAdapter(new SubSubCategoryAdapter(this, list, category, subCategory));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
