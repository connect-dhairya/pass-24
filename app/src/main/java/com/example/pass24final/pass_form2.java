package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class pass_form2 extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    Button next,dateButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    AutoCompleteTextView destination,pass_type, source;
    String username_passform2,des,sou,passtype,finalDate,expiryDate;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_form2);


        Intent intent = getIntent();
        username_passform2 = intent.getStringExtra("username_passform1_to_passform2");


        next = findViewById(R.id.next_btn_pass_form);

        title = findViewById(R.id.title_text_pass_form2);

        title.setText("Hey " + username_passform2);

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        initDatePicker();
        //Date = findView
        //ById(R.id.entrydate_passform2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("UserPassDetails");

                String date_s = finalDate;
                String destination_s = des;
                String source_s = sou;
                String passtype_s = passtype;
                String passengertype_s = "COMMUTER PASS";
                String expiryDate_s =  getEndDate(finalDate,passtype);

                StoringPassDetails storingpassDetails = new StoringPassDetails(date_s,passtype_s,passengertype_s,source_s,destination_s,expiryDate_s);
                reference.child(username_passform2).setValue(storingpassDetails);

                Intent intent = new Intent(pass_form2.this, UploadActivity.class);
                intent.putExtra("username_passform2_to_Upload",username_passform2);
                startActivity(intent);
                finish();
            }
        });


        pass_type = findViewById(R.id.passtype_passform2);

        String[] itemPassType = new String[]{"--","Monthly","Quarterly"};
        ArrayAdapter<String> passTypeAdapter = new ArrayAdapter<>(this,R.layout.list_item,itemPassType);

        pass_type.setAdapter(passTypeAdapter);

        pass_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                passtype =  pass_type.getText().toString();
            }
        });



        destination = findViewById(R.id.destination_pass_form2);

        String[] destinationOccupation = new String[]{"--","Nadiad","Rajkot","Anand","Junagadh","Morbi","Surendranagar"};
//        String[] destinationOccupation = new String[]{"--","Ahmedabad","Nadiad","Rajkot","Anand"};
        ArrayAdapter<String> desAdapter = new ArrayAdapter<>(this,R.layout.list_item,destinationOccupation);

        destination.setAdapter(desAdapter);

        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                des =  destination.getText().toString();
            }
        });


        source = findViewById(R.id.source_passform2);

        String[] sourceOccupation = new String[]{"--","Ahmedabad","Gandhinagar","Vadodara","surat"};
//        String[] sourceOccupation = new String[]{"--","Ahmedabad","Nadiad","Rajkot","Anand"};
        ArrayAdapter<String> souAdapter = new ArrayAdapter<>(this,R.layout.list_item,sourceOccupation);

        source.setAdapter(souAdapter);

        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sou =  source.getText().toString();
            }
        });
    }

    private String getEndDate(String finalDate, String passtype) {

//        Toast.makeText(this, ""+ finalDate + " " +passtype, Toast.LENGTH_SHORT).show();

        String edate = finalDate.replaceFirst(" ","-");
        int y = edate.indexOf("-");
        int x = edate.indexOf(" ");
        String date = edate.substring(y+1,x);
        String month1 = edate.substring(0,3);
        String year1 = edate.substring(edate.length()-4,edate.length());
        return getOtherdate(date,month1,year1,passtype);
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
                String date = makeDateString(day, month, year);
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
        finalDate = getMonthFormat(month) + " " + day + " " + year;
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

    private String getOtherdate(String date, String month, String year, String passtype) {
        String endDate = date;
        String endMonth,endYear;
        int endYearInt = Integer.parseInt(year);
//        String  = passtype1;
        if(passtype.equals("Monthly")){
            switch(month){
                case "JAN": endMonth = "FEB"; break;
                case "FEB": endMonth = "MAR"; break;
                case "MAR": endMonth = "APR"; break;
                case "APR": endMonth = "MAY"; break;
                case "MAY": endMonth = "JUN"; break;
                case "JUN": endMonth = "JUL"; break;
                case "JUL": endMonth = "AUG"; break;
                case "AUG": endMonth = "SEP"; break;
                case "SEP": endMonth = "OCT"; break;
                case "OCT": endMonth = "NOV"; break;
                case "NOV": endMonth = "DEC"; break;
                case "DEC": endMonth = "JAN"; break;
                default: endMonth = "X";
            }

            if(endMonth.equals("JAN"))
                endYearInt++;

            endYear = String.valueOf(endYearInt);
        }else{
            switch(month){
                case "JAN": endMonth = "APR"; break;
                case "FEB": endMonth = "MAY"; break;
                case "MAR": endMonth = "JUN"; break;
                case "APR": endMonth = "JUL"; break;
                case "MAY": endMonth = "AUG"; break;
                case "JUN": endMonth = "SEP"; break;
                case "JUL": endMonth = "OCT"; break;
                case "AUG": endMonth = "NOV"; break;
                case "SEP": endMonth = "DEC"; break;
                case "OCT": endMonth = "JAN"; break;
                case "NOV": endMonth = "FEB"; break;
                case "DEC": endMonth = "MAR"; break;
                default: endMonth = "X";
            } 

            if(endMonth.equals("JAN") || endMonth.equals("FEB") || endMonth.equals("MAR"))
                endYearInt++;

            endYear = String.valueOf(endYearInt);

        }
//        Toast.makeText(this, endMonth  + " " + endDate + " " +  endYear, Toast.LENGTH_SHORT).show();
        return (endMonth  + " " + endDate + " " +  endYear);
    }
}
