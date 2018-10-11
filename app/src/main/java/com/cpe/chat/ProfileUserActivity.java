package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewWelcome;
    private Button buttonLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        textViewWelcome = (TextView) findViewById(R.id.welcome_message);
        buttonLogout = (Button) findViewById(R.id.logout_button);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        findViewById(R.id.logout_button).setOnClickListener(this);
        FirebaseUser user = mAuth.getCurrentUser();
        textViewWelcome.setText("Welcome "+user.getEmail());
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}

