package com.example.btlappgiaitri;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.btlappgiaitri.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    VideoView demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo = findViewById(R.id.videologo);
        demo.setVideoURI(Uri.parse("android.resource://"  + getPackageName() +"/" + R.raw.introduce));
        demo.start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=  new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}