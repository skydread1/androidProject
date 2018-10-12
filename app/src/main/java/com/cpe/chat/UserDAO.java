package com.cpe.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;


    public void saveUser(UserDetails userDetails){

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        databaseReference = db.getReference();
        databaseReference.child(user.getUid()).setValue(userDetails);

        if(user == null){
            finish();
            Toast.makeText(this, "error while retrieving user", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Initialize Database
        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child(user.getUid()).setValue(userDetails);
        Toast.makeText(this, "nickname saved", Toast.LENGTH_SHORT).show();
    }
}