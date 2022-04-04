package com.example.btlappgiaitri.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.MessageItem;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageItemActivity extends AppCompatActivity {

    TextView txtName;
    ImageView btSend,btnBackListMessage,btnOption;
    EditText etEmoji;
    LinearLayout linearLayout;
    ScrollView svMessage;

    CircleImageView avatar;

    String idRoom;

    boolean check =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_item);

        txtName = findViewById(R.id.txtName);
        btnOption= findViewById(R.id.btnOption);
        btnBackListMessage= findViewById(R.id.btnBackListNotify);
        btSend =findViewById(R.id.bt_send);
        etEmoji =findViewById(R.id.et_emoji);
        linearLayout =findViewById(R.id.linear_layout);
        svMessage =findViewById(R.id.scrollListMessage);
        avatar = findViewById(R.id.avatar_MessageItem);

        Intent intent = getIntent();
        String value1 = intent.getStringExtra("Key_Name");
        idRoom =intent.getStringExtra("ID_Room");
        Picasso.get().load(intent.getStringExtra("Avatar")).into(avatar);
        txtName.setText(value1);


        btnBackListMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageItemActivity.this, "Option", Toast.LENGTH_SHORT).show();
            }
        });
        etEmoji.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0){
                    btSend.setVisibility(View.VISIBLE);
                }
                else{
                    btSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //mở và đóng bàn phím biếu cảm
        EmojiPopup popup = EmojiPopup.Builder.fromRootView(findViewById(R.id.root_view)).build(etEmoji);
        etEmoji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (etEmoji.getRight() - etEmoji.getCompoundDrawables()[2].getBounds().width())) {
                        if(!check){
                            etEmoji.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_alt_24,0);
                        }
                        else{
                            etEmoji.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_tag_faces_24,0);
                        }
                        popup.toggle();
                        check = !check;
                        return true;
                    }
                }

                return false;
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TinNhan");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageItem messageItem = snapshot.getValue(MessageItem.class);
                //load tin nhắn
                if(messageItem.getIDRoomChat().equals(idRoom)){
                    EmojiTextView emojiTextView = (EmojiTextView) LayoutInflater
                            .from(linearLayout.getContext())
                            .inflate(R.layout.emoji_text_view,linearLayout,false);
                    emojiTextView.setText(messageItem.getNoiDung());
                    //tin nhắn của tài khoản đăng nhập
                    if(messageItem.getIDTaiKhoan().equals(LoginActivity.ID_Tk)){
                        emojiTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                        emojiTextView.setBackground(getDrawable(R.drawable.round_message_item));
                        linearLayout.addView(emojiTextView);
                    }
                    else{ //tin nhắn của người khác
                        emojiTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                        emojiTextView.setBackground(getDrawable(R.drawable.round_message_item_gray));

                        LinearLayout linearLayoutRoot = new LinearLayout(MessageItemActivity.this);
                        linearLayoutRoot.addView(emojiTextView);
                        TextView tv = new TextView(MessageItemActivity.this);
                        linearLayoutRoot.addView(tv);

                        linearLayout.addView(linearLayoutRoot);
                    }
                    svMessage.post(new Runnable() {
                        @Override
                        public void run() {
                            svMessage.fullScroll(ScrollView.FOCUS_DOWN);
                        }
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

// thêm tin nhắn vào firebase
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etEmoji.getText().toString().trim().equals("")){
                    try {
                        String timestamp = "" + System.currentTimeMillis();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("ID",timestamp);
                        hashMap.put("IDRoomChat",idRoom);
                        hashMap.put("IDTaiKhoan",LoginActivity.ID_Tk);
                        hashMap.put("NoiDung",etEmoji.getText().toString().trim());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TinNhan");
                        reference.child(timestamp).setValue(hashMap);
                        etEmoji.getText().clear();
                    }
                    catch (Exception ex){
                        Log.e("Error","Có lỗi: "+ex);
                    }
                }



            }
        });
    }
}