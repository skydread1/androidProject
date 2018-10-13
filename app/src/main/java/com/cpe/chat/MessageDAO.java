package com.cpe.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public enum MessageDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("id1", "nickname1", "messageContent1"));

        return messages;
    }

    public void saveMessage(String messageContent){
        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        //instance of message
        Message message = new Message(user.getUid(), user.getEmail(), messageContent);

        //save to db
        databaseReference = db.getReference().child("messages");
        databaseReference.child(user.getUid()).setValue(message);
    }
}
