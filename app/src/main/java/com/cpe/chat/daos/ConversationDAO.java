package com.cpe.chat.daos;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpe.chat.firebaseInterfaces.FirebaseCallbackCheckConversationExistence;

import com.cpe.chat.model.Message;
import com.cpe.chat.model.PrivateConversation;
import com.cpe.chat.model.UserDetails;
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
import java.util.regex.Pattern;

public enum ConversationDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private FirebaseUser user;
    private UserDAO userdao = UserDAO.INSTANCE;
    private String nickname;
    private String color;
    private List<Message> chat;
    private List<String> userIds;

    public void checkConversationExistence(final String userToChatId, final FirebaseCallbackCheckConversationExistence firebaseCallbackCheckConversationExistence){

        user = mAuth.getInstance().getCurrentUser();
        reference = db.getReference().child("privateConv");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existence = false;
                Iterable<DataSnapshot> conversationIDs = dataSnapshot.getChildren();
                for(DataSnapshot conv : conversationIDs){
                    //check if conversation ID contains both emails
                    boolean b1 = Pattern.matches(".*" +userToChatId + ".*", conv.getKey());
                    boolean b2 = Pattern.matches(".*" +user.getUid() + ".*", conv.getKey());
                    if (b1 && b2){
                        existence = true;
                    }
                }
                firebaseCallbackCheckConversationExistence.onCallbackCheckConversationExistence(existence);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void createConversation(final String userToChatId){
        reference = db.getReference().child("privateConv");

        //Get Auth Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        //the conversation id will be user1Id - user2Id in alphabetical order
        userIds = new ArrayList<>();
        userIds.add(user.getUid());
        userIds.add(userToChatId);
        java.util.Collections.sort(userIds);
        String conversationId = userIds.get(0) + "-" + userIds.get(1);
        Log.d("convID", conversationId);

        //get time and create a string for it
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date = mdformat.format(calendar.getTime());

        //instance of conversation
        chat = new ArrayList<>();
        //chat.add(new Message(user.getUid(), "conncetion success", user.getUid()+ "has started conv with " +userToChatId , date, "#FFFFFF"));
        PrivateConversation conversation = new PrivateConversation(user.getUid(),userToChatId, chat );
        Log.d("convInstance", conversation.getUser1() + conversation.getUser2() + conversation.getMessages());

        //save to db
        reference = db.getReference().child("privateConv");
        Log.d("reference1", reference.toString());
        reference.child(conversationId).setValue(conversation);
        Log.d("savedb", "data ok");
    }

}
