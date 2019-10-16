package com.example.tamz_zodiac_2019;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main4Activity extends AppCompatActivity {
    int pic;
    ImageView pic1, pic2;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    ImageView globalBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        pic1 = findViewById(R.id.background1);
        pic2 = findViewById(R.id.background2);
        editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();


        pic1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pic = R.drawable.wall01;

             /*    editor.putInt("background", pic);

                editor.commit();
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                */
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                myIntent.putExtra("wall",pic); //Do záměru se hodí data ve formátu ("key","value")
                setResult(101, myIntent); //stejný kod jako request kod
finish();
            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pic = R.drawable.wall02;


/*


                 editor.putInt("background", pic);

                editor.commit();
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                */
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                myIntent.putExtra("wall",pic); //Do záměru se hodí data ve formátu ("key","value")
                setResult(100, myIntent); //stejný kod jako request kod
                finish();

            }
        });

    }





}
