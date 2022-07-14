package com.example.pass24final;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class homeFragment extends Fragment {

    String username_home,passGranted,usernamefb,expiry,final_expiry,renewHome;
    TextView title;
    Boolean diff;
    int datediff;
    FirebaseDatabase namefirebaseDatabase, RenewPassfb,pass_form2fb;
    DatabaseReference namedatabaseReference,RenewPassreference,pass_form2reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        title = view.findViewById(R.id.title_home_fragment);
        Bundle data = this.getArguments();
        username_home = data.getString("username_homeActivity_to_homeFragment");

        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");

        RenewPassfb= FirebaseDatabase.getInstance();
        RenewPassreference = RenewPassfb.getReference("RenewPass");

        pass_form2fb = FirebaseDatabase.getInstance();
        pass_form2reference = pass_form2fb.getReference("UserPassDetails");


        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);

        pass_form2reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expiry = snapshot.child(username_home).child("expiryDate").getValue(String.class);
                if(expiry != null) {
                    String edate = expiry.replaceFirst(" ","-");
                    int y = edate.indexOf("-");
                    int x = edate.indexOf(" ");
                    final_expiry = edate.substring(y+1,x);
                    datediff = (day - Integer.parseInt(final_expiry));
                    diff = datediff < 5 && datediff > 0;
                }else{
                    diff = false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        namedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefb =  snapshot.child(username_home).child("name").getValue(String.class);
                passGranted = snapshot.child(username_home).child("pass").getValue(String.class);
                title.setText("Welcome " + namefb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RenewPassreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                renewHome =  snapshot.child(username_home).child("renewHome").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //title.setText(data.getString("username_homeActivity_to_homeFragment"));


        CardView applyPass = view.findViewById(R.id.card_apply_pass);
        CardView renewPass = view.findViewById(R.id.card_renew_pass);
        CardView timeTable = view.findViewById(R.id.timetable_home);
        CardView downloadPdf = view.findViewById(R.id.download_pdf_home);


        applyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passGranted.equals("yes")){
                    Toast.makeText(getActivity(), username_home +": Go to View Pass", Toast.LENGTH_SHORT).show();
//                    openPass();
                }else if(passGranted.equals("Requested")){
                    Toast.makeText(getActivity(), username_home +": we have your request", Toast.LENGTH_SHORT).show();
                } else {
//                    CheckPass1(address, age, catagory, education, occupation, gender);
                    openPassForm1();
                }
            }
        });
        renewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passGranted.equals("yes")){
                    if(renewHome.equals("No")){
                        Toast.makeText(getContext(), "You don't have to renew right now ", Toast.LENGTH_SHORT).show();
                    }else {
//                        Toast.makeText(getContext(), "Open Renew" + renewHome, Toast.LENGTH_SHORT).show();
                        openRenew();
                    }
                }
                else if(passGranted.equals("Requested")){
                        Toast.makeText(getActivity(), username_home +": we have your request", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Looks like you have not applied for the pass", Toast.LENGTH_SHORT).show();
                    openPassForm1();
                }
            }
        });
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                   Toast.makeText(getActivity(), "" + data.getString("username_homeActivity_to_homeFragment"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),TimeTableActivity.class);
                intent.putExtra("username_home_to_timetable",username_home);
                startActivity(intent);
            }
        });
        downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passGranted.equals("yes")) {
                    openPass();
                }else if(passGranted.equals("Requested")){
                    Toast.makeText(getActivity(), username_home +": we have your request", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Looks like you have not applied for the pass", Toast.LENGTH_SHORT).show();
                    openPassForm1();
                }
            }
        });
        return view;
    }

    private void openPass() {
        Intent intent = new Intent(getContext(),download_pdf.class);
        intent.putExtra("username_home_to_downloadpdf",username_home);
        startActivity(intent);
    }

    private void openRenew() {
        Intent intent = new Intent(getContext(),renewpassActivity.class);
        intent.putExtra("username_home_to_passform1",username_home);
        startActivity(intent);
    }

    private void openPassForm1() {
        Intent intent = new Intent(getContext(),pass_form1.class);
//        Toast.makeText(getContext(),passGranted,Toast.LENGTH_SHORT).show();
        intent.putExtra("username_home_to_passform1",username_home);
        startActivity(intent);
    }
}