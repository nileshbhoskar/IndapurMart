package com.example.suhas.indapurmart.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.beans.User;
import com.example.suhas.indapurmart.common.ICommonConstants;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_reg_details);
        Intent intent = getIntent();

        if (null != intent) {
            mUser = intent.getParcelableExtra(ICommonConstants.KEY_PARCELABLE_USER);
            Log.i(TAG, "User::" + mUser);
        }

        Toolbar tbUserDetail = findViewById(R.id.tb_owner_details);
        tbUserDetail.setTitle(mUser.getMarShopName());
        ImageView iv_profile_picture = findViewById(R.id.iv_profile_picture);
        RelativeLayout rl_user_details = findViewById(R.id.rl_user_details);
        TextView tvUserName = findViewById(R.id.tv_owner_name);
        TextView tvAddress = findViewById(R.id.tv_address);
        TextView tvPhone = findViewById(R.id.tv_phone);
        //TextView tvOpenDay = findViewById(R.id.tv_open_day);
        TextView tvCall = findViewById(R.id.tv_call);
        TextView tvShare = findViewById(R.id.tv_share);
        TextView tvShopTiming = findViewById(R.id.tv_shop_timing);
        setSupportActionBar(tbUserDetail);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
        tvUserName.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        //tvOpenDay.setOnClickListener(this);
        tvCall.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        if (null != mUser) {
            tbUserDetail.setTitle(mUser.getMarShopName());
            tvUserName.setText(getString(R.string.mar_owner_name,mUser.getMarUserName()));
            tvAddress.setText(mUser.getAddress());
            tvPhone.setText(mUser.getMobileNo());
            //tvOpenDay.setText(mUser.getShopTiming());
            tvCall.setText(mUser.getMobileNo());
            tvShopTiming.setText(mUser.getShopTiming());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_user_details:
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_phone:

                break;
            /*case R.id.tv_open_day:
                break;*/
            case R.id.tv_call:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mUser.getMobileNo()));
                startActivity(intent);
                break;
            case R.id.tv_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, (getString(R.string.owner_name_mar) + " : " + mUser.getMarUserName())
                + "\n" + (getString(R.string.shop_name_mar) + " : " + mUser.getMarShopName())
                + "\n" + (getString(R.string.mar_address) + " : " + mUser.getAddress())
                + "\n" + (getString(R.string.mobile_no_mar) + " : " + mUser.getMobileNo()));
                //intent.putExtra(Intent.EXTRA_TEXT, (getString(R.string.village_name) + " : " + mUser.getVillage().getMarVillageName()));
                intent.setType("text/plain");

                startActivity(intent);
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
}
