package com.cpe.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public enum UserDAO {
    INSTANCE;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference();


    public String recoverEmail(){
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        return user.getEmail();
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