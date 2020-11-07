package com.motion.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ui.PlayerView;
import com.motion.app.Classes.HomePost;
import com.motion.app.R;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter {

    private List<HomePost> mPostList;
    Context mContext;

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView mPostImageView, mUserDp, mHeartButton;
        CardView mContentHolder;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            mContentHolder = (CardView) itemView.findViewById(R.id.post_content_holder);
            mPostImageView = (ImageView) itemView.findViewById(R.id.post_image);
            mUserDp = (ImageView) itemView.findViewById(R.id.user_dp);
            mHeartButton = (ImageView) itemView.findViewById(R.id.heart_button);

        }
    }

    public static class VideoTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView mUninitializedOverlay;
        PlayerView mPlayerView;

        public VideoTypeViewHolder(View itemView) {
            super(itemView);

            mUninitializedOverlay = (ImageView) itemView.findViewById(R.id.post_video_uninitialized_overlay);
            mPlayerView = (PlayerView) itemView.findViewById(R.id.player_view);

        }
    }

    public HomeRecyclerAdapter(List<HomePost>data, Context context) {
        this.mPostList = data;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case HomePost.TYPE_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_video_post_layout, parent, false);
                return new VideoTypeViewHolder(view);
            case HomePost.TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_image_post_layout, parent, false);
                return new ImageTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (mPostList.get(position).getPostType()) {
            case HomePost.TYPE_VIDEO:
                return HomePost.TYPE_VIDEO;
            case HomePost.TYPE_IMAGE:
                return HomePost.TYPE_IMAGE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        HomePost post = mPostList.get(listPosition);

        if (post != null) {

            // User dp
            Glide.with(mContext)
                    .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSStSkWpZMKQkWpCwN_2i2g03JYYxB8Iolxg-_NAs-1a40hTDX5")
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageTypeViewHolder) holder).mUserDp);

            // Post image
            Glide.with(mContext)
                    .load(post.getContentUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(((ImageTypeViewHolder) holder).mPostImageView);

            setImageOnClickListeners(holder);
            setImageHolderHeight(holder);

        }
    }

    private void setImageOnClickListeners(final RecyclerView.ViewHolder holder){

        ((ImageTypeViewHolder) holder).mHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ImageTypeViewHolder) holder).mHeartButton.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart_filled_red));

            }
        });

    }

    // Setting a fixed height for the root view
    private void setImageHolderHeight(RecyclerView.ViewHolder holder){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (8 * scale + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, widthPixels - pixels);
        ((ImageTypeViewHolder) holder).mContentHolder.setLayoutParams(params);

    }



    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}