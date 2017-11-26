package com.example.suhas.indapurmart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.suhas.indapurmart.R;
import com.example.suhas.indapurmart.beans.Village;
import com.example.suhas.indapurmart.common.ICommonConstants;

import java.util.List;

/**
 * Created by bhoskar1 on 12/10/17.
 *
 */

public class VillagesListAdapter extends RecyclerView.Adapter<VillagesListAdapter.VillageHolder> {

    private static final String TAG = VillagesListAdapter.class.getSimpleName();
    private List<Village> mDataList;
    private Context mContext;

    public VillagesListAdapter(Context context, List<Village> dataList) {
        mDataList = dataList;
        mContext = context;
    }

    @Override
    public VillageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VillageHolder(LayoutInflater.from(mContext).inflate(R.layout.dialog_li_village_list, parent, false));
    }

    @Override
    public void onBindViewHolder(VillageHolder holder, int position) {
        if (mDataList != null && mDataList.size() > 0) {
            Village village = mDataList.get(position);
            holder.placeDataToControl(village);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mDataList){
            return mDataList.size();
        }
        return 0;
    }

    class VillageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckBox cbSelectedVillage;
        private Village village;

        private VillageHolder(View itemView) {
            super(itemView);
            cbSelectedVillage = itemView.findViewById(R.id.cb_village);
        }

        void placeDataToControl(Village village) {
            if (null == village) {
                Log.e(TAG, "Found null village");
                return;
            }
            this.village = village;
            cbSelectedVillage.setText(village.getMarVillageName());
            cbSelectedVillage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cb_village:
                    SharedPreferences preferences = mContext.getSharedPreferences(ICommonConstants.KEY_SHARED_PREFERENCES, mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    String villagesArray;
                    if (preferences.contains(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST)) {
                        villagesArray = preferences.getString(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, "");
                        String replaced = villagesArray.replace("]", ",");
                        replaced = replaced + village.getEnVillageName() + "\"]";
                        editor.putString(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, replaced);

                    } else {
                        villagesArray = "[\"" + village.getEnVillageName() + "\"]";
                        editor.putString(ICommonConstants.KEY_PREFERENCES_VILLAGE_LIST, villagesArray);
                    }
                    editor.apply();
                    break;
            }
        }
    }
}
