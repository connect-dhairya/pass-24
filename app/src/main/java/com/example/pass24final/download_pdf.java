package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class download_pdf extends AppCompatActivity {

    TextView source,destination,username,startDate,expiryDate,payment,paymentMethod;
    String namefb,username_form_home,city,pricefb,destination_cityfb,source_cityfb,pass_datefb,end_datefb;
    FirebaseDatabase namefirebaseDatabase,paymentfirebaseDatabase,userPassDetailsfirebaseDatabase;
    DatabaseReference namedatabaseReference,paymentdatabaseReference,userPassDetailsdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_pdf);
        Intent intent = getIntent();
        username_form_home = intent.getStringExtra("username_home_to_downloadpdf");

        source = findViewById(R.id.source_text_pass);
        destination = findViewById(R.id.destination_text_pass);
        username = findViewById(R.id.username_text_pass);
        startDate = findViewById(R.id.start_date_text_pass);
        expiryDate = findViewById(R.id.expiry_date_text_pass);
        payment = findViewById(R.id.payment_text_pass);
        paymentMethod = findViewById(R.id.payment_method_text_pass);

        city =  "Ahmedabad2Rajkot";

//        startDate.setText("Start Date:\n18-03-2022");
//        expiryDate.setText("Expiry Date:\n15-04-2022");

        paymentMethod.setText("Payment Method\nRAZORPAY");

        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");

        paymentfirebaseDatabase = FirebaseDatabase.getInstance();
        paymentdatabaseReference = paymentfirebaseDatabase.getReference("passRelatedInfo");


        userPassDetailsfirebaseDatabase = FirebaseDatabase.getInstance();
        userPassDetailsdatabaseReference = userPassDetailsfirebaseDatabase.getReference("UserPassDetails");

        getName();
//        getPrice();
        getCity();
    }

    private void getDate() {
        userPassDetailsdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pass_datefb = snapshot.child(username_form_home).child("date").getValue(String.class);
                end_datefb = snapshot.child(username_form_home).child("expiryDate").getValue(String.class);
                startDate.setText("Start Date:\n" + pass_datefb);
                expiryDate.setText("Expiry Date:\n" + end_datefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCity() {
        userPassDetailsdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                destination_cityfb = snapshot.child(username_form_home).child("destination").getValue(String.class);
                source_cityfb = snapshot.child(username_form_home).child("source").getValue(String.class);
                source.setText("From\nSource:\n" + source_cityfb);
                destination.setText("To\nDestination:\n" + destination_cityfb);
                city = source_cityfb + "2" + destination_cityfb;
                getPrice(city);
                getDate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPrice(String city) {
        paymentdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pricefb = snapshot.child(city).child("Price").getValue(String.class);
                payment.setText("Payment\n" + pricefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getName() {
        namedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                namefb = snapshot.child(username_form_home).child("name").getValue(String.class);
                username.setText(namefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}