package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreen extends AppCompatActivity {
    private static int  Splash_Screen=5000;
    Animation topAnim,bottomAnim;
    ImageView imageView16,imageView17;
    TextView textView4;
    Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView16=findViewById(R.id.imageView16);
        imageView17=findViewById(R.id.imageView17);
        textView4=findViewById(R.id.textView4);

        imageView16.setAnimation(topAnim);
        imageView17.setAnimation(bottomAnim);
        textView4.setAnimation(bottomAnim);

        handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,Homepage_new.class);
                startActivity(intent);
                finish();
            }
        },Splash_Screen);








    }
}