package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

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
        //retrieving messages List thanks to a callback function because FireBase listeners are asynchronous
        messagedao.INSTANCE.getAll(new FirebaseCallbackGetMessage() {
            @Override
            public void onCallbackGetMessages(List<Message> messages) {
                //recyclerView
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);

                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                // specify an adapter
                MessageAdapter adapter = new MessageAdapter(messages , getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() -1);
            }
        });
    }


    @Override
    public void onClick(View view) {
        messagedao.saveMessage(messageContent.getText().toString(), new FirebaseCallbackSaveMessage() {
            @Override
            public void onCallbackSaveMessage(Message message) {
                FirebaseDatabase.getInstance().getReference().child("messages").child(messageContent.getText().toString()).setValue(message);
            }
        });
        finish();
        startActivity(new Intent(MessageActivity.this, MessageActivity.class));
    }
}
