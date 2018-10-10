package com.cpe.chat;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final OnLoginChangeListener listener;

    UserLoginTask(String email, String password, OnLoginChangeListener listener) {
        mEmail = email;
        mPassword = password;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int responseCode = 401;
        try {
            String myUrl = "https://training.loicortola.com/chat-rest/1.0/connect/" + mEmail + "/" + mPassword;
            URL url = new URL(myUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isConnected = responseCode == 200;
        return isConnected;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        listener.loginChange(success);
    }
}