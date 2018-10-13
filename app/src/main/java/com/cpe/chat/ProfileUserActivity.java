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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private TextView textViewWelcome;
    private EditText usernameEditText;
    private Button buttonLogout;
    private Button sendinfobutton;
    private UserDAO userdao = UserDAO.INSTANCE;

    //Auth and Database
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        //Find Views
        textViewWelcome = (TextView) findViewById(R.id.welcome_message);
        buttonLogout = (Button) findViewById(R.id.logout_button);
        sendinfobutton = (Button) findViewById(R.id.send_info);
        usernameEditText = (EditText) findViewById(R.id.username_input);

        //initialize Auth and check if user logged in
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Initialize Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Listeners
        buttonLogout.setOnClickListener(this);
        sendinfobutton.setOnClickListener(this);

        FirebaseUser user = mAuth.getCurrentUser();
        textViewWelcome.setText("Welcome "+user.getEmail());

    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.logout_button:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.send_info:
                FirebaseUser user = mAuth.getCurrentUser();
                userdao.saveUser(new UserDetails(user.getUid(), user.getEmail(), usernameEditText.getText().toString()));
                intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
        }
    }
}

