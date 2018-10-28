package com.cpe.chat.adaptaters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpe.chat.R;
import com.cpe.chat.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TextView nameView_l;
        public TextView nameView_r;
        public TextView dateView_l;
        public TextView dateView_r;
        public LinearLayout linearLayoutText;
        public LinearLayout linearLayoutName;
        public LinearLayout linearLayoutName_l;
        public LinearLayout linearLayoutName_r;



        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView); // msg content

            nameView_l = v.findViewById(R.id.textView_name_l); // nickname left
            nameView_r = v.findViewById(R.id.textView_name_r); // nickname right
            dateView_l = v.findViewById(R.id.textView_date_l); // time and date left
            dateView_r = v.findViewById(R.id.textView_date_r); // time and date right

            linearLayoutText = v.findViewById(R.id.layout_txt);
            linearLayoutName = v.findViewById(R.id.layout_name_date);
            linearLayoutName_l = v.findViewById(R.id.layout_name_date_l);
            linearLayoutName_r = v.findViewById(R.id.layout_name_date_r);

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
        //set bubble layout for left or right
        if (messages.get(i).getId().equals(user.getUid())) {
            bubbleDesign(viewHolder, true, Color.parseColor(messages.get(i).getColor()),
                    messages.get(i).getSenderNickname(),
                    messages.get(i).getDate(),
                    messages.get(i).getMessageContent());
        }else{
            bubbleDesign(viewHolder, false, Color.parseColor(messages.get(i).getColor()),
                    messages.get(i).getSenderNickname(),
                    messages.get(i).getDate(),
                    messages.get(i).getMessageContent());
        }
    }


    public void bubbleDesign(@NonNull ViewHolder viewHolder, Boolean right_or_left, int bubble_color, String nickname, String date_time, String messageContent) {
        viewHolder.textView.setBackgroundColor(bubble_color);
        String date = date_time.substring(8,10)+"."+date_time.substring(5,7)+"."+date_time.substring(0,4);
        String time = date_time.substring(16,22);

        //if true bubble layout right
        if (right_or_left) {
            viewHolder.textView.setBackgroundResource(R.drawable.bubble_right);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(bubble_color);

            viewHolder.linearLayoutText.setGravity(Gravity.RIGHT);
            viewHolder.linearLayoutName.setGravity(Gravity.RIGHT);

            // "Move" views around
            viewHolder.linearLayoutName_l.setVisibility(View.INVISIBLE);
            viewHolder.linearLayoutName_r.setVisibility(View.VISIBLE);
            viewHolder.nameView_l.setVisibility(View.INVISIBLE);
            viewHolder.dateView_l.setVisibility(View.INVISIBLE);
            viewHolder.nameView_r.setVisibility(View.VISIBLE);
            viewHolder.dateView_r.setVisibility(View.VISIBLE);

            //set content
            viewHolder.nameView_r.setText(nickname);
            viewHolder.dateView_r.setText(" at "+time+", "+date);
            viewHolder.textView.setText(messageContent);
        }else{
            // otherwise bubble layout left
            viewHolder.textView.setBackgroundResource(R.drawable.bubble_left);
            GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.textView.getBackground().mutate();
            gradientDrawable.setColor(bubble_color);

            viewHolder.linearLayoutText.setGravity(Gravity.LEFT);
            viewHolder.linearLayoutName.setGravity(Gravity.LEFT);

            viewHolder.linearLayoutName_l.setVisibility(View.VISIBLE);
            viewHolder.linearLayoutName_r.setVisibility(View.INVISIBLE);
            viewHolder.nameView_l.setVisibility(View.VISIBLE);
            viewHolder.dateView_l.setVisibility(View.VISIBLE);
            viewHolder.nameView_r.setVisibility(View.INVISIBLE);
            viewHolder.dateView_r.setVisibility(View.INVISIBLE);

            viewHolder.nameView_l.setText(nickname);
            viewHolder.dateView_l.setText(" at "+time+", "+date);
            viewHolder.textView.setText(messageContent);
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
