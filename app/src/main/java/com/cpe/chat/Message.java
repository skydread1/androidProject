package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    private String id;
    private String senderNickname;
    private String messageContent;
    private String date;

    public Message(String id, String senderNickname, String messageContent, String date) {
        this.id = id;
        this.senderNickname = senderNickname;
        this.messageContent = messageContent;
        this.date = date;
    }

    public String getDate() { return date; }

    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }

    public void setDate(String date) { this.date = date; }

    public String getId() {
        return id;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }


}



