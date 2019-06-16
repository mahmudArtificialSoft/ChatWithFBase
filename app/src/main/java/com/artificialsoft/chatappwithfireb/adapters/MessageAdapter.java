package com.artificialsoft.chatappwithfireb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artificialsoft.chatappwithfireb.R;
import com.artificialsoft.chatappwithfireb.models.Chat;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;

    public MessageAdapter(Context context, List<Chat> mChat, String imageurl) {
        this.context = context;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_Image;
        TextView show_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_Image = itemView.findViewById(R.id.profile_image);
            show_message = itemView.findViewById(R.id.show_messagek);
        }
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.MyViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.MyViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder myViewHolder, int i) {
        Chat chat = mChat.get(i);

        myViewHolder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")){
            myViewHolder.profile_Image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(imageurl).into(myViewHolder.profile_Image);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}