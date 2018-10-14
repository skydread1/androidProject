package com.cpe.chat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public enum UserDAO {
    INSTANCE;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference();
    private FirebaseUser user;
    private String nickname;

    public String recoverEmail(){
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        return user.getEmail();
    }

    public String recoverNickname(){
        user = mAuth.getInstance().getCurrentUser();
        databaseReference = db.getReference().child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> usersIDs = dataSnapshot.getChildren();
                for(DataSnapshot item : usersIDs){
                    if(item.getKey().equals(user.getUid())){
                        nickname = item.child("nickname").getValue(String.class);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return nickname;

    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void saveUser(String username){

        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        //creation of a new user
        UserDetails userDetails = new UserDetails(user.getUid(), user.getEmail(), username);

        databaseReference = db.getReference().child("users");
        databaseReference.child(user.getUid()).setValue(userDetails);
    }
}