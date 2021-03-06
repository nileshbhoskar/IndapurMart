package com.example.suhas.indapurmart.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.suhas.indapurmart.BuildConfig;
import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.adapter.MainCategoryAdapter;
import com.example.suhas.indapurmart.adapter.VillagesListAdapter;
import com.example.suhas.indapurmart.beans.BannerImage;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.BannerImageData;
import com.example.suhas.indapurmart.data.CategoryData;
import com.example.suhas.indapurmart.data.VillageData;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;


public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, NavigationView.OnNavigationItemSelectedListener, IResponseListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvMainCategory;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationLayout;
    private ActionBarDrawerToggle mActionBarToggle;
    private SliderLayout mDemoSlider;
    public static Set<String> selectedVillageList = new HashSet<>();

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


        mNavigationLayout.setVisibility(View.VISIBLE);

        rvMainCategory = findViewById(R.id.rv_categories);
        rvMainCategory.setLayoutManager(new GridLayoutManager(this, 2));

        //loadJSONFromAsset();
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();

        networkRequests.webRequestGETString(this, IWebServices.URL_CATEGORIES, getString(R.string.dialog_msg_loading_data), true, this);
        loadBannerImagesList();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void bannerSlider(Map<String, String> file_maps) {
        Log.i(TAG, "bannerSlider::file_maps::" + file_maps);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*SharedPreferences preferences = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        loadVillageList(!preferences.contains(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST));*/
    }

    private void loadBannerImagesList() {
        Log.i(TAG, "loadBannerImagesList::");
        WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
        NetworkRequests networkRequests = reference.get();

        networkRequests.webRequestGETString(this, IWebServices.URL_ADD_WORLD, getString(R.string.dialog_msg_loading_data), true, this);
    }

    private void loadVillageList(boolean makeRequest) {
        Log.i(TAG, "loadVillageList::" + makeRequest);
        if (makeRequest) {
            WeakReference<NetworkRequests> reference = new WeakReference<>(new NetworkRequests());
            NetworkRequests networkRequests = reference.get();
            networkRequests.webRequestGETString(this, IWebServices.URL_VILLAGES, getString(R.string.dialog_msg_loading_data), true, this);
        }
    }

    public void villageListDialog(final Context context, List<Village> villages) {
        View promptsView = View.inflate(this, R.layout.dialog_village_list, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final RecyclerView etQuantity = promptsView.findViewById(R.id.rv_village_list);
        final Button btnClose = promptsView.findViewById(R.id.btn_close);
        etQuantity.setLayoutManager(new LinearLayoutManager(this));
        selectedVillageList = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, MODE_PRIVATE).getStringSet(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, null);
        //Set<String> villageSet = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, MODE_PRIVATE).getStringSet(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, null);
        Log.i(TAG,"villageListDialog::"+selectedVillageList);
        if (null != selectedVillageList && selectedVillageList.size() > 0) {
            Log.e(TAG, "empty set");
            for (String id : selectedVillageList) {
                Log.i(TAG, "id ::id::" + id);
                for (Village village : villages) {
                    if (id.contains(village.getMarVillageName())) {
                        Log.i(TAG, "id exist::id::" + id);
                        village.setSelected(true);
                        continue;
                    }
                }
            }
        }
        etQuantity.setAdapter(new VillagesListAdapter(this, villages));
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setOnDismissListener(this);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = findViewById(R.id.drawer_layout);
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
            return true;
        } else if (id == R.id.nav_share_app) {
            openShareAppDialog();
        } else if (id == R.id.nav_register_shop) {
            intent.setClass(this, RegisterShopActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_rate_us) {
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
        } else if (id == R.id.nav_terms_n_conditions) {
            intent.setClass(this, TermsConditionsActivity.class);
            startActivity(intent);
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "Indapur Mart app invitation");
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

        if (resp.contains("villageID")) {
            //mGetVillage = false;
            VillageData villageData = new Gson().fromJson(resp, VillageData.class);
            villageListDialog(this, Arrays.asList(villageData.getResult()));
        } else if (resp.contains("desc") && resp.contains("url")) {
            BannerImageData bannerImageData = new Gson().fromJson(resp, BannerImageData.class);
            Log.i(TAG, "networkResponse :: InitializeMain CategoryData::" + bannerImageData);
            LinkedHashMap<String, String> bannerMap = new LinkedHashMap<>();
            for (BannerImage bannerImage : bannerImageData.getResult()) {
                bannerMap.put(bannerImage.getDesc(), IWebServices.DOMAIN_NAME + bannerImage.getUrl());
            }
            bannerSlider(bannerMap);

        } else {
            CategoryData categoryList = new Gson().fromJson(resp, CategoryData.class);
            Log.i(TAG, "networkResponse :: InitializeMain CategoryData::" + categoryList);
            MainCategoryAdapter adapter = new MainCategoryAdapter(this, Arrays.asList(categoryList.getResult()), ICommonConstants.CATEGORY_MAIN);
            rvMainCategory.setAdapter(adapter);
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

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Log.i(TAG, "dialogListener::onDismiss::" + selectedVillageList);
        SharedPreferences preferences = getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, selectedVillageList);
        editor.apply();
    }
}

