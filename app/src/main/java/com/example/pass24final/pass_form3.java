package com.example.pass24final;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class pass_form3 extends AppCompatActivity implements PaymentResultListener {


    TextView username, name;
    TextInputEditText price;
    TextInputLayout note;
    String usernamefb, namefb, pricefb, city;
    FirebaseDatabase namefirebaseDatabase, RenewPassfb,paymentfirebaseDatabase, paidUserfirebaseDatabase, database;
    DatabaseReference namedatabaseReference, RenewPassreference,paymentdatabaseReference, paidUserdatabaseReference, refrence;

    String sPaymentType = "", sTransactionId = "";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_form3);

        Button paynow_btn = findViewById(R.id.paynow_btn);

        paynow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                razorPayPaymentMethod();
            }
        });

        Intent intent = getIntent();
        usernamefb = intent.getStringExtra("username_Upload_to_passform3");

        username = findViewById(R.id.username_passform3);
        name = findViewById(R.id.name_passform3);
        price = findViewById(R.id.price_passform3);

        city = "Ahmedabad2Rajkot";

        username.setText(usernamefb);


        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");

        database = FirebaseDatabase.getInstance();
        refrence = namefirebaseDatabase.getReference("passRelatedInfo");

        paymentfirebaseDatabase = FirebaseDatabase.getInstance();
        paymentdatabaseReference = paymentfirebaseDatabase.getReference("passRelatedInfo");

        paidUserfirebaseDatabase = FirebaseDatabase.getInstance();
        paidUserdatabaseReference = paidUserfirebaseDatabase.getReference("PaidUserDetails");

        RenewPassfb= FirebaseDatabase.getInstance();
        RenewPassreference = RenewPassfb.getReference("RenewPass");

        getName();
        getPrice();
    }

    private void razorPayPaymentMethod() {
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Pass 24");
            //options.put("description", "Demoing Charges");
            options.put("description", "Product Details");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(price.getText().toString()) * 100);
//            options.put("amount", Double.parseDouble(String.valueOf(iTotal)) * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "vaghelajay960@gmail.com");
            preFill.put("contact", "9104514607");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Log.d("RESPONSE", e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            sTransactionId = razorpayPaymentID;
            sPaymentType = "Online";
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();

            pd.dismiss();
            Toast.makeText(this, "Your request has being accepted", Toast.LENGTH_SHORT).show();

            namedatabaseReference.child(usernamefb).child("pass").setValue("Requested");

            StoringRenewPassDetails StoringRenewPassDetails = new StoringRenewPassDetails("No",0,"_");
            RenewPassreference.child(usernamefb).setValue(StoringRenewPassDetails);


            Intent intent = new Intent(pass_form3.this, HomeActivity.class);
            startActivity(intent);



            Toast.makeText(this, "Pass will be avaiable shortly in the view pass section", Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            Log.e("RESPONSE", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Log.d("RESPONSE", "Payment Cancelled " + code + " " + response);
            //Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("RESPONSE", "Exception in onPaymentError", e);
        }
    }

    private void getName() {
        namedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                namefb = snapshot.child(usernamefb).child("name").getValue(String.class);
                name.setText(namefb);
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
                price.setText(pricefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}










