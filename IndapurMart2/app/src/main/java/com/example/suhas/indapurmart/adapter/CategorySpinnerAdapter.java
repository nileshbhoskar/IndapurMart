package com.example.suhas.indapurmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.beans.Category;

import java.util.List;

/**
 * Created by bhoskar1 on 20/12/17.
 */

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Context mContext;
    private List<Category> mDataList;
    public CategorySpinnerAdapter(@NonNull Context context, int resource, List<Category> mDataList) {
        super(context, resource);
        this.mContext = context;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(mDataList.get(position).getMarCategoryName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setText(mDataList.get(position).getMarCategoryName());

        return label;
    }
}
