package com.example.suhas.indapurmart.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.suhas.indapurmart.BuildConfig;
import com.example.suhas.indapurmart.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_new);
        Toolbar tbContactUs = findViewById(R.id.tb_contact_us);
        TextView tv_email_id = findViewById(R.id.tv_email_id);
        TextView tvContactUsNo = findViewById(R.id.tv_contact_us_no);
        TextView tvAppVersion = findViewById(R.id.tv_app_version);
        tbContactUs.setTitle(getString(R.string.contact_us));
        setSupportActionBar(tbContactUs);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.contact_us));
        }
        tvContactUsNo.setOnClickListener(this);
        tvAppVersion.setText(("V " + BuildConfig.VERSION_NAME));
        tv_email_id.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_contact_us_no:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8668911016"));
                startActivity(intent);
                break;
            case R.id.tv_email_id:
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"indapurmart@gmail.com"};
                mailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Indapur Mart app");
                mailIntent.setType("text/html");
                startActivity(Intent.createChooser(mailIntent, "Send mail"));
                break;
        }
    }
}
