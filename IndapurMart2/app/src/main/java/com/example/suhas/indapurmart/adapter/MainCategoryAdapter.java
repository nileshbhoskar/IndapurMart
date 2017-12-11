package com.example.suhas.indapurmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.activity.MainActivity;
import com.example.suhas.indapurmart.activity.SubCategoryActivity;
import com.example.suhas.indapurmart.beans.Category;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bhoskar1 on 15/8/17.
 * <p>
 * class to hold main categoryType data.
 */

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.CategoryHolder> {
    private static final String TAG = MainCategoryAdapter.class.getSimpleName();
    private List<Category> mDataList;
    private Context mContext;
    //private int categoryType;

    public MainCategoryAdapter(Context mContext, List<Category> mData, int category) {
        this.mDataList = mData;
        this.mContext = mContext;
        //this.categoryType = category;
        Log.i(TAG, "MainCategoryAdapter");
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder:: position::" + position);
        if (null != mDataList && mDataList.size() > position) {
            final Category category = mDataList.get(position);
            Log.i(TAG, "onBindViewHolder:: category::" + category);
            if (null == category) {
                return;
            }
            holder.placeData(category);
            //holder.tvCategoryTitle.setText(category.getEnCategoryName());
            holder.rlMainCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SubCategoryActivity.class);
                    intent.putExtra(ICommonConstants.KEY_CATEGORY_URL, category.getImgURL());
                    intent.putExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID, category.getCategoryID());
                    List<SubCategory> subCategories = Arrays.asList(category.getSubCategory());
                    ArrayList<SubCategory> subCategories1 = new ArrayList<>();
                    subCategories1.addAll(subCategories);
                    intent.putParcelableArrayListExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY, subCategories1);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null != mDataList) {
            return mDataList.size();
        }
        return 0;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryTitle;
        private ImageView ivLicBg;
        private RelativeLayout rlMainCategory;
        private Category mCategory;

        private CategoryHolder(View itemView) {
            super(itemView);

            ivLicBg = itemView.findViewById(R.id.iv_lic_bg);
            tvCategoryTitle = itemView.findViewById(R.id.tv_category_title);
            rlMainCategory = itemView.findViewById(R.id.rl_main_category);
        }

        void placeData(Category category) {
            if (category == null) {
                Log.e(TAG, "null category");
                return;
            }

            mCategory = category;

            if (!TextUtils.isEmpty(mCategory.getMarCategoryName())) {
                tvCategoryTitle.setText(mCategory.getMarCategoryName());
            } else {
                tvCategoryTitle.setText(mContext.getString(R.string.main_category_mar));
            }
            if (!TextUtils.isEmpty(mCategory.getImgURL())) {
                Picasso.with(mContext)
                        .load(IWebServices.DOMAIN_NAME + mCategory.getImgURL())
                        .into(ivLicBg);
            }
        }
    }
}
