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

public class passActivity extends AppCompatActivity {

    TextView source,destination,username,startDate,expiryDate,payment,paymentMethod;
    String namefb,username_form_home,city,pricefb,destination_cityfb,source_cityfb;
    FirebaseDatabase namefirebaseDatabase,paymentfirebaseDatabase,cityfirebaseDatabase;
    DatabaseReference namedatabaseReference,paymentdatabaseReference,citydatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        Intent intent = getIntent();
        username_form_home = intent.getStringExtra("username_passform3_to_passActivity");

        source = findViewById(R.id.source_text_pass);
        destination = findViewById(R.id.destination_text_pass);
        username = findViewById(R.id.username_text_pass);
        startDate = findViewById(R.id.start_date_text_pass);
        expiryDate = findViewById(R.id.expiry_date_text_pass);
        payment = findViewById(R.id.payment_text_pass);
        paymentMethod = findViewById(R.id.payment_method_text_pass);

        city =  "Ahmedabad2Rajkot";

        startDate.setText("Start Date:\n18-03-2022");
        expiryDate.setText("Expiry Date:\n15-04-2022");

        paymentMethod.setText("Payment Method\nPATYM");

        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");

        paymentfirebaseDatabase = FirebaseDatabase.getInstance();
        paymentdatabaseReference = paymentfirebaseDatabase.getReference("passRelatedInfo");


        cityfirebaseDatabase = FirebaseDatabase.getInstance();
        citydatabaseReference = cityfirebaseDatabase.getReference("UserPassDetails");

        getName();
        getPrice();
        getCity();
    }

    private void getCity() {
        citydatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                destination_cityfb = snapshot.child(username_form_home).child("destination").getValue(String.class);
                source_cityfb = snapshot.child(username_form_home).child("source").getValue(String.class);
                source.setText("From\nSource:\n" + source_cityfb);
                destination.setText("To\nDestination:\n" + destination_cityfb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPrice() {
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
                namefb =  snapshot.child(username_form_home).child("name").getValue(String.class);
                username.setText(namefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}