package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText inputnumber1,inputnumber2,inputnumber3,inputnumber4,inputnumber5,inputnumber6;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String getOtpBackend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        final Button verifyButton = findViewById(R.id.submit_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");

        inputnumber1 = findViewById(R.id.inputotp1);
        inputnumber2 = findViewById(R.id.inputotp2);
        inputnumber3 = findViewById(R.id.inputotp3);
        inputnumber4 = findViewById(R.id.inputotp4);
        inputnumber5 = findViewById(R.id.inputotp5);
        inputnumber6 = findViewById(R.id.inputotp6);
        final ProgressBar progressBar = findViewById(R.id.progressbar_verify_otp);

        TextView textView = findViewById(R.id.show_contact_no);
        textView.setText(String.format(
                "+91-%s" , getIntent().getStringExtra("phoneNumber")
        ));

        Intent intent = getIntent();
        String name_s = intent.getStringExtra("name");
        String username_s =intent.getStringExtra("username");
        String phoneNumber_s =intent.getStringExtra("phoneNumber");
        String email_s =intent.getStringExtra("email");
        String password_s =intent.getStringExtra("password");
        getOtpBackend = getIntent().getStringExtra("backendOtp");

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_inputnumber1 = inputnumber1.getText().toString().trim();
                String txt_inputnumber2 = inputnumber2.getText().toString().trim();
                String txt_inputnumber3 = inputnumber3.getText().toString().trim();
                String txt_inputnumber4 = inputnumber4.getText().toString().trim();
                String txt_inputnumber5 = inputnumber5.getText().toString().trim();
                String txt_inputnumber6 = inputnumber6.getText().toString().trim();


                if(!txt_inputnumber1.isEmpty() && !txt_inputnumber2.isEmpty() &&!txt_inputnumber3.isEmpty() &&!txt_inputnumber4.isEmpty() &&!txt_inputnumber5.isEmpty() &&!txt_inputnumber6.isEmpty()){

                    String enterCodeOtp = txt_inputnumber1 + txt_inputnumber2 + txt_inputnumber3 +txt_inputnumber4 +txt_inputnumber5 +txt_inputnumber6;

                    if(getOtpBackend!=null){
                        progressBar.setVisibility(View.GONE);
                        verifyButton.setVisibility(View.VISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getOtpBackend,enterCodeOtp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                verifyButton.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    String pass = "no";
                                    StoringData storingData = new StoringData(name_s , username_s, email_s, password_s, phoneNumber_s, pass);
                                    reference.child(username_s).setValue(storingData);
                                    String final_data_string = username_s;
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    intent.putExtra("final_data",final_data_string);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(OTPActivity.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(OTPActivity.this, "Check internet!!!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(OTPActivity.this, "otp verify", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(OTPActivity.this, "please enter all nunmber", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOtpMove();

        TextView resendLabel = findViewById(R.id.textResend);

        resendLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getOtpBackend =newbackendOtp;
                                Toast.makeText(OTPActivity.this, "OTP sent succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberOtpMove() {
        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}