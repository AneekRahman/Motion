package com.motion.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.motion.app.Adapters.ExploreGridAdapter;
import com.motion.app.Adapters.TrendingBoxAdapter;
import com.motion.app.Classes.ExpandableHeightGridView;
import com.motion.app.Classes.ExploreGrid;
import com.motion.app.Classes.MyUtils;
import com.motion.app.Classes.TrendingBoxItem;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    // Views reference
    private LinearLayout mMainLinearHolder;
    private ExpandableHeightGridView mGridView;
    private ImageView mGlobalToggle;
    private RecyclerView mTrendingsRecycler;

    // Class reference
    private boolean globalEnabled = false;

    // Trending recycler adapter
    private TrendingBoxAdapter mTrendingAdapter;
    private List<TrendingBoxItem> mTrendings = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    // Grid posts list and adapter
    private ExploreGridAdapter mAdapter;
    private List<ExploreGrid> mPosts = new ArrayList<>();

    public ExploreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_explore, container, false);

        mMainLinearHolder = (LinearLayout) rootview.findViewById(R.id.main_linear_holder);
        mGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.grid_view);
        mGlobalToggle = (ImageView) rootview.findViewById(R.id.global_toggle);
        mTrendingsRecycler = (RecyclerView) rootview.findViewById(R.id.trendings_box_recycler);

        mMainLinearHolder.setPadding(0, MyUtils.getStatusBarHeight(getContext()), 0, 0);

        // Setting up trendings
        setUpTrendingsRecycler();
        insertTrendings();

        // Setting up grid
        setUpGrid();
        setUpClickListeners();
        insertPostsToAdapter();

        return rootview;

    }

    private void setUpTrendingsRecycler(){

        mTrendingAdapter = new TrendingBoxAdapter(getContext(), mTrendings);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTrendingsRecycler.setLayoutManager(mLayoutManager);
        mTrendingsRecycler.setItemAnimator(new DefaultItemAnimator());
        mTrendingsRecycler.setAdapter(mTrendingAdapter);
        mTrendingsRecycler.setFocusable(false);
        mTrendingsRecycler.setNestedScrollingEnabled(false);

    }

    private void setUpGrid(){

        // Setting up the Gridview and its adapter
        mAdapter = new ExploreGridAdapter(getContext(), mPosts);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);
        mGridView.setFocusable(false);

    }

    private void setUpClickListeners(){

        if(globalEnabled){
            mGlobalToggle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_orbit_icon_filled));
        }

        mGlobalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(globalEnabled){

                    mGlobalToggle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_orbit_icon_stroke));
                    globalEnabled = false;
                    Toast.makeText(getContext(), "Showing personalized results", Toast.LENGTH_SHORT).show();

                }else{

                    mGlobalToggle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_orbit_icon_filled));
                    globalEnabled = true;
                    Toast.makeText(getContext(), "Showing global results", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void insertPostsToAdapter(){

        mPosts.clear();

        ExploreGrid post = new ExploreGrid("aneek_rahman", 7957357366L, "https://cdn.images.express.co.uk/img/dynamic/143/590x/No-Man-s-Sky-gets-alternative-covers-689362.jpg");
        mPosts.add(post);

        for (int i = 0; i < 20; i++){

            post = new ExploreGrid("aneek_rahman", 7957357366L, "https://cdn.images.express.co.uk/img/dynamic/143/590x/No-Man-s-Sky-gets-alternative-covers-689362.jpg");
            mPosts.add(post);

        }

        mAdapter.notifyDataSetChanged();

    }

    private void insertTrendings(){

        mTrendings.clear();

        TrendingBoxItem item = new TrendingBoxItem();
        mTrendings.add(item);

        item = new TrendingBoxItem();
        mTrendings.add(item);

        item = new TrendingBoxItem();
        mTrendings.add(item);

        item = new TrendingBoxItem();
        mTrendings.add(item);

        item = new TrendingBoxItem();
        mTrendings.add(item);

        item = new TrendingBoxItem();
        mTrendings.add(item);

    }

}
