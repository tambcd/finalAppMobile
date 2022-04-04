package com.example.btlappgiaitri.navigation_bar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.btlappgiaitri.AddVideo.AddVideoActivity;
import com.example.btlappgiaitri.Home.HomeActivity;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.LoiMoi;
import com.example.btlappgiaitri.Models.Notifycation;
import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.entertain.MovieActivity;
import com.example.btlappgiaitri.message.MessageActivity;
import com.example.btlappgiaitri.personal.PersonalActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class navigationBarActivity extends AppCompatActivity {
    public static ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    public  static  String id_user = LoginActivity.ID_Tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        chipNavigationBar = findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeActivity()).commit();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();

        reference.child("Videos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chipNavigationBar.showBadge(R.id.home);
                return;
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
        reference.child("LoiMoi").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LoiMoi loiMoi = snapshot.getValue(LoiMoi.class);
                if(loiMoi.getIDTKNhan().equals(LoginActivity.ID_Tk)){
                    chipNavigationBar.showBadge(R.id.utilities);
                    return;
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
        reference.child("thongBao").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notifycation notifycation = snapshot.getValue(Notifycation.class);
                if(notifycation.getIDTKN().equals(LoginActivity.ID_Tk)){
                    chipNavigationBar.showBadge(R.id.message);
                    return;
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

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        chipNavigationBar.dismissBadge(R.id.home);
                        fragment = new HomeActivity();
                        break;
                    case R.id.utilities:
                        chipNavigationBar.dismissBadge(R.id.utilities);
                        fragment = new MovieActivity();
                        break;
                    case R.id.btnAdd:
                        chipNavigationBar.dismissBadge(R.id.btnAdd);
                        fragment = new AddVideoActivity();
                        break;
                    case R.id.message:
                        chipNavigationBar.dismissBadge(R.id.message);
                        fragment = new MessageActivity();
                        break;
                    case R.id.Person:
                        chipNavigationBar.dismissBadge(R.id.Person);
                        id_user =  LoginActivity.ID_Tk;
                        fragment = new PersonalActivity();
                        break;
                }

                if (fragment != null) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });


    }
}