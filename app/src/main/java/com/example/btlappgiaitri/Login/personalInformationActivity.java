package com.example.btlappgiaitri.Login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappgiaitri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class personalInformationActivity extends AppCompatActivity {

    EditText email,Name,sdt,que,date;
    Button btnadd;
    DatePickerDialog.OnDateSetListener setListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        email = findViewById(R.id.email_sg);
        Name = findViewById(R.id.txtName);
        sdt = findViewById(R.id.sdt);
        que = findViewById(R.id.QueQuan);
        date = findViewById(R.id.TextDate);
        btnadd = findViewById(R.id.btn);


        Calendar calendar = Calendar.getInstance();
         final int  year = calendar.get(calendar.YEAR);
         final int  month = calendar.get(calendar.MONTH);
         final int day = calendar.get(calendar.DAY_OF_MONTH);

         date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DatePickerDialog datePickerDialog = new DatePickerDialog(
                         personalInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpfirebaseUser();

            }
        });



    }

    private void UpfirebaseUser() {
        if(Isvalidate()){
            String timestamp = "" + System.currentTimeMillis();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("avatar","https://firebasestorage.googleapis.com/v0/b/mobileapp-b95db.appspot.com/o/hello.jpg?alt=media&token=37366c7b-2114-40f9-a613-2362338e71af");
            hashMap.put("date",""+ date.getText());
            hashMap.put("email",""+ email.getText());
            hashMap.put("idtk",""+ SignUpActivity.id_dk);
            hashMap.put("name",""+ Name.getText());
            hashMap.put("quequan",""+ que.getText());
            hashMap.put("sdt",""+ sdt.getText());
            hashMap.put("ID",""+timestamp);


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
            reference.child(timestamp)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(personalInformationActivity.this, "đã xong  ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(personalInformationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(personalInformationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    });
        }


    }

    private boolean Isvalidate() {
        if(Name.getText().toString().trim().length()==0){
            Toast.makeText(this, "fist Name is empty", Toast.LENGTH_SHORT).show();
                    return false;
        }
        else if(!isValidEmail(email.getText())){
            Toast.makeText(this, "Email not invalidate", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(sdt.getText().toString().trim().length() !=10){
            Toast.makeText(this, "phone number must have 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(que.getText().toString().trim().length() ==0){
            Toast.makeText(this, "countryside is empty", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}