package com.cpe.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cpe.chat.R;
import com.cpe.chat.daos.UserDAO;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsername;


public class ProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private TextView textViewProfileEmail;
    private TextView textViewProfileUsername;

    private Button buttonChangeInfo;

    private TextView textViewMessageText;
    private Button buttonLogout;

    private Button buttonGeneralChat;
    private Button buttonPrivateChat;

    private UserDAO userdao = UserDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        //Find Views
        textViewProfileEmail = (TextView) findViewById(R.id.profile_email);
        textViewProfileUsername = (TextView) findViewById(R.id.profile_username);

        buttonChangeInfo = (Button) findViewById(R.id.profile_change_button);

        buttonGeneralChat = (Button) findViewById(R.id.button_general_chat);
        buttonPrivateChat = (Button) findViewById(R.id.button_private_chat);

        buttonLogout = (Button) findViewById(R.id.logout_button);


        //Listeners
        buttonChangeInfo.setOnClickListener(this);
        buttonGeneralChat.setOnClickListener(this);
        buttonPrivateChat.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);


        //Display user Info
        userdao.INSTANCE.getUsernameFromDB(new FirebaseCallbackGetUsername() {
            @Override
            public void onCallbackGetUsername(String userName) {
                textViewProfileEmail.setText("Email Address : " + userdao.getUserEmail());
                textViewProfileUsername.setText("Username : " + userName);
            }
        });
    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.profile_change_button:
                Intent intentProfileUpdate = new Intent(ProfileUserActivity.this, ProfileUpdateActivity.class);
                startActivity(intentProfileUpdate);
                break;

            case R.id.button_general_chat:
                Intent intentMessageG = new Intent(ProfileUserActivity.this, MessageActivity.class);
                Log.d("intenta1", "intent ok");
                startActivity(intentMessageG);
                Log.d("intenta2", "start ok");
                break;

            case R.id.button_private_chat:
                Intent intentMessageP = new Intent(this, ConversationSelectionActivity.class);
                startActivity(intentMessageP);
                break;

            case R.id.logout_button:
                userdao.signOut();
               // Toast.makeText(this, "Log out success", Toast.LENGTH_SHORT).show();
                finish();
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}

