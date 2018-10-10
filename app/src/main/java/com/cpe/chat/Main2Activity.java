package com.cpe.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

  final static private String TAG = Main2Activity.class.getSimpleName();
  @BindView(R.id.username) EditText username;
  @BindView(R.id.password) EditText password;
  @BindView(R.id.button) Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    Log.i(TAG, "onCreate");
    ButterKnife.bind(this);
  }

  @OnClick(R.id.button) void buttonClicked() {
    String usernameText = username.getText().toString();
    String passwordText = password.getText().toString();
    Toast.makeText(Main2Activity.this, "username: " + usernameText + " password: " + passwordText, Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.password) void passwordClicked() {
    Toast.makeText(this, "Password Clicked", Toast.LENGTH_SHORT).show();
  }

  @Override protected void onStart() {
    super.onStart();
    Log.i(TAG, "onStart");
  }

  @Override protected void onResume() {
    super.onResume();
    Log.i(TAG, "onResume");
  }

  @Override protected void onPause() {
    super.onPause();
    Log.i(TAG, "onPause");
  }

  @Override protected void onStop() {
    super.onStop();
    Log.i(TAG, "onStop");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Log.i(TAG, "onDestroy");
  }
}
