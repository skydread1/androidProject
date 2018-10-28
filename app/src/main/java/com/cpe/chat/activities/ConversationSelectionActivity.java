package com.cpe.chat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpe.chat.R;
import com.cpe.chat.adaptaters.UserAdapter;
import com.cpe.chat.daos.ConversationDAO;
import com.cpe.chat.daos.UserDAO;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackCheckConversationExistence;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackCheckUserExistence;
import com.cpe.chat.firebaseInterfaces.FirebaseCallbackGetUsers;
import com.cpe.chat.model.UserDetails;

import java.util.List;

public class ConversationSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ConversationDAO conversationdao = ConversationDAO.INSTANCE;

    private EditText conversation_user_input;
    private Button button_conv_chat;
    private UserDAO userdao = UserDAO.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_selection);

        //Find Views
        conversation_user_input = (EditText) findViewById(R.id.conversation_user_input);
        button_conv_chat = (Button) findViewById(R.id.button_conv_chat);

        //Listeners
        button_conv_chat.setOnClickListener(this);

        //retrieving user List thanks to a callback function because FireBase listeners are asynchronous
        userdao.getAllUser(new FirebaseCallbackGetUsers() {
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
    }




    @Override
    public void onClick(View v) {
        String userToChat = conversation_user_input.getText().toString().trim();
        userdao.checkUserExistence(userToChat, new FirebaseCallbackCheckUserExistence() {
            @Override
            public void onCallbackCheckUserExistence(final String userToChatId) {
                //check if user exists
                if(!userToChatId.equals("unknown")){
                    //check if a conversation is already on
                    conversationdao.checkConversationExistence(userToChatId, new FirebaseCallbackCheckConversationExistence() {
                        @Override
                        public void onCallbackCheckConversationExistence(boolean existence) {
                            Log.d("convExistence", String.valueOf(existence));
                            if(existence){
                                //case 1 : a conversation already exists between the 2 users
                                Toast.makeText(ConversationSelectionActivity.this, "retrieving conversation...", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //case 2 : no conversation recorded, create a new one
                                conversationdao.createConversation(userToChatId);
                            }
                            //start private messages activity
                            Intent intent = new Intent(ConversationSelectionActivity.this, MessageActivity.class);
                            intent.putExtra("userToChatId",userToChatId );
                            intent.putExtra("privateChat","private" );
                            startActivity(intent);
                        }
                    });



                }
                else{
                    Toast.makeText(ConversationSelectionActivity.this, "this user does not exist, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });



        /*Log.d("we are here", String.valueOf(v.getId()));
        //if (finalI == 99) {
        Intent intent = new Intent(ConversationSelectionActivity.this, MessageGeneralActivity.class);
        //intent.putExtra("oldValue", view.getId().);
        startActivity(intent);*/
        //}
    }
}
