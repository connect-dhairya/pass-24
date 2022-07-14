package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

public class profileActivity extends AppCompatActivity {
    TextView name,username;
    TextInputLayout username_edit,fullname_edit,phoneNumber_edit,email_edit;
    String usernamefb ,namefb ,emailfb , phoneNumberfb ;
    Button update_btn;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.test_name);
        username = findViewById(R.id.test_username);
        username_edit = findViewById(R.id.username_test);
        phoneNumber_edit = findViewById(R.id.phoneNumber_test);
        fullname_edit = findViewById(R.id.name_test);
        email_edit = findViewById(R.id.email_test);
        update_btn =findViewById(R.id.update_btn_test);

        DatabaseReference reference;
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_btn_clicked();
            }
        });
        showalluserdata();
    }

    private void showalluserdata() {
        Intent intent = getIntent();
        String total_string = intent.getStringExtra("total_string");

        // final_data_string = usernameGetter + "_" + nameGetter + "%" + emailGetter + "$" + phoneGetter;
        int number1 = total_string.indexOf("_");
        int number2 = total_string.indexOf("%");
        int number3 = total_string.indexOf("$");
         usernamefb = total_string.substring(0,number1);
         namefb = total_string.substring(number1+1,number2);
         emailfb = total_string.substring(number2+1,number3);
         phoneNumberfb = total_string.substring(number3+1,total_string.length());

        name.setText(namefb);
        username.setText(usernamefb);

        username_edit.getEditText().setText(namefb);
        phoneNumber_edit.getEditText().setText(phoneNumberfb);
        fullname_edit.getEditText().setText(usernamefb );
        email_edit.getEditText().setText(emailfb);
    }

    private void update_btn_clicked() {
        if(nameChange() || phoneNumberChange() || emailChange()){
            Toast.makeText(profileActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(profileActivity.this, "No changes were made", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean emailChange() {
        if (!emailfb.equals(email_edit.getEditText().getText().toString())) {
            reference.child(usernamefb).child("email").setValue(email_edit.getEditText().getText().toString());
            emailfb = email_edit.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean phoneNumberChange() {
        if(!phoneNumberfb.equals(phoneNumber_edit.getEditText().getText().toString())){
            reference.child(usernamefb).child("phoneNumber").setValue(phoneNumber_edit.getEditText().getText().toString());
            phoneNumberfb = phoneNumber_edit.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean nameChange() {
        if(!namefb.equals(fullname_edit.getEditText().getText().toString())){
            reference.child(usernamefb).child("name").setValue(fullname_edit.getEditText().getText().toString());
            namefb = fullname_edit.getEditText().getText().toString();
            name.setText(fullname_edit.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }
}
