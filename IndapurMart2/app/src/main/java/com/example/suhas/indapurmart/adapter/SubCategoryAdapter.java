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
import com.example.suhas.indapurmart.activity.ServiceProvidersActivity;
import com.example.suhas.indapurmart.beans.Category;
import com.example.suhas.indapurmart.beans.SubCategory;
import com.example.suhas.indapurmart.common.ICommonConstants;
import com.example.suhas.indapurmart.common.IWebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dcpl-android on 21/8/17.
 *
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.CategoryHolder> {
    private static final String TAG = SubCategoryAdapter.class.getSimpleName();
    private List<SubCategory> mDataList;
    private Context mContext;
    private String categoryId;

    public SubCategoryAdapter(Context mContext, List<SubCategory> mData, String categoryId) {
        this.mDataList = mData;
        this.mContext = mContext;
        this.categoryId = categoryId;
        Log.i(TAG,"SubCategoryAdapter");
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        if (null != mDataList && mDataList.size() > position) {
            final SubCategory category = mDataList.get(position);
            if (null == category) {
                return;
            }

            holder.placeData(category);
            holder.tvCategoryTitle.setText(category.getMarSubCatName());
            holder.rlMainCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ServiceProvidersActivity.class);
                    intent.putExtra(ICommonConstants.KEY_CATEGORY_URL, category.getImgURL());
                    intent.putExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID, categoryId);
                    intent.putExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID, category.getSubCatID());
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
        private RelativeLayout rlMainCategory;
        private SubCategory mCategory;
        private ImageView ivLicBg;
        private CategoryHolder(View itemView) {
            super(itemView);
            ivLicBg = itemView.findViewById(R.id.iv_lic_bg);

            tvCategoryTitle = itemView.findViewById(R.id.tv_category_title);
            rlMainCategory = itemView.findViewById(R.id.rl_main_category);
        }

        void placeData(SubCategory category) {
            if (category == null) {
                Log.e(TAG, "null category");
                return;
            }

            mCategory = category;

            if (!TextUtils.isEmpty(mCategory.getMarSubCatName())) {
                tvCategoryTitle.setText(mCategory.getMarSubCatName());
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
