package com.cpe.chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;

public enum MessageDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private List<Message> chat;
    private FirebaseUser user;

    public List<Message> getAll() {
        user = mAuth.getInstance().getCurrentUser();
        chat = new ArrayList<>();
        reference = db.getReference().child("messages");
        Log.d("aaa", "This is my message");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat.clear();
                Iterable<DataSnapshot> messageIDs = dataSnapshot.getChildren();
                for(DataSnapshot msg : messageIDs){
                    String key=msg.getKey();
                    Log.d("ggg",key);
                    Message message = msg.getValue(Message.class);
                    Log.d("ccc", message.getId() + message.getSenderNickname() + message.getMessageContent());
                    chat.add(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ddd", "json retrieving failed");
            }
        });

        return chat;
    }

    public void saveMessage(String messageContent){
        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        //instance of message
        Message message = new Message(user.getUid(), user.getEmail(), messageContent);

        //save to db
        reference = db.getReference().child("messages");
        reference.child(messageContent).setValue(message);
    }
}
