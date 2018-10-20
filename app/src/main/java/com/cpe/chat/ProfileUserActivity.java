package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private TextView textViewProfileEmail;
    private TextView textViewProfileUsername;

    private EditText editTextUsernameInput;
    private Button buttonChangeUsername;

    private TextView textViewMessageText;
    private Button buttonLogout;

    private Button buttonChat;

    private UserDAO userdao = UserDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        //Find Views
        textViewProfileEmail = (TextView) findViewById(R.id.profile_email);
        textViewProfileUsername = (TextView) findViewById(R.id.profile_username);

        editTextUsernameInput = (EditText) findViewById(R.id.profile_username_input);
        buttonChangeUsername = (Button) findViewById(R.id.profile_change_button);

        textViewMessageText = (TextView) findViewById(R.id.profile_message_text);
        buttonChat = (Button) findViewById(R.id.profile_message_button);

        buttonLogout = (Button) findViewById(R.id.logout_button);


        //Listeners
        buttonChangeUsername.setOnClickListener(this);
        buttonChat.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        //Display user Info
        userdao.INSTANCE.getUsernameFromDB(new FirebaseCallbackGetUsername() {
            @Override
            public void onCallbackGetUsername(String userName) {
                textViewProfileEmail.setText(userdao.getUserEmail());
                textViewProfileUsername.setText(userName);
            }
        });
    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.profile_change_button:
                String usernameInput = editTextUsernameInput.getText().toString().trim();
                if(usernameInput.isEmpty()){
                    editTextUsernameInput.setError("Email is required");
                    editTextUsernameInput.requestFocus();
                }
                else{
                    userdao.saveUser(editTextUsernameInput.getText().toString());
                    Intent intentProfile = new Intent(this, ProfileUserActivity.class);
                    startActivity(intentProfile);
                }
                break;

            case R.id.profile_message_button:
                Intent intentMessage = new Intent(this, MessageActivity.class);
                startActivity(intentMessage);
                break;

            case R.id.logout_button:
                userdao.signOut();
                Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}

