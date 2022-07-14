package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTableActivity2 extends AppCompatActivity {
    ListView listView;
    String[] title;
    TextView sourceText,destinationText,dayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table2);

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        String destination = intent.getStringExtra("destination");
        String day = intent.getStringExtra("day");

        sourceText = findViewById(R.id.source_timetable2);
        destinationText = findViewById(R.id.destination_timetable2);
        dayText = findViewById(R.id.daydate);

        int x = day.indexOf("|");
        String realDay = day.substring(x+2,day.length());

        sourceText.setText(source);
        destinationText.setText(destination);
        dayText.setText(day);

        String daystr;


        if(realDay.matches("Sunday")){
            daystr = "sunday";
        }else{
            daystr = "Nosunday";
        }

        String finaldata = source + destination + daystr;

//        Toast.makeText(this, bBoolean  + "1" + realDay , Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, day + "1" + realDay, Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.list_view);


        switch(finaldata){
            case "AhmedabadNadiadNosunday" : getAhmedabadNadiadNosundayTimetable(); break;
            case "AhmedabadNadiadsunday" : getAhmedabadNadiadsundayTimetable(); break;
            case "AhmedabadRajkotNosunday" : getAhmedabadRajkotNosundayTimetable(); break;
            case "AhmedabadRajkotsunday" : getAhmedabadRajkotsundayTimetable(); break;
            case "AhmedabadAnandNosunday" : getAhmedabadAnandNosundayTimetable(); break;
            case "AhmedabadAnandsunday" : getAhmedabadAnandsundayTimetable(); break;
            case "AhmedabadVadodarasunday" : getAhmedabadvadodarasundayTimetable(); break;
            case "AhmedabadVadodaraNosunday" : getAhmedabadvadodaraNosundayTimetable(); break;
            case "AhmedabadAhmedabadsunday" : NoBus(); break;
            case "AhmedabadAhmedabadNosunday" : NoBus(); break;
            case "AhmedabadGandhinagarsunday" : getAhmedabadgandhinagarsundayTimetable(); break;
            case "AhmedabadGandhinagarNosunday" : getAhmedabadgandhinagarNosundayTimetable(); break;

            case "AnandAhmedabadsunday" : getAnandAhmedabadsundayTimetable(); break;
            case "AnandAhmedabadNosunday" : getAnandAhmedabadNosundayTimetable(); break;
            case "AnandNadiadNosunday" : getAhmedabadNadiadNosundayTimetable(); break;
            case "AnandNadiadsunday" : getAhmedabadNadiadsundayTimetable(); break;
            case "AnandRajkotNosunday" : getAhmedabadRajkotNosundayTimetable(); break;
            case "AnandRajkotsunday" : getAhmedabadRajkotsundayTimetable(); break;
            case "AnandVadodarasunday" : getAhmedabadvadodarasundayTimetable(); break;
            case "AnandVadodaraNosunday" : getAhmedabadvadodaraNosundayTimetable(); break;
            case "AnandGandhinagarsunday" : getAhmedabadgandhinagarsundayTimetable(); break;
            case "AnandGandhinagarNosunday" : getAhmedabadgandhinagarNosundayTimetable(); break;
            case "AnandAnandsunday" : NoBus(); break;
            case "AnandAnandNosunday" : NoBus(); break;


            case "GandhinagarNadiadNosunday" : getAhmedabadNadiadNosundayTimetable(); break;
            case "GandhinagarNadiadsunday" : getAhmedabadNadiadsundayTimetable(); break;
            case "GandhinagarRajkotNosunday" : getAhmedabadRajkotNosundayTimetable(); break;
            case "GandhinagarRajkotsunday" : getAhmedabadRajkotsundayTimetable(); break;
            case "GandhinagarVadodarasunday" : getAhmedabadvadodarasundayTimetable(); break;
            case "GandhinagarVadodaraNosunday" : getAhmedabadvadodaraNosundayTimetable(); break;
            case "GandhinagarAnandsunday" : getgandhinagarAnandsundayTimetable(); break;
            case "GandhinagarAnandNosunday" : getgandhinagarAnandNosundayTimetable(); break;
            case "GandhinagarAhmedabadsunday" : getgandhinagarAnandsundayTimetable(); break;
            case "GandhinagarAhmedabadNosunday" : getgandhinagarAnandNosundayTimetable(); break;
            case "GandhinagarGandhinagarsunday" : NoBus(); break;
            case "GandhinagarGandhinagarNosunday" : NoBus(); break;


            case "VadodaraNadiadNosunday" : getAhmedabadNadiadNosundayTimetable(); break;
            case "VadodaraNadiadsunday" : getAhmedabadNadiadsundayTimetable(); break;
            case "VadodaraRajkotNosunday" : getAhmedabadRajkotNosundayTimetable(); break;
            case "VadodaraRajkotsunday" : getAhmedabadRajkotsundayTimetable(); break;
            case "VadodaraAnandsunday" : getgandhinagarAnandsundayTimetable(); break;
            case "VadodaraAnandNosunday" : getgandhinagarAnandNosundayTimetable(); break;
            case "VadodaraAhmedabadsunday" : getgandhinagarAnandsundayTimetable(); break;
            case "VadodaraAhmedabadNosunday" : getgandhinagarAnandNosundayTimetable(); break;
            case "VadodaraGandhinagarsunday" : getgandhinagarAnandsundayTimetable(); break;
            case "VadodaraGandhinagarNosunday" : getgandhinagarAnandNosundayTimetable(); break;
            case "VadodaraVadodarasunday" : NoBus(); break;
            case "VadodaraNosunday" : NoBus(); break;

            default:
                throw new IllegalStateException("Unexpected value: " + finaldata);
        }

    }

    private void NoBus() {title = getResources().getStringArray(R.array.NoBus);


        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAhmedabadvadodaraNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadvadodarasundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Nadiad_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getgandhinagarAnandNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getgandhinagarAnandsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadgandhinagarNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadgandhinagarsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAnandAhmedabadNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Rajkot_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAnandAhmedabadsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Rajkot_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadAnandNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Nadiad_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadAnandsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Anand_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadRajkotsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Rajkot_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadRajkotNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Rajkot_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadNadiadsundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Nadiad_Sunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAhmedabadNadiadNosundayTimetable() {
        title = getResources().getStringArray(R.array.timtable_Ahmedabad2Nadiad_NoSunday);

        MainAdapter adapter = new MainAdapter(TimeTableActivity2.this,title);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , "" + title[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,TimeTableActivity.class));
        finish();
    }
}