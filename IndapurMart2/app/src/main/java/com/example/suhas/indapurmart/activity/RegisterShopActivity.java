package com.example.suhas.indapurmart.activity;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.beans.Category;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.IWebServices;
import com.example.suhas.indapurmart.data.CategoryData;
import com.example.suhas.indapurmart.data.VillageData;
import com.example.suhas.indapurmart.keyboard.CustomKeyboardView;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbit.com.networkreauest.request.NetworkRequests;
import nbit.com.networkreauest.util.IResponseListener;
/*import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.google.api.translate.TranslateV2;*/

public class RegisterShopActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, IResponseListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = RegisterShopActivity.class.getSimpleName();
    private Toolbar toolbar;
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
    private Button btnUpdate;
    private CustomKeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private Category[] mCategoryData;
    private SubCategory[] mSubCategoryData;
    private List<String> mCategoryList;
    private List<String> mSubCategoryList;
    private Context mContext;
    private static final String LI_CATEGORY = "Select category";
    private static final String LI_SUB_CATEGORY = "Select sub category";

    public void hideSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        mContext = this;
        toolbar = findViewById(R.id.toolbar);
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
        btnUpdate = findViewById(R.id.btn_update);
        mKeyboardView = findViewById(R.id.keyboardView);

        setSupportActionBar(toolbar);
        btnUpdate.setOnClickListener(this);
        etMarName.setOnFocusChangeListener(this);
        etMarShopName.setOnFocusChangeListener(this);
        etShopAddress.setOnFocusChangeListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerSubCategory.setOnItemSelectedListener(this);
        spinnerVillage.setOnItemSelectedListener(this);
        loadCategoryData();
        loadVillageData();
        List<String> villageList = new ArrayList();
        villageList.add(LI_SUB_CATEGORY);
        spinnerSubCategory.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, villageList));

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            switch (view.getId()) {
                case R.id.et_shop_address:
                    selectKeyboard(etShopAddress);
                    break;
                case R.id.et_mar_shop_name:
                    selectKeyboard(etMarShopName);
                    break;
                case R.id.et_shop_time:
                    selectKeyboard(etShopTime);
                    break;
            }
        } else {

        }
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

    private void storeUserDetails() {
        Map<String, String> params = new HashMap<>();

        params.put("enUserName", etEngName.getText().toString());
        params.put("marUserName", etMarName.getText().toString());
        params.put("enShopName", etEngShopName.getText().toString());
        params.put("marShopName", etMarShopName.getText().toString());
        params.put("mobileNo", etMobileNumber.getText().toString());
        params.put("address", etShopAddress.getText().toString());
        params.put("shopTiming", etShopTime.getText().toString());
        String category = "[{" + "categoryID:" + spinnerCategory.getSelectedItem().toString() + ",  subCatID:" + spinnerSubCategory.getSelectedItem().toString() + "}]";
        params.put("category", category);
        //params.put("test", spinnerSubCategory.getSelectedItem().toString());
        params.put("village", spinnerVillage.getSelectedItem().toString());
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
        if (view.getId() == R.id.spinner_category) {
            String categoryName = spinnerCategory.getSelectedItem().toString();
            Category category1 = null;
            for (Category category : mCategoryData) {
                if (category.getMarCategoryName().equals(categoryName)) {
                    category1 = category;
                    break;
                }
            }

            if (mSubCategoryList == null) {
                mSubCategoryList = new ArrayList<>();
            }
            for (SubCategory subCategory : category1.getSubCategory()) {
                mSubCategoryList.add(subCategory.getMarSubCatName());
            }
            mSubCategoryList.add(0, LI_SUB_CATEGORY);
            spinnerSubCategory.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, mSubCategoryData));
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
            Log.i(TAG, "networkResponse :: InitializeMain CategoryData::" + mCategoryData);
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

            VillageData villageData = new Gson().fromJson(resp, VillageData.class);
            List<String> villages = new ArrayList<>();
            for (Village village : villageData.getResult()) {
                villages.add(village.getMarVillageName());
            }
            villages.add(0, "select Village");
            spinnerVillage.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, villages));
        }
    }

    public void selectKeyboard(final EditText editText) {

        // Do not show the preview balloons
        mKeyboardView.setPreviewEnabled(false);

        hideSoftKeyboard(this, editText);

        mKeyboard = new Keyboard(mContext, R.xml.kbd_mar1);
        showKeyboardWithAnimation();
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setKeyboard(mKeyboard);

        mKeyboardView.setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
                this,
                etMarName,
                mKeyboardView));

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selectKeyboard(editText);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Layout layout = ((EditText) v).getLayout();
                        float x = event.getX() + editText.getScrollX();
                        float y = event.getY() + editText.getScrollY();
                        int line = layout.getLineForVertical((int) y);

                        int offset = layout.getOffsetForHorizontal(line, x);
                        if (offset > 0)
                            if (x > layout.getLineMax(0))
                                editText
                                        .setSelection(offset);     // touch was at end of text
                            else
                                editText.setSelection(offset - 1);

                        editText.setCursorVisible(true);
                        break;
                }
                return true;
            }
        });
    }

    private void showKeyboardWithAnimation() {
        if (mKeyboardView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(RegisterShopActivity.this,
                            R.anim.slide_from_bottom);
            mKeyboardView.showWithAnimation(animation);
        }
    }

    public class BasicOnKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

        EditText editText;
        CustomKeyboardView displayKeyboardView;
        private Activity mTargetActivity;

        public BasicOnKeyboardActionListener(Activity targetActivity, EditText editText,
                                             CustomKeyboardView
                                                     displayKeyboardView) {
            mTargetActivity = targetActivity;
            this.editText = editText;
            this.displayKeyboardView = displayKeyboardView;
        }

        @Override
        public void swipeUp() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void onText(CharSequence text) {
            int cursorPosition = editText.getSelectionEnd();
            String before = editText.getText().toString().substring(0, cursorPosition);
            String after = editText.getText().toString().substring(cursorPosition);
            editText.setText(before + text + after);
            editText.setSelection(cursorPosition + 1);
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            switch (primaryCode) {
                case 66:
                case 67:
                    long eventTime = System.currentTimeMillis();
                    KeyEvent event =
                            new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0,
                                    KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);

                    mTargetActivity.dispatchKeyEvent(event);
                    break;
                case -110:
                    displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_mar2));
                    break;
                case -111:
                    displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_mar1));
                    break;
                default:
                    break;
            }
        }
    }
}
