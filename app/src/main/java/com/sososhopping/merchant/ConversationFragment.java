package com.sososhopping.merchant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.sososhopping.merchant.databinding.FragmentConversationBinding;
import com.sososhopping.merchant.view.activity.MainActivity;

import java.util.ArrayList;

public class ConversationFragment extends Fragment {

    private static final String CHATROOMID = "chatroomId";
    private static final String USERNAME = "userName";
    private static final String CHATROOMMESSAGESPATH = "ChatroomMessages";
    private static final String CHATROOMINFOR = "ChatroomInfor";

    private String storeId;
    private String chatroomId;
    private String userName;
    private String ownerUid;
    private String userUid;

    private RecyclerView chatRecyclerView;
    private ChatAdapter adapter;
    private RecyclerView.LayoutManager chatLayoutManager;
    private EditText chatroomInputEditText;
    private Button chatroomInputSendButton;
    private TextView chatroomToolbarTextView;

    ArrayList<Chat> chatList = new ArrayList<>();

    private DatabaseReference ref;

    public ConversationFragment() {
    }

    public static ConversationFragment newInstance(String storeId, String chatroomId, String userName) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putString(CHATROOMID, chatroomId);
        args.putString(USERNAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chatroomId = getArguments().getString(CHATROOMID);
            userName = getArguments().getString(USERNAME);
        }

        String[] split = chatroomId.split("@");
        storeId = split[0];
        ownerUid = split[1];
        userUid = split[2];

        ref = ((MainActivity)getActivity()).ref;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentConversationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversation, container, false);

        chatroomInputEditText = binding.chatroomInputEditText;
        chatroomInputSendButton = binding.chatroomInputSendButton;
        chatroomToolbarTextView = binding.chatroomToolbarTextView;
        chatroomToolbarTextView.setText(userName+ " 님");

        chatLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ChatAdapter(getContext(), userName, ownerUid, chatList);
        setChatList();

        chatRecyclerView = binding.chatroom;
        chatRecyclerView.setLayoutManager(chatLayoutManager);
        chatRecyclerView.scrollToPosition(0);
        chatRecyclerView.setAdapter(adapter);

        chatroomInputSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chatroomInputEditText.getText().toString().equals("")) {
                    String content = chatroomInputEditText.getText().toString();
                    Chat chat = new Chat(ownerUid, "text", content, null);

                    ref.child(CHATROOMMESSAGESPATH).child(chatroomId).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            chatroomInputEditText.setText("");

                            ref.child(CHATROOMINFOR).child(storeId).child(chatroomId).child("lastMessage").setValue(content);
                            ref.child(CHATROOMINFOR).child(storeId).child(chatroomId).child("lastMessageTimestamp").setValue(ServerValue.TIMESTAMP);

                            ref.child(CHATROOMINFOR).child(userUid).child(chatroomId).child("lastMessage").setValue(content);
                            ref.child(CHATROOMINFOR).child(userUid).child(chatroomId).child("lastMessageTimestamp").setValue(ServerValue.TIMESTAMP);
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ref.child(CHATROOMINFOR).child(storeId).child(chatroomId).child("leaveChatroomTimestamp").setValue(ServerValue.TIMESTAMP);
    }

    private void setChatList() {
        ref.child(CHATROOMMESSAGESPATH).child(chatroomId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Chat chat = snapshot.getValue(Chat.class);
                chatList.add(chat);
                adapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}