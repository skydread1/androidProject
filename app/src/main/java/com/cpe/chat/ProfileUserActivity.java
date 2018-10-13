package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private TextView textViewWelcome;
    private EditText usernameEditText;
    private Button buttonLogout;
    private Button sendinfobutton;
    private String userEmail;
    private UserDAO userdao = UserDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        //Find Views
        textViewWelcome = (TextView) findViewById(R.id.welcome_message);
        buttonLogout = (Button) findViewById(R.id.logout_button);
        sendinfobutton = (Button) findViewById(R.id.send_info);
        usernameEditText = (EditText) findViewById(R.id.username_input);

        //Listeners
        buttonLogout.setOnClickListener(this);
        sendinfobutton.setOnClickListener(this);

        //greetings
        userEmail = userdao.recoverEmail();
        textViewWelcome.setText("Welcome " + userEmail);
    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.logout_button:
                userdao.signOut();
                Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.send_info:
                userdao.saveUser(usernameEditText.getText().toString());
                intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
        }
    }
}

