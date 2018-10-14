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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;

public enum MessageDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private List<Message> chat;
    private FirebaseUser user;
    private UserDAO userdao = UserDAO.INSTANCE;

    public List<Message> getAll() {
        user = mAuth.getInstance().getCurrentUser();
        chat = new ArrayList<>();
        reference = db.getReference().child("messages");
        reference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat.clear();
                Iterable<DataSnapshot> messageIDs = dataSnapshot.getChildren();
                for(DataSnapshot msg : messageIDs){

                    //this should work but doesn't for no reason
                    //Message message = msg.getValue(Message.class);

                    //retrieving element one by one...
                    String id = msg.child("id").getValue(String.class);
                    String messageContent = msg.child("messageContent").getValue(String.class);
                    String senderNickname = msg.child("senderNickname").getValue(String.class);
                    String date = msg.child("date").getValue(String.class);
                    chat.add(new Message(id, senderNickname, messageContent, date));
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

        //get the nickname
        String nickname = userdao.INSTANCE.recoverNickname();

        //get time and create a string for it
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date = mdformat.format(calendar.getTime());

        //instance of message
        Message message = new Message(user.getUid(), user.getEmail(), messageContent, date);

        //save to db
        reference = db.getReference().child("messages");
        reference.child(messageContent).setValue(message);
    }
}
