package com.cpe.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public enum UserDAO {
    INSTANCE;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;


    public void saveUser(UserDetails userDetails){
        //Get Database Reference
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        databaseReference = db.getReference().child("users");
        databaseReference.child(user.getUid()).setValue(userDetails);
    }
}