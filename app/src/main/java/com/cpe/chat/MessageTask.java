package com.cpe.chat;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MessageTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private OnMessageChangeListener listener;

    MessageTask(String email, String password, OnMessageChangeListener message) {
        mEmail = email;
        mPassword = password;
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        int responseCode = 401;
        try {
            URL url = new URL("https://training.loicortola.com/chat-rest/1.0/messages/" + mEmail + "/" + mPassword);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //retrieve messages and parse



    }

    @Override
    protected void onPostExecute(final List<String> message) {
        listener.retrieveMessage(message);
    }
}