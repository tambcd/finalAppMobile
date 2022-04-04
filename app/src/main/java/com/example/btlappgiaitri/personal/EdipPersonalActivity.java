package com.example.btlappgiaitri.personal;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EdipPersonalActivity extends AppCompatActivity {

    EditText email,Name,sdt,que,date;
    Button btnadd;
    String idupdate = null;
    ImageView backlogin ;
    DatePickerDialog.OnDateSetListener setListener;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edip_personal);

        email = findViewById(R.id.email_ed);
        Name = findViewById(R.id.txtNameed);
        sdt = findViewById(R.id.sdted);
        que = findViewById(R.id.QueQuaned);
        date = findViewById(R.id.TextDateed);
        btnadd = findViewById(R.id.btned);
        backlogin = findViewById(R.id.closeSGed);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });


        Calendar calendar = Calendar.getInstance();
        final int  year = calendar.get(calendar.YEAR);
        final int  month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EdipPersonalActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        m++;
                        String dateTime = d + "/" + m + "/" + y;
                        date.setText(dateTime);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);

                    assert user != null;
                    if(user.getIdtk().equals(LoginActivity.ID_Tk)){
                        Log.e("data" ,user.getEmail() );
                        email.setText(user.getEmail());
                        Name.setText(user.getName());
                        sdt.setText(user.getSdt());
                        que.setText(user.getQuequan());
                        date.setText(user.getDate());
                        idupdate = user.getID();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatefirebaseUser();

            }


        });

    }



    private void UpdatefirebaseUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(idupdate).child("email").setValue(email.getText().toString().trim());
        reference.child("User").child(idupdate).child("name").setValue(Name.getText().toString().trim());
        reference.child("User").child(idupdate).child("sdt").setValue(sdt.getText().toString().trim());
        reference.child("User").child(idupdate).child("quequan").setValue(que.getText().toString().trim());
        reference.child("User").child(idupdate).child("date").setValue(date.getText().toString().trim());
        Toast.makeText(this, "xong", Toast.LENGTH_SHORT).show();
        finish();

    }
}