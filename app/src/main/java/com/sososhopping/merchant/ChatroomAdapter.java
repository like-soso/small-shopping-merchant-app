package com.sososhopping.merchant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatroomAdapter extends RecyclerView.Adapter {

    Context context;
    String storeId;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    ArrayList<ChatroomInfor> chatroomInforList;

    public ChatroomAdapter(Context context, ArrayList<ChatroomInfor> chatroomInforList, String storeId) {
        this.context = context;
        this.chatroomInforList = chatroomInforList;
        this.storeId = storeId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).chatroomItemCustomerName.setText(chatroomInforList.get(position).customerName + " 님");
        ((ViewHolder)holder).chatroomItemContent.setText(chatroomInforList.get(position).lastMessage);

        if (chatroomInforList.get(position).lastMessageTimestamp != null) {
            String lastMessageTimeStamp = chatroomInforList.get(position).lastMessageTimestamp.toString();
            Long lastMessageTime = Long.parseLong(lastMessageTimeStamp);
            Date date = new Date(lastMessageTime + 1000 * 60 * 60 * 9);

            ((ViewHolder) holder).chatroomItemTime.setText(simpleDateFormat.format(date));

            if (chatroomInforList.get(position).leaveChatroomTimestamp != null) {
                String leaveTimestamp = chatroomInforList.get(position).leaveChatroomTimestamp.toString();
                Long leaveTime = Long.parseLong(leaveTimestamp);
                if (lastMessageTime > leaveTime) {
                    ((ViewHolder) holder).chatroomItemAlarm.setVisibility(View.VISIBLE);
                }
            } else {
                ((ViewHolder) holder).chatroomItemAlarm.setVisibility(View.VISIBLE);
            }
        } else {
            ((ViewHolder) holder).chatroomItemTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return chatroomInforList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatroomItemCustomerName;
        public TextView chatroomItemTime;
        public TextView chatroomItemContent;
        public ImageView chatroomItemAlarm;

        public ViewHolder(View v) {
            super(v);
            this.chatroomItemCustomerName = v.findViewById(R.id.chatroomItemCustomerName);
            this.chatroomItemTime = v.findViewById(R.id.chatroomItemTime);
            this.chatroomItemContent = v.findViewById(R.id.chatroomItemContent);
            this.chatroomItemAlarm = v.findViewById(R.id.chatroomItemAlarm);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        ChatroomInfor chatroomInfor = chatroomInforList.get(pos);
                        Bundle bundle = new Bundle();
                        bundle.putString("chatroomId", chatroomInfor.chatroomId);
                        bundle.putString("userName", chatroomInfor.customerName);
                        Navigation.findNavController((View) (v.getParent().getParent().getParent().getParent().getParent().getParent())).navigate(R.id.action_storeManageFragment_to_conversationFragment, bundle);
                    }
                }
            });
        }
    }
}
