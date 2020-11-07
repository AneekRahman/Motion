package com.motion.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.motion.app.Classes.ExploreGrid;
import com.motion.app.Classes.MyUtils;
import com.motion.app.R;

import java.util.List;

public class ExploreGridAdapter extends BaseAdapter {

    // Given context, posts
    private Context mContext;
    private List<ExploreGrid> mPosts;

    // Class instance constructor
    public ExploreGridAdapter(Context c, List<ExploreGrid> posts) {
        mContext = c;
        this.mPosts = posts;
    }

    // Needed method
    @Override
    public int getCount() {
        return mPosts.size();
    }
    // Needed method
    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }
    // Needed method
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflating the profile grid layout for each of the girds
        convertView = LayoutInflater.from(mContext).inflate(R.layout.explore_grid_layout, null);

        ExploreGrid post = mPosts.get(position);

        // Connecting the grid layouts view to code
        RelativeLayout mainHolder = (RelativeLayout) convertView.findViewById(R.id.main_holder);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.username);
        TextView viewsCount = (TextView) convertView.findViewById(R.id.views_count);

        // Setting a fixed size to the gird
        setGridSize(mainHolder);

        // Showing the user post gif
        Glide.with(mContext)
                .load(post.getContentUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);


        userNameTextView.setText("@" + post.getUserName());
        viewsCount.setText(String.valueOf(MyUtils.formatNumbers(post.getViewsCount())) + " views");

        return convertView;

    }

    // The method for setting a fixed size to the gird
    private void setGridSize(RelativeLayout v){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixelsFrom8Dp = (int) (4 * scale + 0.5f);

        float adjustedWidth = ( displayWidth - pixelsFrom8Dp ) / 3;
        int adjustedHeight = (int) (adjustedWidth * 1.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) adjustedWidth,(int) adjustedWidth);
        v.setLayoutParams(params);

    }


}