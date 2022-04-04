package com.example.btlappgiaitri.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassActivity extends AppCompatActivity {
    ImageView backlogin ;
    Button btnConfirm;
    EditText et_Email,et_NewPass,et_UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        backlogin = findViewById(R.id.closeSG);
        btnConfirm = findViewById(R.id.btnConfirm);
        et_Email =findViewById(R.id.email_fg);
        et_UserName =findViewById(R.id.username_fg);
        et_NewPass =findViewById(R.id.new_pass_fg);


        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("TaiKhoan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //kiểm tra xem ID tài khoản tồn tại không
                        if(snapshot.hasChild(et_UserName.getText().toString().trim())&&!et_UserName.getText().toString().trim().equals("")){
                            reference.child("User").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName1) {
                                    User user = snapshot.getValue(User.class);
                                    //kiểm tra xem số điện thoại hoặc email có chính xác không
                                    if((user.getEmail().equals(et_Email.getText().toString().trim()) ||
                                        user.getSdt().equals(et_Email.getText().toString().trim()))&&
                                        user.getIdtk().equals(et_UserName.getText().toString().trim()))
                                    {
                                        reference.child("TaiKhoan").child(et_UserName.getText().toString().trim()).child("Pass").setValue(et_NewPass.getText().toString().trim());
                                        Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(ForgetPassActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    else{
                                        Toast.makeText(ForgetPassActivity.this,"Email hoặc sdt không chính xác",Toast.LENGTH_LONG).show();
                                    }
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
                        else {
                            Toast.makeText(ForgetPassActivity.this,"ID tài khoản không chính xác",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}