package com.cpe.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends AppCompatActivity {

    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("id1", "nickname1", "messageContent1"));
        messages.add(new Message("id2", "nickname2", "messageContent2"));
        messages.add(new Message("id3", "nickname3", "messageContent3"));

        return messages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(this, "test enter", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        List<Message> messages = getAll();
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        MessageAdapter adapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(adapter);
    }
}
