package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Sign_up_Activity extends AppCompatActivity {

    TextInputLayout RegEmail, RegPassword, RegName, RegPhoneNumber, RegUsername;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressBar progressBar;
    Button getOTP;
    String returnStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        RegEmail = findViewById(R.id.email);
        RegPassword = findViewById(R.id.password);
        RegUsername = findViewById(R.id.username);
        RegName = findViewById(R.id.fullname);
        RegPhoneNumber = findViewById(R.id.phoneNumber);
        progressBar = findViewById(R.id.progressbar_sending_otp);
        getOTP = findViewById(R.id.getOTP);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");


    }


    public void registerUser(View view) {

        String name = RegName.getEditText().getText().toString();
        String username = RegUsername.getEditText().getText().toString();
        String phoneNumber = RegPhoneNumber.getEditText().getText().toString();
        String email = RegEmail.getEditText().getText().toString();
        String password = RegPassword.getEditText().getText().toString();

        Query checkUser = FirebaseDatabase.getInstance().getReference("users"). orderByChild("username").equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    RegUsername.setError("user already exist");
                }
                else{
                    hello(name,username,phoneNumber,email,password,returnStr);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hello(String name, String username, String phoneNumber, String email, String password, String returnStr) {
        if (!name.isEmpty()) {
            RegName.setError(null);
            RegName.setErrorEnabled(false);
            if (!username.isEmpty()) {
                RegUsername.setError(null);
                RegUsername.setErrorEnabled(false);
                if (!phoneNumber.isEmpty()) {
                    RegPhoneNumber.setError(null);
                    RegPhoneNumber.setErrorEnabled(false);
                    if (!email.isEmpty()) {
                        RegEmail.setError(null);
                        RegEmail.setErrorEnabled(false);
                        if (!password.isEmpty()) {
                            RegPassword.setError(null);
                            RegPassword.setErrorEnabled(false);
                            if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                if(username.length()>6){
                                    RegUsername.setError(null);
                                    RegUsername.setErrorEnabled(false);
                                    if(password.length()>6) {
                                        RegPassword.setError(null);
                                        RegPassword.setErrorEnabled(false);
                                        if (phoneNumber.length() == 10) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            getOTP.setVisibility(View.INVISIBLE);
                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                    "+91 " + phoneNumber,
                                                    60,
                                                    TimeUnit.SECONDS,
                                                    Sign_up_Activity.this,
                                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                        @Override
                                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                            progressBar.setVisibility(View.GONE);
                                                            getOTP.setVisibility(View.VISIBLE);
                                                        }

                                                        @Override
                                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                                            progressBar.setVisibility(View.GONE);
                                                            getOTP.setVisibility(View.VISIBLE);
                                                            Toast.makeText(Sign_up_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                            progressBar.setVisibility(View.GONE);
                                                            getOTP.setVisibility(View.VISIBLE);
                                                            Intent intent = new Intent(Sign_up_Activity.this, OTPActivity.class);
                                                            intent.putExtra("name", name);
                                                            intent.putExtra("username", username);
                                                            intent.putExtra("phoneNumber", phoneNumber);
                                                            intent.putExtra("email", email);
                                                            intent.putExtra("password", password);
                                                            intent.putExtra("backendOtp", backendOtp);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                            );
                                        } else {
                                            RegPhoneNumber.setError("Invalid Phone number");
                                        }
                                    }else{
                                        RegPassword.setError("Password should be more than 6 letter");
                                        }
                                }else{
                                    RegUsername.setError("Username should be more than 6 letter");
                                    }
                            } else {
                                RegEmail.setError("Invalid email address");
                            }
                        } else {
                            RegPassword.setError("Password cannot be empty");
                        }
                    } else {
                        RegEmail.setError("Email cannot be empty");
                    }
                } else {
                    RegPhoneNumber.setError("Phone number cannot be empty");
                }
            } else {
                RegUsername.setError("Field cannot be empty");
            }
        } else {
            RegName.setError("Field cannot be empty");
        }
    }

    public void backTOLogin(View view) {
        Intent intent = new Intent(Sign_up_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
