package com.cpe.chat.daos;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpe.chat.firebaseInterfaces.FirebaseCallbackCheckUserExistence;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsers;
import com.cpe.chat.model.UserDetails;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsername;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public enum UserDAO{
    INSTANCE;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference();
    private FirebaseUser userAuth;
    private String userName;
    private List<UserDetails> userDetailsList;

    public void getUsernameFromDB(final FirebaseCallbackGetUsername firebaseCallbackGetUsername) {
        userAuth = mAuth.getInstance().getCurrentUser();
        userName = new String();
        reference = db.getReference().child("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child(userAuth.getUid()).child("nickname").getValue(String.class);
                //sending the messages list to the callback to overpass the asynchronous issue
                firebaseCallbackGetUsername.onCallbackGetUsername(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ddd", "json retrieving failed");
            }
        });
    }




    public String getUserEmail(){
        userAuth = mAuth.getInstance().getCurrentUser();
        return userAuth.getEmail();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void saveUser(){

        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        //creation of a new user
        UserDetails userDetails = new UserDetails(user.getUid(), user.getEmail(), user.getEmail(), "#FFFFFF");

        reference = db.getReference().child("users");
        reference.child(user.getUid()).setValue(userDetails);
    }

    public void updateUserUsername(String username){

        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        reference = db.getReference().child("users");
        Log.d("reference1", user.getUid() );
        reference.child(user.getUid()).child("nickname").setValue(username);
    }

    public void updateUserColor(String color){

        boolean trueHexcolor = checkColor(color);
        //if color conforms hex code do
        if (trueHexcolor == true ){
            Log.d("myTag", "TRUE");
            //Get Database Reference
            FirebaseUser user = mAuth.getInstance().getCurrentUser();

            reference = db.getReference().child("users");
            reference.child(user.getUid()).child("color").setValue(color);
        } else{
            //else do nothing
            Log.d("myTag", "FALSE");
        }


    }


    //check if color hex value is valid
    public boolean checkColor(String color){
        // value string can only include characters # a-f A-F 0-9 value is ok
        if (color.matches("^#[0-9a-fA-F]{6}$")) {
            return true;
        }
        return false;
    }
    public void getAllUser(final FirebaseCallbackGetUsers firebaseCallbackGetUsers) {
        userAuth = mAuth.getInstance().getCurrentUser();
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

    public void checkUserExistence(final String userToChat, final FirebaseCallbackCheckUserExistence FirebaseCallbackCheckUserExistence){

        reference = db.getReference().child("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userToChatId = "unknown";
                //get username and color
                Iterable<DataSnapshot> usersIDs = dataSnapshot.getChildren();
                for(DataSnapshot item : usersIDs){
                    if(userToChat.equals(item.child("email").getValue()) || userToChat.equals(item.child("nickname").getValue())){
                        userToChatId = item.child("uid").getValue(String.class);
                    }
                }
                Log.d("emailUser", userToChatId);
                FirebaseCallbackCheckUserExistence.onCallbackCheckUserExistence(userToChatId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}