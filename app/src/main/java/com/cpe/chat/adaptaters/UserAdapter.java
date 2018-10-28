package com.cpe.chat.adaptaters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cpe.chat.R;
import com.cpe.chat.model.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button_user_list;

        public ViewHolder(View v) {
            super(v);
            button_user_list = v.findViewById(R.id.button_user_list);
        }
    }

    List<UserDetails> userDetailsList;

    Context context;
    LayoutInflater layoutInflater;

    private FirebaseAuth mAuth;
    private FirebaseUser user = mAuth.getInstance().getCurrentUser();

    public UserAdapter(List<UserDetails> userDetailsList, Context context) {
        super();
        this.userDetailsList = userDetailsList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_user, viewGroup, false);
        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        //if (!userDetailsList.get(i).getUid().equals(user.getUid())) {

            int bubble_color = Color.parseColor(userDetailsList.get(i).getColor());
            viewHolder.button_user_list.setBackgroundColor(bubble_color);

            viewHolder.button_user_list.setId(99+i);

            viewHolder.button_user_list.setText(userDetailsList.get(i).getEmail()+ " as " + userDetailsList.get(i).getNickname());
        //}
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }
}
