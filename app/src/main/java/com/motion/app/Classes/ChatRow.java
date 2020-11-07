package com.motion.app.Classes;

public class ChatRow {

    int chatMsgID, msgUserID;
    String chatMsg, msgUserDpUrl, msgTime;

    public ChatRow(int chatMsgID, int msgUserID, String chatMsg, String msgUserDpUrl, String msgTime){

        this.chatMsgID = chatMsgID;
        this.msgUserID = msgUserID;
        this.chatMsg = chatMsg;
        this.msgUserDpUrl = msgUserDpUrl;
        this.msgTime = msgTime;

    }

    public int getChatMsgID() {
        return chatMsgID;
    }

    public int getMsgUserID() {
        return msgUserID;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public String getMsgUserDpUrl() {
        return msgUserDpUrl;
    }

    public String getMsgTime() {
        return msgTime;
    }

}
