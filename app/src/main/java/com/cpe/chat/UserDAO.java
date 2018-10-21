package com.cpe.chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;


public enum UserDAO{
    INSTANCE;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference();
    private FirebaseUser userAuth;
    private String userName;

    public void getUsernameFromDB(final FirebaseCallbackGetUsername firebaseCallbackGetUsername) {
        userAuth = mAuth.getInstance().getCurrentUser();
        userName = new String();
        reference = db.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
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
    private boolean checkColor(String color){
        //email and password conformity tests
        Log.d("myTag", "INCHECK color");
        if (color.isEmpty()) {
           // Toast.makeText(UserDAO.this, "Color is Empty", LENGTH_SHORT).show();
            Log.d("myTag", "EMPTY");
            return false;
        }
        // value string can only include characters # a-f A-F 0-9 value is ok
        if (color.matches("/^#[0-9A-F]+$/")) {
            Log.d("myTag", "characters ok");
            // check length:
            if (color.length()!= 7) {
                Log.d("myTag", "Length");
                //("Maximum length of password should be 6")
                return false;
            }
            return true;
        } else {
            Log.d("myTag", "characters not ok"+ color.matches("/^#[0-9A-F]+$/"));

            return false;
        }
    }

}