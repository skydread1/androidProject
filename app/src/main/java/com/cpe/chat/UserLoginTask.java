package com.cpe.chat;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    final static private String TAG = UserLoginTask.class.getSimpleName();

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

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String input;
            System.out.println("HELLO");
            Log.i(TAG, "HELLO");
            System.out.println("\n");
            while ((input = in.readLine()) != null) {

                JSONObject loginStat  = new JSONObject(input);

                JSONArray cast = loginStat.getJSONArray("status");
                for (int i=0; i<cast.length(); i++) {
                    JSONObject s = cast.getJSONObject(i);
                    String stat = s.getString("status");
                    Log.i(TAG, "IN FOR");
                    System.out.println("JSON");
                    System.out.println("\n");
                    Log.i(TAG, "status : " + stat);
                    System.out.println("status : " + stat);
                    System.out.println("\n");
                }

            }
            in.close();

            //JSONObject loginStatus = new JSONObject(responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
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