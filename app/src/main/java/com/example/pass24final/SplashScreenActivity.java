package com.example.pass24final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView appname,tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        appname = findViewById(R.id.appname);
        tagline = findViewById(R.id.tagline);

        image.setAnimation(topAnim);
        appname.setAnimation(bottomAnim);
        tagline.setAnimation(bottomAnim);


        new Handler().postDelayed((Runnable) () -> {
            Intent intent = new Intent(this,LoginActivity.class);
            finish();
        },SPLASH_SCREEN);
    }
}