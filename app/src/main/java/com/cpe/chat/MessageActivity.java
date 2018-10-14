package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText messageContent;
    private Button button_send_message;
    private MessageDAO messagedao = MessageDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //Find Views
        messageContent = (EditText) findViewById(R.id.input_message);
        button_send_message = (Button) findViewById(R.id.button_send_message);

        //Listeners
        button_send_message.setOnClickListener(this);

        //recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        List<Message> messages = MessageDAO.INSTANCE.getAll();

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        MessageAdapter adapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        messagedao.saveMessage(messageContent.getText().toString());
        startActivity(new Intent(MessageActivity.this, MessageActivity.class));
    }
}
