package com.example.btlappgiaitri.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.Adapter.AdapterMessage;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.Models.RoomChat;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListMessageActivity extends AppCompatActivity {

    ListView listViewMessage;
    ImageButton btnBack,btnAddNewMessage;

    int selectedIdRoom=-1;

    AdapterMessage lstMessageAdapter;

    ArrayList<MessageData> MessageList;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.context_menu_listmessage,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(ListMessageActivity.this);
                builder.setTitle("Xoá thiết bị");
                builder.setMessage("Bạn có chắc chắn muốn xoá cuộc trò chuyện với "+MessageList.get(selectedIdRoom).getFullName()+" không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String idroom = MessageList.get(selectedIdRoom).getId();

                        reference.child("RoomChat").child(idroom).removeValue();
                        ArrayList<MessageData> MessageList1 = new ArrayList<MessageData>();
                        for(MessageData item : MessageList){
                            if(!item.getId().equals(idroom)){
                                MessageList1.add(item);
                            }
                        }
                        MessageList = MessageList1;
                        lstMessageAdapter = new AdapterMessage(ListMessageActivity.this,MessageList);
                        listViewMessage.setAdapter(lstMessageAdapter);
                    }
                });
                builder.setNegativeButton(("Không"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                builder.create();
                builder.show();
                break;

        }
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);


        MessageList = new ArrayList<>();


        reference.child("RoomChat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomChat roomChat = snapshot.getValue(RoomChat.class);
                //Load các roomchat mà tài khoản có mặt
                if(LoginActivity.ID_Tk.equals(roomChat.getIDTaiKhoan1())||LoginActivity.ID_Tk.equals(roomChat.getIDTaiKhoan2())){

                    reference.child("User").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName1) {
                            User user = snapshot.getValue(User.class);
                            if(roomChat.getIDTaiKhoan1().equals(LoginActivity.ID_Tk)){
                                if(roomChat.getIDTaiKhoan2().equals(user.getIdtk())){
                                    MessageList.add(new MessageData(roomChat.getID(),user.getName(),"test","Hôm qua",user.getAvatar()));
                                    lstMessageAdapter.notifyDataSetChanged();
                                }
                            }
                            else{
                                if(roomChat.getIDTaiKhoan1().equals(user.getIdtk())){
                                    MessageList.add(new MessageData(roomChat.getID(),user.getName(),"test","Hôm qua",user.getAvatar()));
                                    lstMessageAdapter.notifyDataSetChanged();
                                }
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
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        lstMessageAdapter = new AdapterMessage(ListMessageActivity.this,MessageList);
        listViewMessage =  findViewById(R.id.listMessage);
        listViewMessage.setAdapter(lstMessageAdapter);

        registerForContextMenu(listViewMessage);

        listViewMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( ListMessageActivity.this, MessageItemActivity.class);
                intent.putExtra("Key_Name", lstMessageAdapter.getItem(i).getFullName());
                intent.putExtra("ID_Room", lstMessageAdapter.getItem(i).getId());
                intent.putExtra("Avatar", lstMessageAdapter.getItem(i).getAvartar());
                startActivity(intent);
            }
        });

        listViewMessage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIdRoom = i;
                return false;
            }
        });



        btnBack = findViewById(R.id.btnBackListNotify);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddNewMessage = findViewById(R.id.btnOption);
        btnAddNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMessageActivity.this,AddRoomChat.class);
                startActivity(intent);
            }
        });

    }
}