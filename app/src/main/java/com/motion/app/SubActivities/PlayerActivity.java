package com.motion.app.SubActivities;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.motion.app.Classes.CacheDataSourceFactory;
import com.motion.app.Classes.MyUtils;
import com.motion.app.R;

public class PlayerActivity extends AppCompatActivity {

    // View declarations
    private SwipeRefreshLayout mRefreshView;
    private SimpleExoPlayerView mPlayerView;
    private TextView mDescTextView;
    private RecyclerView mUpNextRecycler;

    // Exoplayer declarations
    private String vidAddress;
    private SimpleExoPlayer mExoPlayer;
    private MediaSource mMediaSource;
    private int mExoPlayerWindowIndex = 0;
    private long mPlaybackPosition = 0;
    private boolean mActivityPaused = false;

    // Up next declarations


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.main_holder);
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        mDescTextView = (TextView) findViewById(R.id.desc);
        mUpNextRecycler = (RecyclerView) findViewById(R.id.up_next_recycler);

        mRefreshView.setPadding(0, MyUtils.getStatusBarHeight(this), 0, 0);

        setVideoUserCredentials();
        initializeExoPlayer();


    }

    private void setVideoUserCredentials(){

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            setVidAdress(bundle.getString("vidAddress"));
        }

        mDescTextView.setText( MyUtils.getTrimmedText(mDescTextView, getString(R.string.exampleText), 200, "... ","See More") );
        // For spannable see more text click event
        mDescTextView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void setVidAdress(String vidAdress){

        this.vidAddress = vidAdress;

    }

    // Initializing the posts exoplayer
    public void initializeExoPlayer(){

        if(mExoPlayer == null){

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            setExoPlayerListeners();

            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mExoPlayerWindowIndex, mPlaybackPosition);
            mExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);

            prepareExoPlayer();
        }

    }

    // Preparing the exoplayer with the video address
    private void prepareExoPlayer(){

        if(vidAddress == null) return;
        Uri uri = Uri.parse(vidAddress);
        mMediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mMediaSource, false, false);


    }

    // Building mediasource for exoplayer
    private MediaSource buildCacheMediaSource(Uri uri) {

        return new ExtractorMediaSource(uri,
                new CacheDataSourceFactory(this, 100 * 1024 * 1024, 5 * 1024 * 1024),
                new DefaultExtractorsFactory(),
                null,
                null);

    }

    // Building mediasource for exoplayer
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ripple-exo")).createMediaSource(uri);
    }

    private void setExoPlayerListeners(){

        mExoPlayer.addListener(new Player.EventListener() {

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                // Giving Player state response to user
                switch (playbackState){

                    case ExoPlayer.STATE_READY: {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                    break;
                    case ExoPlayer.STATE_BUFFERING: {}
                    case ExoPlayer.STATE_ENDED: {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                    case ExoPlayer.STATE_IDLE: {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                    break;
                }

            }

            // On error keep on trying to ready the player on and on (This happens when internet connection unintentionally goes)
            @Override
            public void onPlayerError(ExoPlaybackException error) {

                mExoPlayer.prepare(mMediaSource, false, false);

            }
            // Not needed default methods
            @Override public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}
            @Override public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
            @Override public void onLoadingChanged(boolean isLoading) {}
            @Override public void onPositionDiscontinuity(int reason) {}
            @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
            @Override public void onSeekProcessed() {}
            @Override public void onRepeatModeChanged(int repeatMode) {}
            @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
        });

        mExoPlayer.setVideoListener(new SimpleExoPlayer.VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

                float scale = (float) width / (float) MyUtils.getWindowSize(getApplicationContext(), false);

                mPlayerView.getLayoutParams().height = (int) ( (float) height / scale);
                mPlayerView.requestLayout();

            }

            @Override
            public void onRenderedFirstFrame() {

            }
        });



    }

    public void releasePlayer() {

        if(mExoPlayer != null) {

            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mExoPlayerWindowIndex = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(mActivityPaused)
                initializeExoPlayer();
            mActivityPaused = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23)) {
            if(mActivityPaused)
                initializeExoPlayer();
            mActivityPaused = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
            mActivityPaused = true;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
            mActivityPaused = true;
        }
    }


}
