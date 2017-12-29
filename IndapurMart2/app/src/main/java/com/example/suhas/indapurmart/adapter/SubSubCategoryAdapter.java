package com.example.suhas.indapurmart.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.activity.DetailsActivity;
import com.example.suhas.indapurmart.beans.User;
import com.example.suhas.indapurmart.common.ICommonConstants;

import java.util.List;

/**
 * Created by bhoskar1 on 16/8/17.
 *
 */

public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.SubSubCategoryHolder> {

    private static final String TAG = SubSubCategoryAdapter.class.getSimpleName();
    private List<User> mDataList;
    private Context mContext;
    private String category;
    private String subCategory;

    public SubSubCategoryAdapter(Context mContext, List<User> mData, String category, String subCategory) {
        Log.i(TAG,"SubSubCategoryAdapter");
        Log.i(TAG,"SubSubCategoryAdapter::mData::" + mData);

        this.mDataList = mData;
        this.mContext = mContext;
        this.category = category;
        this.subCategory = subCategory;
    }

    @Override
    public SubSubCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");
        return new SubSubCategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_sub_category, parent, false));
    }

    @Override
    public void onBindViewHolder(SubSubCategoryHolder holder, int position) {
        Log.i(TAG,"onBindViewHolder::Test subsub category");
        if (null != mDataList && mDataList.size() > 0) {
            final User user = mDataList.get(position);
            holder.tvCategoryTitle.setText(mContext.getString(R.string.mar_owner_name,user.getMarUserName()));
            holder.tvShopName.setText(user.getMarShopName());
            holder.tvContactNo.setText(user.getMobileNo());
            holder.tv_address.setText(user.getAddress());
            holder.rlSubSubCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Fragment fragment = ServiceProvidersActivity.newInstance(category, subCategory, village.getVillageID());
                    //FragmentTransaction fragmentTransaction = ;
                    Log.i(TAG,"category::" + category);
                    Log.i(TAG,"subCategory::" + subCategory);

                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    //intent.putExtra(ICommonConstants.KEY_PARCELABLE_CATEGORY_ID, category);
                    //intent.putExtra(ICommonConstants.KEY_PARCELABLE_SUB_CATEGORY_ID, subCategory);
                    //intent.putExtra(ICommonConstants.KEY_PARCELABLE_VILLAGE_ID, user.getVillageID());
                    intent.putExtra(ICommonConstants.KEY_PARCELABLE_USER, user);
                    mContext.startActivity(intent);
                }
            });

            holder.ivLiscCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + user.getMobileNo()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,"getItemCount::" + mDataList.size());
        if (null != mDataList) {
            return mDataList.size();
        }
        return 0;
    }

    class SubSubCategoryHolder extends RecyclerView.ViewHolder {

        private TextView tvShopName;
        private TextView tvContactNo;
        private TextView tv_address;
        private TextView tvCategoryTitle;
        private ImageView ivLiscCall;
        private RelativeLayout rlSubSubCategory;

        private SubSubCategoryHolder(View itemView) {
            super(itemView);
            ivLiscCall = itemView.findViewById(R.id.iv_lisc_call);
            tvShopName = itemView.findViewById(R.id.tv_shop_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tvContactNo = itemView.findViewById(R.id.tv_contact_no);
            tvCategoryTitle = itemView.findViewById(R.id.tv_sub_category_title);
            rlSubSubCategory = itemView.findViewById(R.id.rl_sub_sub_category);
        }
    }
}
