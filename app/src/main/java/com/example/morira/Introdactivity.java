package com.example.morira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class Introdactivity extends AppCompatActivity {
    ImageView logo, appname, splashImg;
    LottieAnimationView LottieAnimationView;
    ImageView imageView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introdactivity);
        logo = findViewById(R.id.logopink);
        splashImg = findViewById(R.id.img);
        appname = findViewById(R.id.fontcuda);
        LottieAnimationView = findViewById(R.id.lottie);
        editText = findViewById(R.id.textView2);
        imageView = findViewById(R.id.buttonn);

        imageView.setOnClickListener(v -> {
            String name = editText.getText().toString();
            Intent secondpage = new Intent(Introdactivity.this, SecondPage.class);
            secondpage.putExtra("name:", name);
            startActivity(secondpage);
        });

        splashImg.animate().translationY(-4000).setDuration(1000).setStartDelay(1900);
        logo.animate().translationY(3000).setDuration(1000).setStartDelay(1900);
        appname.animate().translationY(3000).setDuration(1000).setStartDelay(1900);
        LottieAnimationView.animate().translationY(3000).setDuration(1000).setStartDelay(1900);

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