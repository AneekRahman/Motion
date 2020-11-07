package com.motion.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.motion.app.Classes.TrendingBoxItem;
import com.motion.app.R;

import java.util.List;

public class TrendingBoxAdapter extends RecyclerView.Adapter<TrendingBoxAdapter.ViewHolder> {

    private List<TrendingBoxItem> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public TrendingBoxAdapter(Context context, List<TrendingBoxItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.trending_now_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String animal = mData.get(position);

        holder.setGridSize();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardHolder;

        ViewHolder(View itemView) {
            super(itemView);

            mCardHolder = itemView.findViewById(R.id.card_holder);

        }

        // The method for setting a fixed size to the gird
        private void setGridSize(){

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int displayWidth = displayMetrics.widthPixels;

            final float scale = mContext.getResources().getDisplayMetrics().density;
            int pixelsFrom8Dp = (int) (80 * scale + 0.5f);

            float adjustedWidth = ( displayWidth - pixelsFrom8Dp ) / 2;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) adjustedWidth,(int) adjustedWidth);
            mCardHolder.setLayoutParams(params);

        }

    }

}
