package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class forgotPassword3 extends AppCompatActivity {
    FirebaseDatabase namefirebaseDatabase;
    DatabaseReference namedatabaseReference;
    TextInputLayout passwordTL,confirmpasswordTL;
    String usernamefb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password3);
        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");
        passwordTL = findViewById(R.id.password_fp3);
        confirmpasswordTL = findViewById(R.id.confirmpassword_fp3);
        Intent intent= getIntent();
        usernamefb = intent.getStringExtra("final_data");
    }

    public void updatePassword(View view) {
        String password = passwordTL.getEditText().getText().toString();
        String confirmPassword = confirmpasswordTL.getEditText().getText().toString();

        if(!password.isEmpty()){
            passwordTL.setError(null);
            passwordTL.setErrorEnabled(false);
            if(!confirmPassword.isEmpty()){
                confirmpasswordTL.setError(null);
                confirmpasswordTL.setErrorEnabled(false);
                if(password.length() > 6 && password.length() < 10){
                    passwordTL.setError(null);
                    passwordTL.setErrorEnabled(false);
                    if(confirmPassword.length() > 6 && confirmPassword.length() < 10){
                        confirmpasswordTL.setError(null);
                        confirmpasswordTL.setErrorEnabled(false);
                        if(password.equals(confirmPassword)){
                            passwordTL.setError(null);
                            passwordTL.setErrorEnabled(false);
                            confirmpasswordTL.setError(null);
                            confirmpasswordTL.setErrorEnabled(false);
                            update(password);
                        }  else{
                            confirmpasswordTL.setError("Not the same");
                        }
                    }else{
                        confirmpasswordTL.setError("Password too short");
                    }
                }else{
                    passwordTL.setError("Password too short");
                }
            }else{
                confirmpasswordTL.setError("Password can't be empty");
            }
        }else{
            passwordTL.setError("Password can't be empty");
        }
    }

    private void update(String password) {

        Toast.makeText(this, "" + password, Toast.LENGTH_SHORT).show();
        namedatabaseReference.child(usernamefb).child("password").setValue(password);
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}