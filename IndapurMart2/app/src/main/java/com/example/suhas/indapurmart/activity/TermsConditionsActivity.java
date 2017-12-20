package com.example.suhas.indapurmart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suhas.indapurmart.BuildConfig;
import com.example.suhas.indapurmart.R;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_us_new);
        Toolbar tbAboutUs = findViewById(R.id.tb_about_us);
        TextView tvAboutUsText = findViewById(R.id.tv_about_us_text);
        ImageView ivAppLogo = findViewById(R.id.iv_app_logo);
        TextView tvAppVersion = findViewById(R.id.tv_app_version);
        tbAboutUs.setTitle(getString(R.string.title_terms_conditions));
        tvAppVersion.setText(("V " + BuildConfig.VERSION_NAME));
        tvAboutUsText.setText(getString(R.string.terms_n_conditions));
        setSupportActionBar(tbAboutUs);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
