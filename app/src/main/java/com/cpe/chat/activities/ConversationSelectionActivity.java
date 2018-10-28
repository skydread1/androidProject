package com.cpe.chat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cpe.chat.R;
import com.cpe.chat.adaptaters.UserAdapter;
import com.cpe.chat.adaptaters.MessageAdapter;
import com.cpe.chat.daos.ConversationDAO;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsers;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackSaveMessage;
import com.cpe.chat.model.Message;
import com.cpe.chat.model.UserDetails;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ConversationSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ConversationDAO conversationdao = ConversationDAO.INSTANCE;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_selection);

        //retrieving user List thanks to a callback function because FireBase listeners are asynchronous
        conversationdao.INSTANCE.getAllUser(new FirebaseCallbackGetUsers() {
            @Override
            public void onCallbackGetUsers(List<UserDetails> users)  {
                //recyclerView
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id_users);
                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                // specify an adapter
                UserAdapter adapter = new UserAdapter(users , getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        });

        //listeners
        /*for (int i = 99; i < adapter.getItemCount(); i++) {
            Log.d("itemcount", String.valueOf(adapter.getItemCount()));
            final int finalI = i;
            btn = (Button) findViewById(i);
            Log.d("textloop3", String.valueOf(btn.getId()));
            btn.setOnClickListener(new RecyclerView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("we are here", String.valueOf(v.getId()));
                    //if (finalI == 99) {
                    Intent intent = new Intent(ConversationSelectionActivity.this, MessageActivity.class);
                    //intent.putExtra("oldValue", view.getId().);
                    startActivity(intent);
                    //}
                }
            }) ;
        }*/
    }




    @Override
    public void onClick(View v) {
        Log.d("textloop3", String.valueOf(v.getId()));
    }
}
