package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class forgotPassword1 extends AppCompatActivity {

    String username;
    TextInputLayout usernameTL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);

        usernameTL = findViewById(R.id.forgetphone);
    }

    public void performForgetPassword(View view) {
        username = usernameTL.getEditText().getText().toString();
        if(!username.isEmpty()){
            usernameTL.setError(null);
            usernameTL.setErrorEnabled(false);
            nextActivity(username);
        }else{
            usernameTL.setError("Invalid");
        }
    }

    private void nextActivity(String username) {
        Query checkUser = FirebaseDatabase.getInstance().getReference("users"). orderByChild("username").equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usernameTL.setError(null);
                    usernameTL.setErrorEnabled(false);
                    String phoneNumber = snapshot.child(username).child("phoneNumber").getValue(String.class);
                    Toast.makeText(forgotPassword1.this, "You will get OTP on your given number:" + phoneNumber, Toast.LENGTH_SHORT).show();
                    ForgetPassword(phoneNumber);
                }

                else{
                    usernameTL.setError("No user found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ForgetPassword(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91 " + phoneNumber,
                60,
                TimeUnit.SECONDS,
                forgotPassword1.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(forgotPassword1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Intent intent = new Intent(forgotPassword1.this, forgotPassword2.class);
                        intent.putExtra("backendOtp", backendOtp);
                        intent.putExtra("phoneNumber", phoneNumber);
                        intent.putExtra("username_forgot1_2", username);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(forgotPassword1.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}