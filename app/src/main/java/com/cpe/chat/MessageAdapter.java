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
    class ViewHolder0  {
        public TextView text_view_left;
        public ViewHolder0 (View v) {
            super(v);
            text_view_left = v.findViewById(R.id.textMessage_bubble_left);
        }
    }

    class ViewHolder1  {
        public TextView text_view_right;

        public ViewHolder1(View v) {
            super(v);
            text_view_right = v.findViewById(R.id.textMessage_bubble_right);
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_message_left, viewGroup, false)
                return new ViewHolder0(v);
            case 1:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_message_right, viewGroup, false)
                return new ViewHolder1(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int red = Color.parseColor(messages.get(i).getColor());
        viewHolder.textView.setBackgroundColor(red);
        if (messages.get(i).getId().equals(user.getUid())) {

            //set bubble layout
            viewHolder.textView.setBackgroundResource(R.drawable.bubble_right);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(red);

            viewHolder.textView.setText(messages.get(i).getSenderNickname()+" said the " + messages.get(i).getDate() +"\n"+messages.get(i).getMessageContent());
        }else{

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
