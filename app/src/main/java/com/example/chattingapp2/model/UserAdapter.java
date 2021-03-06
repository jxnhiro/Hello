package com.example.chattingapp2.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattingapp2.MessageActivity;
import com.example.chattingapp2.R;
import com.example.chattingapp2.StartActivity;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
        private Context context;
        private List<Users> mUsers;

    //Constructor
    public UserAdapter(Context context, List<Users> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = mUsers.get(position);
        holder.unm.setText(users.getUsername());
        holder.gT.setText(users.getGroup());
        if(users.getImageURL().equals("default")){
            holder.imgV.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(context).load(users.getImageURL()).into(holder.imgV);
        }

        //Click listener to chat
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent (context, MessageActivity.class);
                i2.putExtra("userid", users.getId());
                context.startActivity(i2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView unm;
        public ImageView imgV;
        public TextView gT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gT = itemView.findViewById(R.id.groupUserItem);
            unm = itemView.findViewById(R.id.nameUserItem);
            imgV = itemView.findViewById(R.id.imageUserItem);
        }
    }
}
