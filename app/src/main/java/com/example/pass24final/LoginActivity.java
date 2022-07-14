package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFile";
    TextView title,topic;
    TextInputLayout username,password;
    Button go,signUp;
    CheckBox remember_me;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.usernameLogin);
        go = findViewById(R.id.go_btn);
        signUp = findViewById(R.id.sign_btn);
//        go = findViewById(R.id.go_btn);
        title = findViewById(R.id.welcome_string);
        topic = findViewById(R.id.sign_text);
        password =  findViewById(R.id.passwordLogin);
        remember_me = findViewById(R.id.remembermebutton);


        SharedPreferences preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        String usernameStr = preferences.getString("username","");
        String passwordStr = preferences.getString("password","");
        username.getEditText().setText(usernameStr);
        password.getEditText().setText(passwordStr);

//        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
//        String checkbox = preferences.getString("remember","");
//        if(checkbox.equals("true")){
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//        }else if(checkbox.equals("false")){
//            Toast.makeText(LoginActivity.this, "" + username, Toast.LENGTH_SHORT).show();
//        }
//
//
//        remember_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(compoundButton.isChecked()){
//
//                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("remember","true");
//                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
//
//                }else if (!compoundButton.isChecked()){
//
//                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("remember","false");
//                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getEditText().getText().toString();
                String txt_password = password.getEditText().getText().toString();

                if(!txt_username.isEmpty()){
                    username.setError(null);
                    username.setErrorEnabled(false);
                    if(!txt_password.isEmpty()){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        loginTODatabase();

                    }else{
                        password.setError("password cannot be empty");
                    }
                }else{
                    username.setError("username cannot be empty");
                }

            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Sign_up_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void loginTODatabase() {

        final String username_data = username.getEditText().getText().toString();
        final String password_data = password.getEditText().getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");

        Query check_username = reference.orderByChild("username").equalTo(username_data);

        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passwordCheck = snapshot.child(username_data).child("password").getValue(String.class);
                    if(passwordCheck.equals(password_data)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        if(remember_me.isChecked()) {
                            storedData(username_data, password_data);
                        }
                        startActivity(new Intent(    LoginActivity.this,HomeActivity.class));
//
//                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
//
//                        intent.putExtra("final_data",username_data);

//                        startActivity(intent);

                        finish();

                    }else{
                        password.setError("Wrong password");
                    }
                }else{
                    username.setError("username doesn't exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callForgetPassword(View view) {
        Intent intent = new Intent(LoginActivity.this,forgotPassword1.class);
        startActivity(intent);
    }

    private void storedData(String txt_username, String txt_password) {
//        Toast.makeText(LoginActivity.this, txt_username, Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor =  getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString("username",txt_username);
        editor.putString("password",txt_password);
        editor.apply();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
