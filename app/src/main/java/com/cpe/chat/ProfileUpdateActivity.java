package com.cpe.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private EditText updateUsername;
    private Button updateUsernameButton;

    private EditText updateColor;
    private Button updateColorButton;

    private Button goBackProfileButton;

    private UserDAO userdao = UserDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        //Find Views
        updateUsername = (EditText) findViewById(R.id.profile_update_username);
        updateUsernameButton = (Button) findViewById(R.id.profile_update_username_button);

        updateColor = (EditText) findViewById(R.id.profile_update_color);
        updateColorButton = (Button) findViewById(R.id.profile_update_color_button);

        goBackProfileButton = (Button) findViewById(R.id.profile_update_gobackprofile_button);

        //Listeners
        updateUsernameButton.setOnClickListener(this);
        updateColorButton.setOnClickListener(this);
        goBackProfileButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.profile_update_username_button:
                String usernameInput = updateUsername.getText().toString().trim();
                if(usernameInput.isEmpty()){
                    updateUsername.setError("Enter something");
                    updateUsername.requestFocus();
                }
                else{
                    userdao.updateUserUsername(updateUsername.getText().toString());
                    Toast.makeText(ProfileUpdateActivity.this, "Username updated", Toast.LENGTH_SHORT).show();
                    updateUsername.setText("");
                }
                break;

            case R.id.profile_update_color_button:
                String colorInput = updateColor.getText().toString().trim();
                if(colorInput.isEmpty()){
                    updateColor.setError("Enter something");
                    updateColor.requestFocus();
                }
                else{
                    if(userdao.checkColor(colorInput)){
                        userdao.updateUserColor(colorInput);
                        updateColor.setText("");
                        Toast.makeText(ProfileUpdateActivity.this, "Color Updated", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ProfileUpdateActivity.this, "Wrong format, try again", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.profile_update_gobackprofile_button:
                finish();
                Intent intentProfile = new Intent(this, ProfileUserActivity.class);
                startActivity(intentProfile);
                break;
        }
    }
}
