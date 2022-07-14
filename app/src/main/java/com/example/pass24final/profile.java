package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView name,username;
    TextInputLayout username_edit,fullname_edit,phoneNumber_edit,email_edit;
    Button update_btn;
//    private static final String FILE_NAME = "myFile";
    String usernamefb ,namefb ,emailfb , phoneNumberfb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);


//        SharedPreferences preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
//        String usernamefb = preferences.getString("username","Data not found");

        Intent intent = getIntent();
        usernamefb = intent.getStringExtra("total_string");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        name = findViewById(R.id.test_name);
        username = findViewById(R.id.test_username);
        username_edit = findViewById(R.id.username_test);
        phoneNumber_edit = findViewById(R.id.phoneNumber_test);
        fullname_edit = findViewById(R.id.name_test);
        email_edit = findViewById(R.id.email_test);
        update_btn =findViewById(R.id.update_btn_test);
        username.setText(usernamefb);
        getdata();

        update_btn.setOnClickListener(view -> update_btn_clicked());
    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  String value = snapshot.child(total_string).child("name").getValue(String.class);

                namefb = snapshot.child(usernamefb).child("name").getValue(String.class);
                emailfb = snapshot.child(usernamefb).child("email").getValue(String.class);
                phoneNumberfb = snapshot.child(usernamefb).child("phoneNumber").getValue(String.class);
                //String usernameGetter = snapshot.child(usernamefb).child("username").getValue(String.class);

                name.setText(namefb);

                username_edit.getEditText().setText(usernamefb);
                phoneNumber_edit.getEditText().setText(phoneNumberfb);
                fullname_edit.getEditText().setText(namefb);
                email_edit.getEditText().setText(emailfb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update_btn_clicked() {
        if(nameChange() || phoneNumberChange() || emailChange()){
            Toast.makeText(profile.this, "Data Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(profile.this, "No changes were made", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean emailChange() {
        if(!emailfb.equals(email_edit.getEditText().getText().toString())){
            databaseReference.child(usernamefb).child("email").setValue(email_edit.getEditText().getText().toString());
            emailfb = email_edit.getEditText().getText().toString();

            return true;
        }else {
            return false;
        }
    }

    private boolean phoneNumberChange() {
        if(!phoneNumberfb.equals(phoneNumber_edit.getEditText().getText().toString())){
            databaseReference.child(usernamefb).child("phoneNumber").setValue(phoneNumber_edit.getEditText().getText().toString());
            phoneNumberfb = phoneNumber_edit.getEditText().getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean nameChange() {
        if(!namefb.equals(fullname_edit.getEditText().getText().toString())){
            databaseReference.child(usernamefb).child("name").setValue(fullname_edit.getEditText().getText().toString());
            namefb = fullname_edit.getEditText().getText().toString();
            name.setText(fullname_edit.getEditText().getText().toString());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
