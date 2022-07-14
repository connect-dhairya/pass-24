package com.example.pass24final;

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


import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class
TimeTableActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    Button date;
    AutoCompleteTextView destination, source ;
    String notFormatDate,des,sou,finalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username_home_to_timetable");

        TextView textView = findViewById(R.id.tt_user);
//        textView.setText("Hey "+ username + "!");

        date = findViewById(R.id.datePickerButton_timetable);
        date.setText(getTodaysDate());
        initDatePicker();

        source = findViewById(R.id.source_timetable);

        String[] sourceOccupation = new String[]{"--","Ahmedabad","Anand","Gandhinagar","Vadodara"};
//        String[] sourceOccupation = new String[]{"--","Ahmedabad","Nadiad","Rajkot","Anand"};
        ArrayAdapter<String> souAdapter = new ArrayAdapter<>(this,R.layout.list_item,sourceOccupation);

        source.setAdapter(souAdapter);

        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sou =  source.getText().toString();
            }
        });

        destination = findViewById(R.id.destination_timetable);

        String[] destinationOccupation = new String[]{"--","Nadiad","Rajkot","Anand","Ahmedabad","Gandhinagar","Vadodara"};
//        String[] destinationOccupation = new String[]{"--","Ahmedabad","Nadiad","Rajkot","Anand"};
        ArrayAdapter<String> desAdapter = new ArrayAdapter<>(this,R.layout.list_item,destinationOccupation);

        destination.setAdapter(desAdapter);

        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                des =  destination.getText().toString();
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
                String d = makeDateString(day, month, year);
                date.setText(d);
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
        notFormatDate = day + "/"+ month + "/" + year;
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


    public void tt1TOtt2(View view) {

        if(!sou.isEmpty()){
            if(!des.isEmpty()){
                OpenActivity();
            }else{
                des = "--";
            }
        }else{
            sou = "--";
        }
    }

    private void OpenActivity() {
        String day = finalDate + " | " + getDay();
//        Toast.makeText(this, ""+day, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TimeTableActivity.this, TimeTableActivity2.class);
        intent.putExtra("source", sou);
        intent.putExtra("destination", des);
        intent.putExtra("day", day);
        startActivity(intent);
        finish();

    }

    private String getDay() {
        int x = notFormatDate.indexOf("/");
        String day = notFormatDate.substring(0,x);

        String notFormatDate2 = notFormatDate.replaceFirst("/","|");
        int y = notFormatDate2.indexOf("/");
        String month = notFormatDate.substring(x+1,y);

        int len = notFormatDate.length();
        String year = notFormatDate.substring(y+1,len);

        int dayInt = Integer.parseInt(day);
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);

        int num2,num3 = 6 ,num4 , finalDate1;
        yearInt = yearInt  - 2000;

        num4 = yearInt/4;

        switch (monthInt){
            case 1: num2 = 1;break;
            case 2: num2 = 4;break;
            case 3: num2 = 4;break;
            case 4: num2 = 0;break;
            case 5: num2 = 2;break;
            case 6: num2 = 5;break;
            case 7: num2 = 0;break;
            case 8: num2 = 3;break;
            case 9: num2 = 6;break;
            case 10: num2 = 1;break;
            case 11: num2 = 4;break;
            case 12: num2 = 6;break;
            default: num2 = 0;
        }

        finalDate1 = dayInt + num2 + num3 + yearInt +num4;
        finalDate1 = finalDate1%7;

        String realDay;
        switch (finalDate1){
            case 0: realDay= "Saturday";break;
            case 1: realDay= "Sunday";break;
            case 2: realDay= "Monday";break;
            case 3: realDay= "Tuesday";break;
            case 4: realDay= "Wednesday";break;
            case 5: realDay= "Thursday";break;
            case 6: realDay= "Friday";break;
            default: realDay= "Saturday";
        }

        return realDay;
//        Toast.makeText(this, " "+ realDay, Toast.LENGTH_SHORT).show();

    }
}
