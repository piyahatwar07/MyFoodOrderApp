package com.example.myfoodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation fade_animation;
   ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fade_animation = AnimationUtils.loadAnimation(this, R.anim.fade_animation);
        img=findViewById(R.id.logo);
       img.setAnimation(fade_animation);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3500);
//                  sleep(100);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    // showNetDisabledAlertToUser(SplashActivity.this);
                    Intent openMain = new Intent(SplashActivity.this, RegistrationActivity.class);
                    startActivity(openMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}
