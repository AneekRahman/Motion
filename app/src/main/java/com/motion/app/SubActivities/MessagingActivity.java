package com.motion.app.SubActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.motion.app.Adapters.ChatAdapter;
import com.motion.app.Adapters.HomeRecyclerAdapter;
import com.motion.app.Classes.ChatRow;
import com.motion.app.Classes.HomePost;
import com.motion.app.R;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {

    private ImageView mMessageToUserDp, mBackBtn;
    private RecyclerView mRecyclerView;

    // Class reference
    private List<ChatRow> mChatList = new ArrayList<>();
    private ChatAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // User to which current user is messaging
    private String userToMsgDpUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mMessageToUserDp = (ImageView) findViewById(R.id.message_to_user_dp);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setMessageToUserCredentials();
        setOnClickListeners();
        // Setting up recycler
        mAdapter = new ChatAdapter(mChatList, this);
        setUpRecyclerView();

        setTestMsgs();

    }

    private void setMessageToUserCredentials(){

        userToMsgDpUrl = "https://www.wikihow.com/images/thumb/8/80/Get-Over-a-Girl-That-Left-You-for-Another-Guy-%28Teens%29-Step-2.jpg/aid74129-v4-728px-Get-Over-a-Girl-That-Left-You-for-Another-Guy-%28Teens%29-Step-2.jpg";

        Glide.with(this)
                .load(userToMsgDpUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(mMessageToUserDp);

    }

    private void setUpRecyclerView(){

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setOnClickListeners(){

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


    }

    private void setTestMsgs(){

        ChatRow chatRow = new ChatRow(0,
                2,
                "I'm not aneek bro",
                userToMsgDpUrl,
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                2,
                "I'm not aneek again bro, I'm someone else, Who? IDK myself :D",
                userToMsgDpUrl,
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                1,
                "Aneek's here baby :D Waddup?",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSStSkWpZMKQkWpCwN_2i2g03JYYxB8Iolxg-_NAs-1a40hTDX5",
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                2,
                "Nothing much aneek bro, just doing some test msgs yk",
                userToMsgDpUrl,
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                2,
                "Its not really that fun :/ i dont like it",
                userToMsgDpUrl,
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                2,
                "YOU'RE NOT SO FUNNY MAN, get over it :/ Youre like a rotten tomato, you stink, and really bad too, dont like it, wont like it, hate it like how i hate this stinking life of mine :'D",
                userToMsgDpUrl,
                "2:34PM");
        mChatList.add(chatRow);

        chatRow = new ChatRow(0,
                1,
                "Okay non-Aneek bro, Youre like a rotten tomato, you stink, and really bad too, dont like it, wont like it, hate it like how i hate this stinking life of mine",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSStSkWpZMKQkWpCwN_2i2g03JYYxB8Iolxg-_NAs-1a40hTDX5",
                "2:34PM");
        mChatList.add(chatRow);

        mAdapter.notifyDataSetChanged();

    }

}
