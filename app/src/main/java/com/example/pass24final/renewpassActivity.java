package com.example.pass24final;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class renewpassActivity  extends AppCompatActivity implements PaymentResultListener {

    TextView username, name;
    TextInputEditText price;
    Button dateButton;
    private DatePickerDialog datePickerDialog;
    TextInputLayout  note;
    String usernamefb, namefb, pricefb, city,date;
    FirebaseDatabase namefirebaseDatabase, paymentfirebaseDatabase,RenewPassfb, paidUserfirebaseDatabase, database;
    DatabaseReference namedatabaseReference, paymentdatabaseReference, RenewPassreference,paidUserdatabaseReference, refrence;
    int counter;
//    String counter;
    String sPaymentType = "", sTransactionId = "";
    ProgressDialog pd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_pass);

        dateButton = findViewById(R.id.renew_datePickerButton);
        dateButton.setText(getTodaysDate());
        initDatePicker();

       Button paynow_btn = findViewById(R.id.paynow_btn);

        paynow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { razorPayPaymentMethod(); }
        });

        Intent intent = getIntent();
        usernamefb = intent.getStringExtra("username_home_to_passform1");

        username = findViewById(R.id.username_renew);
        name = findViewById(R.id.name_renew);
        price = findViewById(R.id.price_renew);
        note = findViewById(R.id.upi_note_renew);

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
        setCounter();
    }

    private void setCounter() {
        RenewPassreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter =  snapshot.child(usernamefb).child("renew").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

            RenewPassreference.child(usernamefb).child("renew").setValue(counter + 1);
            RenewPassreference.child(usernamefb).child("renewHome").setValue("No");
            RenewPassreference.child(usernamefb).child("date").setValue(date);


            Intent intent = new Intent(renewpassActivity.this, HomeActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Your request has being accepted", Toast.LENGTH_SHORT).show();

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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
       String  finalDate = getMonthFormat(month) + " " + day + " " + year;
        return finalDate;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }


    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}








