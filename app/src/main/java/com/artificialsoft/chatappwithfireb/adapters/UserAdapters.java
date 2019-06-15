package com.artificialsoft.chatappwithfireb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artificialsoft.chatappwithfireb.MessageActivity;
import com.artificialsoft.chatappwithfireb.R;
import com.artificialsoft.chatappwithfireb.models.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapters extends RecyclerView.Adapter<UserAdapters.MyViewHolder> {

    private Context context;
    private List<User> mUsers;

    public UserAdapters(Context context, List<User> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView textViewUserName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            textViewUserName = itemView.findViewById(R.id.userName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_userlist, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final User users = mUsers.get(i);
        myViewHolder.textViewUserName.setText(users.getUsername());
        if (users.getImageURL().equals("default")){
            myViewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(users.getImageURL()).into(myViewHolder.profileImage);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", users.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
