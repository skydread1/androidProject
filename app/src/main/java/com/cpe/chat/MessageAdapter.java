package com.cpe.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView nameView;
        public LinearLayout linearLayoutText;
        public LinearLayout linearLayoutName;



        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            nameView = v.findViewById(R.id.textView_Name_n_date);
            linearLayoutText = v.findViewById(R.id.layout_txt);
            linearLayoutName = v.findViewById(R.id.layout_name);

        }
    }

    List<Message> messages;

    Context context;
    LayoutInflater layoutInflater;

    private FirebaseAuth mAuth;
    private FirebaseUser user = mAuth.getInstance().getCurrentUser();

    public MessageAdapter(List<Message> messages, Context context) {
        super();
        this.messages = messages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_message, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int bubble_color = Color.parseColor(messages.get(i).getColor());
        viewHolder.textView.setBackgroundColor(bubble_color);

        //set bubble layout for left or right
        if (messages.get(i).getId().equals(user.getUid())) {

            viewHolder.textView.setBackgroundResource(R.drawable.bubble_right);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(bubble_color);

            viewHolder.linearLayoutText.setGravity(Gravity.RIGHT);
            viewHolder.linearLayoutName.setGravity(Gravity.RIGHT);

            viewHolder.nameView.setText(messages.get(i).getSenderNickname()+" at " + messages.get(i).getDate());
            viewHolder.textView.setText(messages.get(i).getMessageContent());
        }else{

            viewHolder.textView.setBackgroundResource(R.drawable.bubble_left);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(bubble_color);

            viewHolder.linearLayoutText.setGravity(Gravity.LEFT);
            viewHolder.linearLayoutName.setGravity(Gravity.LEFT);

            viewHolder.nameView.setText(messages.get(i).getSenderNickname()+" at " + messages.get(i).getDate());
            viewHolder.textView.setText(messages.get(i).getMessageContent());
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
