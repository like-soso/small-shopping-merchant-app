package com.sososhopping.merchant.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.sososhopping.merchant.ChatroomInfor;
import com.sososhopping.merchant.ChatroomUsers;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ActivityMainBinding;
import com.sososhopping.merchant.utils.token.TokenStore;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseUser user;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference ref;
    public Task<AuthResult> authResultTask;
    public boolean afterLogin = false;

    @Override
    protected void onStart() {
        super.onStart();

        //파이어베이스 재인증 및 사용자 온라인 설정
        if (afterLogin == true) {
            user = mAuth.getCurrentUser();
            if (user == null) {
                authResultTask = mAuth.signInWithCustomToken(TokenStore.getFirebaseToken())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                user = authResult.getUser();
                                ref.child("User").child(user.getUid()).child("connection").setValue(true);
                            }
                        });
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //오프라인 설정
        if (afterLogin == true) {
            ref.child("User").child(this.user.getUid()).child("connection").setValue(false);
            ref.child("User").child(this.user.getUid()).child("lastOnline").setValue(ServerValue.TIMESTAMP);
            mAuth.signOut();
        }
    }

    //채팅방 생성
    public String makeChatroom(String storeId, String userId, String customerName, String storeName) {
        String userUid = "U" + userId;
        String ownerUid = user.getUid();
        String chatRoomId = storeId + "@" +  ownerUid + "@" + userUid;

        ref.child("ChatroomUsers").child(chatRoomId).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ChatroomInfor chatRoomInfor = null;

                        if (task.isSuccessful()) {
                            chatRoomInfor = task.getResult().getValue(ChatroomInfor.class);
                        }

                        if (chatRoomInfor == null) {
                            chatRoomInfor = new ChatroomInfor(customerName, storeName, chatRoomId);

                            ref.child("ChatroomInfor")
                                    .child(userUid)
                                    .child(chatRoomId)
                                    .setValue(chatRoomInfor);

                            ref.child("ChatroomInfor")
                                    .child(storeId)
                                    .child(chatRoomId)
                                    .setValue(chatRoomInfor);

                            ChatroomUsers chatRoomUserInfor = new ChatroomUsers(userUid, ownerUid);
                            ref.child("ChatroomUsers")
                                    .child(chatRoomId)
                                    .setValue(chatRoomUserInfor);
                        }
                    }
                });

        return chatRoomId;
    }
}
