package com.example.covidsl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int myscreen=5000;

    Animation topSideani,bottomSideani;
    ImageView image;
    TextView text,text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        topSideani= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomSideani= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        image=findViewById(R.id.imageView);
        text=findViewById(R.id.textView);
        text1=findViewById(R.id.textView3);


        image.setAnimation(topSideani);
        text.setAnimation(bottomSideani);
        text1.setAnimation(bottomSideani);

        //to handle the delay process
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent second=new Intent(SplashScreen.this,AdminCitizens.class);
                startActivity(second);
                finish();
            }
        },myscreen);
    }

}

