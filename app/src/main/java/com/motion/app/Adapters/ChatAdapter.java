package com.motion.app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.motion.app.Classes.ChatRow;
import com.motion.app.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    // TODO for testing
    private int own_users_id = 1;

    private static final int TYPE_OWN_MSG = 0;
    private static final int TYPE_OTHERS_MSG = 1;

    private List<ChatRow> mChatList;
    Context mContext;

    public class OwnMsgViewHolder extends RecyclerView.ViewHolder {

        TextView mMsg;

        public OwnMsgViewHolder(View itemView) {
            super(itemView);

            mMsg = (TextView) itemView.findViewById(R.id.msg);

        }
    }

    public class OthersMsgViewHolder extends RecyclerView.ViewHolder {

        ImageView mDp;
        TextView mMsg;
        View mPointer;

        public OthersMsgViewHolder(View itemView) {
            super(itemView);

            mDp = (ImageView) itemView.findViewById(R.id.other_users_dp);
            mMsg = (TextView) itemView.findViewById(R.id.msg);
            mPointer = (View) itemView.findViewById(R.id.pointer);


        }

    }

    public ChatAdapter(List<ChatRow>data, Context context) {
        this.mChatList = data;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case TYPE_OWN_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_own_layout, parent, false);
                return new OwnMsgViewHolder(view);
            case TYPE_OTHERS_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_others_layout, parent, false);
                return new OthersMsgViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        ChatRow chatRow = mChatList.get(position);

        if(chatRow.getMsgUserID() == own_users_id){

            return TYPE_OWN_MSG;

        }else{

            return TYPE_OTHERS_MSG;

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        ChatRow chatRow = mChatList.get(listPosition);

        if(chatRow.getMsgUserID() == own_users_id){
            // Own users msg

            setOwnUserMsg(holder, listPosition);


        }else{

            setOtherUsersMsg(holder, listPosition);


        }

    }

    private void setOwnUserMsg(RecyclerView.ViewHolder holder, int position){

        OwnMsgViewHolder viewHolder = (OwnMsgViewHolder)holder;
        ChatRow chatRow = mChatList.get(position);

        viewHolder.mMsg.setText(chatRow.getChatMsg());


    }

    private void setOtherUsersMsg(RecyclerView.ViewHolder holder, int position){

        OthersMsgViewHolder viewHolder = (OthersMsgViewHolder)holder;
        ChatRow chatRow = mChatList.get(position);

        if(position != 0){
            ChatRow prevChatRow = mChatList.get(position - 1);

            if(prevChatRow.getMsgUserID() == chatRow.getMsgUserID()){

                // Not the first msg

                viewHolder.mDp.setVisibility(View.GONE);
                viewHolder.mPointer.setVisibility(View.GONE);

            }else{

                // The first msg

                Glide.with(mContext)
                        .load(chatRow.getMsgUserDpUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(viewHolder.mDp);

            }
        }else{

            // The first msg

            Glide.with(mContext)
                    .load(chatRow.getMsgUserDpUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.mDp);

        }

        viewHolder.mMsg.setText(chatRow.getChatMsg());


    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }
}
