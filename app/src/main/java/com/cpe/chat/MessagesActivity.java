package com.cpe.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        List<String> messages = new ArrayList<>();

        messages.add("Hello world");
        messages.add("Hello world");
        messages.add("Hello world");
        messages.add("Hello world");
        messages.add("Hello world");
        messages.add("Hello world");

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        MessageAdapter adapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(adapter);
    }
}
