package com.example.morira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

public class SecondPage extends AppCompatActivity {

    private SparkButton sparkButton;
    com.airbnb.lottie.LottieAnimationView LottieAnimationView1;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);
        sparkButton = findViewById(R.id.spark_button);
        textView = findViewById(R.id.nameofcustomer);
        String gelen = getIntent().getStringExtra("name:");
        textView.setText(gelen);
        sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                Intent thirdpage = new Intent(SecondPage.this, ThirdPage.class);
                startActivity(thirdpage);
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });


        LottieAnimationView1 = findViewById(R.id.lottie1);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

    }
}