package com.example.suhas.indapurmart.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
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

import com.daimajia.slider.library.SliderLayout;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IResponseListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvMainCategory;
    private ArrayList<SubCategory> subCategories;
    private String catID;
    private boolean mVillageSelected;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationLayout;
    private ActionBarDrawerToggle mActionBarToggle;
    private boolean mGetVillage;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarToggle);
        mActionBarToggle.syncState();

        mNavigationLayout = findViewById(R.id.navigation);
        mNavigationLayout.setNavigationItemSelectedListener(this);
        mDemoSlider = findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.ic_hotel);
        file_maps.put("Big Bang Theory", R.drawable.product_detail_img);
        file_maps.put("House of Cards", R.drawable.metbkk_bkg_nahm_restaurant);
        file_maps.put("Game of Thrones", R.drawable.ic_food_first);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID)) {
            catID = intent.getStringExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID);
        }
        if (null != intent && intent.hasExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY)) {
            subCategories = intent.getParcelableArrayListExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY);
        }
        rvMainCategory = findViewById(R.id.rv_categories);
        rvMainCategory.setLayoutManager(new GridLayoutManager(this, 2));

        //loadJSONFromAsset();
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();

        networkRequests.webRequestGETString(this, IWebServices.URL_CATEGORIES, getString(R.string.dialog_msg_loading_data), true, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        loadVillageList(!preferences.contains(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST));
    }

    private void loadVillageList(boolean makeRequest) {
        Log.i(TAG, "loadVillageList::" + makeRequest);
        if (makeRequest) {
            mGetVillage = true;
            WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
            NetworkRequests networkRequests = reference.get();

            networkRequests.webRequestGETString(this, IWebServices.URL_VILLAGES, getString(R.string.dialog_msg_loading_data), true, this);
        }
    }

    public void villageListDlg(final Context context, List<Village> villages) {
        View promptsView = View.inflate(this, R.layout.dialog_village_list, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final RecyclerView etQuantity = promptsView.findViewById(R.id.rv_village_list);
        final Button btnClose = promptsView.findViewById(R.id.btn_close);
        etQuantity.setLayoutManager(new LinearLayoutManager(this));
        etQuantity.setAdapter(new VillagesListAdapter(this, villages));
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        if (id == R.id.nav_village_selection) {
            loadVillageList(true);
        } else if (id == R.id.nav_share_app) {
            openShareAppDialog();
        } else if (id == R.id.nav_register_shop) {
            intent.setClass(this, RegisterShopActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_rate_us) {
            //openShareAppDialog();
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.nav_contact_us) {
            intent.setClass(this, ContactUsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_notification) {
            Toast.makeText(this, "coming soon...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about_us) {
            intent.setClass(this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void openShareAppDialog() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Rotary app invitation");
        intent.putExtra(Intent.EXTRA_TEXT, "To know more about the IndapurMart download Android app : \nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        startActivity(intent);
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

        if (resp.contains("VillageID")) {
            mGetVillage = false;
            VillageData villageData = new Gson().fromJson(resp, VillageData.class);
            villageListDlg(this, Arrays.asList(villageData.getResult()));
        } else {
            CategoryData categoryList = new Gson().fromJson(resp, CategoryData.class);
            if (null == subCategories) {
                Log.i(TAG, "networkResponse :: InitializeMain CategoryData::" + categoryList);
                MainCategoryAdapter adapter = new MainCategoryAdapter(this, Arrays.asList(categoryList.getResult()), ICommonConstants.CATEGORY_MAIN);
                rvMainCategory.setAdapter(adapter);
            } else {
                Log.i(TAG, "networkResponse :: InitializeSub CategoryData::" + categoryList);
                SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, catID);
                rvMainCategory.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

