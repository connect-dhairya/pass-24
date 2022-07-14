package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pass_form1 extends AppCompatActivity {
TextInputLayout RegAddress,RegAge;
AutoCompleteTextView RegOccupation, RegEducation, RegCatagory;
RadioGroup radioGroup;
TextView title;
RadioButton radioButton;
String username_passform1;
FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
String occup,edu,cata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_form1);

        title = findViewById(R.id.title_text_pass_form1);

        Intent intent = getIntent();
        username_passform1 = intent.getStringExtra("username_home_to_passform1");



        title.setText("Hey " + username_passform1);

        radioGroup = findViewById(R.id.gender_group);

        RegAddress = findViewById(R.id.address_otherdetails);
        RegAge = findViewById(R.id.age_otherdetails);

        RegOccupation = findViewById(R.id.occupation_otherdetails);

        String[] itemOccupation = new String[]{"--","Salaried","Business"};
        ArrayAdapter<String> occAdapter = new ArrayAdapter<>(this,R.layout.list_item,itemOccupation);

        RegOccupation.setAdapter(occAdapter);

        RegOccupation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                occup =  RegOccupation.getText().toString();
            }
        });

        RegEducation = findViewById(R.id.eduaction_otherdetails);

        String[] itemEducation = new String[]{"--","SSLC","Degree","Post Grad","PHD","Inter"};
        ArrayAdapter<String> Eduadapter = new ArrayAdapter<>(this,R.layout.list_item,itemEducation);

        RegEducation.setAdapter(Eduadapter);

        RegEducation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edu =  RegEducation.getText().toString();
            }
        });

        RegCatagory = findViewById(R.id.category_otherdetails);

        String[] itemCategory = new String[]{"--","General","SCBC","ST","SE"};
        ArrayAdapter<String> Cateadapter = new ArrayAdapter<>(this,R.layout.list_item,itemCategory);

        RegCatagory.setAdapter(Cateadapter);

        RegCatagory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cata =  RegCatagory.getText().toString();
            }
        });
    }
    public void checkButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
    }
    public void passform1method(View view) {
        String address = RegAddress.getEditText().getText().toString();
        String age = RegAge.getEditText().getText().toString();


        if(!address.isEmpty()){
            RegAddress.setError(null);
            RegAddress.setErrorEnabled(false);
            if(!age.isEmpty()){
                RegAge.setError(null);
                RegAge.setErrorEnabled(false);
                    passform1methodIndatabase();
            }else{
                RegAge.setError("Age cannot be empty");
            }
        }else{
            RegAddress.setError("Address cannot be empty");
        }
    }

    private void passform1methodIndatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("otherdetails");

        String address_s = RegAddress.getEditText().getText().toString();
        String age_s = RegAge.getEditText().getText().toString();
        String occupation_s = occup;
        String education_s = edu;
        String catagory_s = cata;
        String gender_s = radioButton.getText().toString();

        StoringOtherDetails storingOtherDetails = new StoringOtherDetails(gender_s,address_s,age_s,occupation_s,education_s,catagory_s);
        reference.child(username_passform1).setValue(storingOtherDetails);

        Intent intent = new Intent(pass_form1.this, pass_form2.class);
        intent.putExtra("username_passform1_to_passform2",username_passform1);
        startActivity(intent);
        finish();
    }
}