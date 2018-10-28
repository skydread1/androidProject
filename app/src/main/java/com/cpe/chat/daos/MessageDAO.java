package com.cpe.chat.daos;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpe.chat.model.Message;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetMessage;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackSaveMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum MessageDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private List<Message> chat;
    private FirebaseUser user;
    private UserDAO userdao = UserDAO.INSTANCE;
    private String nickname;
    private String color;
    private List<String> userIds;

    public void getAllGeneralMessages(final FirebaseCallbackGetMessage firebaseCallbackGetMessage) {
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
                    String color = msg.child("color").getValue(String.class);
                    chat.add(new Message(id, senderNickname, messageContent, date, color));
                }

                //sending the messages list to the callback to overpass the asynchronous issue
                if(!chat.isEmpty()){
                    firebaseCallbackGetMessage.onCallbackGetMessages(chat);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ddd", "json retrieving failed");
            }
        });
    }

    public void getAllPrivateMessages(final String userToChatId, final FirebaseCallbackGetMessage firebaseCallbackGetMessage) {
        user = mAuth.getInstance().getCurrentUser();
        chat = new ArrayList<>();
        //build the conv id
        //the conversation id will be user1Id - user2Id in alphabetical order
        userIds = new ArrayList<>();
        userIds.add(user.getUid());
        userIds.add(userToChatId);
        java.util.Collections.sort(userIds);
        String conversationId = userIds.get(0) + "-" + userIds.get(1);

        reference = db.getReference().child("privateConv").child(conversationId).child("messages");
        reference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat.clear();
                Iterable<DataSnapshot> messageIDs = dataSnapshot.getChildren();
                for(DataSnapshot msg : messageIDs){

                    //retrieving elements one by one...
                    String id = msg.child("id").getValue(String.class);
                    String messageContent = msg.child("messageContent").getValue(String.class);
                    String senderNickname = msg.child("senderNickname").getValue(String.class);
                    String date = msg.child("date").getValue(String.class);
                    String color = msg.child("color").getValue(String.class);
                    chat.add(new Message(id, senderNickname, messageContent, date, color));
                }

                //sending the messages list to the callback to overpass the asynchronous issue
                if(!chat.isEmpty()){
                    firebaseCallbackGetMessage.onCallbackGetMessages(chat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ddd", "json retrieving failed");
            }
        });
    }

    public void prepareMessage(final String messageContent, final FirebaseCallbackSaveMessage firebaseCallbackSaveMessage){
        //Because we want to display the nickname, we need to save our message in the
        //firebase addValueEventListener method because the method is asynchronous
        nickname = new String();
        color = new String();
        reference = db.getReference().child("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get username and color
                Iterable<DataSnapshot> usersIDs = dataSnapshot.getChildren();
                for(DataSnapshot item : usersIDs){
                    if(item.getKey().equals(user.getUid())){
                        nickname = item.child("nickname").getValue(String.class);
                        color =  item.child("color").getValue(String.class);
                    }
                }

                //Get Auth Reference
                FirebaseUser user = mAuth.getInstance().getCurrentUser();

                //get time and create a string for it
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String date = mdformat.format(calendar.getTime());

                //instance of message
                Message message = new Message(user.getUid(), nickname, messageContent, date, color);

                //save to db
                //saving the message via callback
                firebaseCallbackSaveMessage.onCallbackSaveMessage(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void saveGeneralMessage(Message message){
        FirebaseDatabase.getInstance().getReference().child("messages").child(message.getMessageContent()).setValue(message);
    }

    public void savePrivateMessage(Message message, String userToChatId){
        user = mAuth.getInstance().getCurrentUser();
        chat = new ArrayList<>();
        //build the conv id
        //the conversation id will be user1Id - user2Id in alphabetical order
        userIds = new ArrayList<>();
        userIds.add(user.getUid());
        userIds.add(userToChatId);
        java.util.Collections.sort(userIds);
        String conversationId = userIds.get(0) + "-" + userIds.get(1);

        reference = db.getReference().child("privateConv").child(conversationId).child("messages").child(message.getMessageContent());
        reference.setValue(message);
    }
}
