package com.cpe.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {
    private String id;
    private String senderNickname;
    private String message;

    public Message(String id, String senderNickname, String message) {
        this.id = id;
        this.senderNickname = senderNickname;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


