package com.motion.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.motion.app.Classes.ExploreGrid;
import com.motion.app.Classes.MyUtils;
import com.motion.app.Classes.ProfileGrid;
import com.motion.app.R;

import java.util.List;

public class ProfileGridAdapter extends BaseAdapter {

    // Given context, posts
    private Context mContext;
    private List<ProfileGrid> mPosts;

    // Class instance constructor
    public ProfileGridAdapter(Context c, List<ProfileGrid> posts) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.profile_grid_layout, null);

        ProfileGrid post = mPosts.get(position);

        // Connecting the grid layouts view to code
        RelativeLayout imageHolder = (RelativeLayout) convertView.findViewById(R.id.imageview_holder);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);

        // Setting a fixed size to the gird
        setGridSize(imageHolder);

        // Showing the user post gif
        Glide.with(mContext)
                .load(post.getContentUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);

        return convertView;

    }

    // The method for setting a fixed size to the gird
    private void setGridSize(RelativeLayout v){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixelsFrom8Dp = (int) (30 * scale + 0.5f);

        float adjustedWidth = ( displayWidth - pixelsFrom8Dp ) / 2;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) adjustedWidth,(int) adjustedWidth);
        v.setLayoutParams(params);

    }


}