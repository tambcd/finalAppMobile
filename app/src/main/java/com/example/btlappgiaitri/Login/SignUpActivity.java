package com.example.btlappgiaitri.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    ImageView backlogin;
    EditText et_User,et_Pass,et_RePass;
    Button btnSignUp;
    public static String id_dk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        backlogin = findViewById(R.id.closeSG);
        btnSignUp =findViewById(R.id.btnSignup);
        et_User = findViewById(R.id.usrename);
        et_Pass =findViewById(R.id.pass);
        et_RePass = findViewById(R.id.Re_pass);


        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check =true ;
                //check thông tin tài khoản có hợp lệ không
                if(CheckUser(et_User.getText().toString())){
                    check =false;
                    Toast.makeText(SignUpActivity.this,"Username không được chứa các ký tự '.', '#', '$', '[', hoặc ']' ",Toast.LENGTH_LONG).show();
                }

                if(et_User.getText().toString().trim().equals("")){
                    check =false;
                    Toast.makeText(SignUpActivity.this,"Nhập username",Toast.LENGTH_LONG).show();
                }
                if(et_Pass.getText().toString().trim().equals("")){
                    check =false;
                    Toast.makeText(SignUpActivity.this,"Nhập pass",Toast.LENGTH_LONG).show();
                }

                if(!et_Pass.getText().toString().trim().equals(et_RePass.getText().toString().trim())){
                    check =false;
                    Toast.makeText(SignUpActivity.this,"Mật khẩu không khớp",Toast.LENGTH_LONG).show();
                }
                //check xem tài khoản đã có trong firebase hay chưa
                boolean finalCheck = check;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TaiKhoan");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(finalCheck ==false){
                            return;
                        }
                        //nếu chưa có thì thêm vào firebase
                        if(!snapshot.hasChild(et_User.getText().toString().trim())){
                            CreateAccount(et_User.getText().toString().trim(),et_Pass.getText().toString().trim());
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"Tài khoản đã tồn tại!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    //hàm thêm thông tin tài khoản mới vào firebase
    private void CreateAccount(String s1,String s2) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("ID",s1);
        hashMap.put("Pass",s2);

        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TaiKhoan");
            reference.child(s1)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            id_dk = s1;
                            Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, personalInformationActivity.class);
                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        catch (Exception ex){
            Log.e("Error","Có lỗi: "+ex);
        }
    }

    String listCheckUser[] = {".","#","$","[","]"};
    private boolean CheckUser(String s){
        for (String item: listCheckUser) {
            if(s.contains(item)){
                return true;
            }
        }
        return false;
    }


}