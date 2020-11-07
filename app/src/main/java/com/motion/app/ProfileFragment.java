package com.motion.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.motion.app.Adapters.ExploreGridAdapter;
import com.motion.app.Adapters.ProfileGridAdapter;
import com.motion.app.Classes.ExpandableHeightGridView;
import com.motion.app.Classes.ExploreGrid;
import com.motion.app.Classes.MyUtils;
import com.motion.app.Classes.ProfileGrid;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    // Views reference
    private LinearLayout mCoverHolder;
    private ImageView mUserDp, mCover;
    private ExpandableHeightGridView mGridView;

    // Profile grid posts list and adapter
    private ProfileGridAdapter mAdapter;
    private List<ProfileGrid> mPosts = new ArrayList<>();

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        mCoverHolder = (LinearLayout) rootview.findViewById(R.id.cover_holder);
        mUserDp = (ImageView) rootview.findViewById(R.id.user_dp);
        mCover = (ImageView) rootview.findViewById(R.id.cover);
        mGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.grid_view);


        setUserCredentials();
        setUpGrid();

        // TODO testing
        insertPostsToAdapter();

        return rootview;

    }

    private void setUserCredentials(){

        // User cover
        mCoverHolder.setPadding(0, MyUtils.getStatusBarHeight(getContext()), 0, 0);
        Glide.with(getContext())
                .load("https://static1.squarespace.com/static/56e69b2c7c65e415097626ed/t/5aaa172024a694c63a8606a9/1521096493186/Demon+Days+April+Tour+Landscape.jpg")
                .apply(RequestOptions.centerCropTransform())
                .into(mCover);

        // User dp
        Glide.with(getContext())
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSStSkWpZMKQkWpCwN_2i2g03JYYxB8Iolxg-_NAs-1a40hTDX5")
                .apply(RequestOptions.circleCropTransform())
                .into(mUserDp);

    }

    private void setUpGrid(){

        // Setting up the Gridview and its adapter
        mAdapter = new ProfileGridAdapter(getContext(), mPosts);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);
        mGridView.setFocusable(false);

    }

    private void insertPostsToAdapter(){

        mPosts.clear();

        ProfileGrid post = new ProfileGrid(7957357366L, "https://media.giphy.com/media/9rns5t08XvXn8rYp35/giphy.gif");
        mPosts.add(post);

        for (int i = 0; i < 20; i++){

            post = new ProfileGrid(7957357366L, "https://media.giphy.com/media/9rns5t08XvXn8rYp35/giphy.gif");
            mPosts.add(post);

        }

        mAdapter.notifyDataSetChanged();

    }


}
