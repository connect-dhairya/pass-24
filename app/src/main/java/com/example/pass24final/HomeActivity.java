 package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String FILE_NAME = "myFile";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String total_string ,passGranted;
    CardView applyPass ,renewPass;
    FirebaseDatabase namefirebaseDatabase;
    DatabaseReference namedatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        total_string = preferences.getString("username","Data not found");

        namefirebaseDatabase = FirebaseDatabase.getInstance();
        namedatabaseReference = namefirebaseDatabase.getReference("users");

        namedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passGranted = snapshot.child(total_string).child("pass").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Intent intent = getIntent();
//        total_string = intent.getStringExtra("final_data");

        //hooks
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawablelayout);
        navigationView = findViewById(R.id.navigation_bar);
        applyPass = findViewById(R.id.card_apply_pass);
        renewPass = findViewById(R.id.card_renew_pass);

        //toolbar
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home_menu);

        if(savedInstanceState == null){
            openHomeFragement();
        }

    }

    private void openHomeFragement() {
        Bundle data = new Bundle();
        data.putString("username_homeActivity_to_homeFragment",total_string);
        Fragment fragment = new homeFragment();
        fragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_menu:
                openHomeFragement();
                break;
            case R.id.personal_details_menu:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new profileFragment()).commit();
                Intent profileintent = new Intent(HomeActivity.this,profile.class);
//                Toast.makeText(this, total_string, Toast.LENGTH_SHORT).show();
                profileintent.putExtra("total_string",total_string);
                startActivity(profileintent);
                break;
            case R.id.logout_menu:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                Intent logoutintent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logoutintent);
                break;
            case R.id.view_status:
//                Intent intent = new Intent(HomeActivity.this, tsetActivity.class);
//                startActivity(intent);
                if(passGranted.equals("Requested")){
                    Toast.makeText(this, "Requested", Toast.LENGTH_SHORT).show();
                }else if(passGranted.equals("yes")){
                    Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
                }
//                openHomeFragement();
                break;
            case R.id.privacy_policy_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new privacyPolicyFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}