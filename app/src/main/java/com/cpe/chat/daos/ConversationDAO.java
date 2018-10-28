package com.cpe.chat.daos;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsers;
import com.cpe.chat.model.Message;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetMessage;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackSaveMessage;
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

public enum ConversationDAO {
    INSTANCE;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private List<UserDetails> userDetailsList;
    private FirebaseUser user;
    private UserDAO userdao = UserDAO.INSTANCE;
    private String nickname;
    private String color;

    public void getAllUser(final FirebaseCallbackGetUsers firebaseCallbackGetUsers) {
        user = mAuth.getInstance().getCurrentUser();
        userDetailsList = new ArrayList<>();
        reference = db.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDetailsList.clear();
                Iterable<DataSnapshot> usersIDs = dataSnapshot.getChildren();
                for(DataSnapshot item : usersIDs){

                    //retrieving element one by one...
                    String color = item.child("color").getValue(String.class);
                    String email = item.child("email").getValue(String.class);
                    String nickname = item.child("nickname").getValue(String.class);
                    String uid = item.child("uid").getValue(String.class);
                    Log.d("mama", color + email + nickname + uid);
                    userDetailsList.add(new UserDetails(uid, email, nickname, color));
                }

                //sending the messages list to the callback to overpass the asynchronous issue
                if(!userDetailsList.isEmpty()){
                    firebaseCallbackGetUsers.onCallbackGetUsers(userDetailsList);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ddd", "json retrieving failed");
            }
        });
    }

    /*public void saveConversation(final String messageContent, final FirebaseCallbackSaveMessage firebaseCallbackSaveMessage){
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
    }*/
}
