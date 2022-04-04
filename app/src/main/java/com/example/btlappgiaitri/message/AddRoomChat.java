package com.example.btlappgiaitri.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.Adapter.AdapterAddRoomChat;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.BanBe;
import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.Models.RoomChat;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddRoomChat extends AppCompatActivity {

    TextView etSearch;

    ListView listViewDanhSach;

    ImageButton btnBack;

    AdapterAddRoomChat lstMessageAdapter_AddRoom;

    ArrayList<MessageData> MessageList_AddRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_chat);



        btnBack = findViewById(R.id.btnBack_AddRoomChat);
        etSearch = findViewById(R.id.etSearch_AddRoomChat);
        listViewDanhSach =findViewById(R.id.list_AddRoomChat);

        MessageList_AddRoom = new ArrayList<>();
        lstMessageAdapter_AddRoom = new AdapterAddRoomChat(AddRoomChat.this,MessageList_AddRoom);
        listViewDanhSach.setAdapter(lstMessageAdapter_AddRoom);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(!user.getIdtk().equals(LoginActivity.ID_Tk)){
                    reference.child("BanBe").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                BanBe banBe = snapshot.getValue(BanBe.class);
                                if((banBe.getIDTK1().equals(LoginActivity.ID_Tk)&&banBe.getIDTK2().equals(user.getIdtk()))||
                                   (banBe.getIDTK2().equals(LoginActivity.ID_Tk)&&banBe.getIDTK1().equals(user.getIdtk()))){
                                    MessageList_AddRoom.add(new MessageData(user.getIdtk(),user.getName(),user.getAvatar()));
                                    lstMessageAdapter_AddRoom.notifyDataSetChanged();
                                }
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lstMessageAdapter_AddRoom.getFilter().filter(charSequence.toString());
                lstMessageAdapter_AddRoom.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        listViewDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reference.child("RoomChat").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long dem = snapshot.getChildrenCount();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            RoomChat roomChat = postSnapshot.getValue(RoomChat.class);
                            if((roomChat.getIDTaiKhoan1().equals(LoginActivity.ID_Tk)&&roomChat.getIDTaiKhoan2().equals(MessageList_AddRoom.get(i).getId()))||
                                    (roomChat.getIDTaiKhoan2().equals(LoginActivity.ID_Tk)&&roomChat.getIDTaiKhoan1().equals(MessageList_AddRoom.get(i).getId()))){
                                Intent intent = new Intent( AddRoomChat.this, MessageItemActivity.class);
                                intent.putExtra("Key_Name", MessageList_AddRoom.get(i).getFullName());
                                intent.putExtra("ID_Room", roomChat.getID());
                                intent.putExtra("Avatar", MessageList_AddRoom.get(i).getAvartar());
                                startActivity(intent);
                                return;
                            }
                            dem--;
                        }
                        if(dem==0){
                            String timestamp = "" + System.currentTimeMillis();
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("ID",timestamp);
                            hashMap.put("IDTaiKhoan1",MessageList_AddRoom.get(i).getId());
                            hashMap.put("IDTaiKhoan2",LoginActivity.ID_Tk);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RoomChat");
                            reference.child(timestamp).setValue(hashMap);

                            Intent intent = new Intent( AddRoomChat.this, MessageItemActivity.class);
                            intent.putExtra("Key_Name", MessageList_AddRoom.get(i).getFullName());
                            intent.putExtra("ID_Room", timestamp);
                            intent.putExtra("Avatar", MessageList_AddRoom.get(i).getAvartar());
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}