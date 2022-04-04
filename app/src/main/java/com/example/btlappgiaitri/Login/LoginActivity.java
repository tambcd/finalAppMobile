package com.example.btlappgiaitri.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.navigation_bar.navigationBarActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class LoginActivity extends AppCompatActivity {

    public static String ID_Tk;
    TextView signup,forgetpass;
    Button btnlogin;
    EditText etUserName ,etPassword;
    ImageView sun, dayland, nightland;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ID_Tk = null;
        sun = findViewById(R.id.sun);
        dayland = findViewById(R.id.day_landscape);
        nightland = findViewById(R.id.night_landscape);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);
        signup = (TextView) findViewById(R.id.txtsignup) ;
        forgetpass = (TextView) findViewById(R.id.forgetPass);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        etUserName = (EditText) findViewById(R.id.Email_lg);
        etPassword = (EditText) findViewById(R.id.Pass_lg);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserName.setText("");
                etPassword.setText("");
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserName.setText("");
                etPassword.setText("");
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TaiKhoan");

                    reference.child(etUserName.getText().toString()).child("Pass").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //kiểm tra nếu snapshot.getValue() không null và = mật khẩu thì đăng nhập thành công
                            if(etPassword.getText().toString().equals(snapshot.getValue(String.class))){
                                ID_Tk =  etUserName.getText().toString();
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                etUserName.setText("");
                                etPassword.setText("");

                                Intent intent = new Intent(LoginActivity.this, navigationBarActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                catch (Exception ex){
                    Log.w("Warning","Tài khoản mật khẩu không chính xác");
                    Toast.makeText(LoginActivity.this, "Tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sun.setTranslationY(-110);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night){
                    sun.animate().translationY(110).setDuration(1000);
                    dayland.animate().alpha(0).setDuration(1300);
                    daySky.animate().alpha(0).setDuration(1300);
                } else {
                    sun.animate().translationY(-110).setDuration(1000);
                    dayland.animate().alpha(1).setDuration(1300);
                    daySky.animate().alpha(1).setDuration(1300);
                }
            }
        });

    }


}