package com.example.tamz_zodiac_2019;
//Settings
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Main4Activity extends AppCompatActivity {
    RelativeLayout rl;
    int pic;
    ImageView pic1, pic2;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        pic1 = findViewById(R.id.background1);
        pic2 = findViewById(R.id.background2);

        pic1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pic = R.drawable.wall01;
                findViewById(android.R.id.background).setBackgroundResource(R.drawable.wall01);

            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pic = R.drawable.wall02;
                findViewById(android.R.id.background).setBackgroundResource(R.drawable.wall02);

            }
        });

        editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();

        editor.putInt("background", pic);
        editor.commit();


    }


}
