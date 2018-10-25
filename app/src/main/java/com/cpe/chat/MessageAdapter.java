package com.cpe.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        //public TextView textView_left;
        // public TextView textView_right;


        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
           // textView_left = v.findViewById(R.id.textMessage_bubble_left);
            //textView_right = v.findViewById(R.id.textMessage_bubble_right);
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
        int red = Color.parseColor(messages.get(i).getColor());
        viewHolder.textView.setBackgroundColor(red);
        if (messages.get(i).getId().equals(user.getUid())) {
            Log.d("buble", "Bubble_right");

            //set bubble layout
            viewHolder.textView.setBackgroundResource(R.drawable.bubble_right);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(red);

            viewHolder.textView.setText(messages.get(i).getSenderNickname()+" said the " + messages.get(i).getDate() +"\n"+messages.get(i).getMessageContent());
        }else{
            Log.d("buble", "Bubble_left");

            viewHolder.textView.setBackgroundResource(R.drawable.bubble_left);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(red);

            viewHolder.textView.setText(messages.get(i).getSenderNickname()+" said the " + messages.get(i).getDate() +"\n"+messages.get(i).getMessageContent());
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
