package com.example.tamz_zodiac_2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//druhotne TODO: po stisku obrazku toast action_up a down, alfa kanal, a spusti se webovka horoskopy.cz - vytvoříme url adresu a hodíme ji do intentu - spustime aktivitu už existující androiďáckou kam dáme jen tu adresu
//změna pozadí druhé aktivity - při kliku na settings zvolení obrázku - startActivityForResult - pošleme zprávu o změně pozadí
// shared preferenes: pro uložení settings,


// Add new Activity (that starts after clicking the zodiac symbols ) with TextView that describes zodiac properties + zodiac date + zodiac name
// Add new Activity that is activated from menu item "About"
// Add corect zodiac symbols based on the day
// Start new activity with opening URL (e.g. https://www.horoskopy.cz/) in web browser with selected zodiac
// Change background of main activity using startActivityForResult and onActivityResult
// Save actual state of aplication using SharedPreferences (Date, Month, Zodiac symbols, Name)
// Test setOnTouchListener with MotionEvent.ACTION_DOWN and MotionEvent.ACTION_UP on zodiac image


public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{
/////////////////// Variables ///////////////////
    DatePicker myDate;
    TextView myText;
    TextView zodiacName;
    ImageView myImage;
    int globalYear = 2000;
    int globalMonth = 0;
    int globalDay = 1;
    ImageView globalBackground;
    SharedPreferences.Editor editor;
    int[] zodiacDays = new int[] {20,20,20,20,21,21,22,22,22,23,22,21};
    static final int[] zodiacPics = {
            R.drawable.kozoroh01,
            R.drawable.vodnar02,
            R.drawable.ryby03,
            R.drawable.beran04,
            R.drawable.byk05,
            R.drawable.blizenci06,
            R.drawable.rak07,
            R.drawable.lev08,
            R.drawable.panna09,
            R.drawable.vahy10,
            R.drawable.stir11,
            R.drawable.strelec12
    };

    String[] zodiacNames = {
            "Kozoroh",
            "Vodnář",
            "Ryby",
            "Beran",
            "Býk",
            "Blíženci",
            "Rak",
            "Lev",
            "Panna",
            "Váhy",
            "Štír",
            "Střelec"
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDate = findViewById(R.id.myDatePicker);
        myDate.init(globalYear, globalMonth, globalDay, this);

        myText = findViewById(R.id.textView);
        myText.setText("Fill your birthdate");

        myImage = findViewById(R.id.imageView);

        myImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) { //parametr Event pomocí něhož můžeme odchytávat co se stalo

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                // Toast.makeText(getApplicationContext(), "Action down", Toast.LENGTH_LONG).show(); //getAppCont získáme ID aplikace bo tato fce je anonymní
                myText.setText("action down");
                myImage.setColorFilter(Color.argb(150,50,255,50));
            }

            if(event.getAction() == MotionEvent.ACTION_UP) {
                myText.setText("action up");
                myImage.clearColorFilter();

                //Toast.makeText(getApplicationContext(), "hmmmmm", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getApplicationContext(), Main2Activity.class);
                myIntent.putExtra("monthOfYear", globalMonth);
                myIntent.putExtra("dayOfMonth", globalDay);
                myIntent.putExtra("year", globalDay);
                myIntent.putExtra("zodiacName", zodiacName.getText());

                startActivityForResult(myIntent, 202);
            }

            return true; //aby poslouchal dál a neskončil na prvním ifu
            }
        });

        zodiacName = findViewById(R.id.zodiacName);
    }

/////////////////// onActivityResult ///////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //Odchytávač pomocí toho request kódu

        super.onActivityResult(requestCode, resultCode, data); //do proměnné 'data' přijdou ta data a můžeme je číst

        try {
            String msg = "null";

            if (requestCode == 202) {
                msg = data.getStringExtra("main2");

            } else if (requestCode == 203) {
                 msg = data.getStringExtra("main3");
            } else if (requestCode == 204) {
                msg = data.getStringExtra("main4");
            }

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        } catch(Exception e) {
            zodiacName.setText("");
        }
    }
/////////////////// Options menu ///////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); //Create menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //On clicked menu item

        int id=item.getItemId();

        if (id == R.id.item0) { //About
            Intent myIntent = new Intent(getApplicationContext(), Main3Activity.class);
            startActivity(myIntent);
        }

        if (id == R.id.item1) { //Settings
            Intent myIntent = new Intent(getApplicationContext(), Main4Activity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    /////////////////// onDateChanged ///////////////////
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        globalMonth = monthOfYear;
        globalDay = dayOfMonth;
        globalYear = year;

        if (dayOfMonth <= zodiacDays[monthOfYear]){

            zodiacName.setText(zodiacNames[monthOfYear]);
            myImage.setImageResource(zodiacPics[monthOfYear]);

        } else {

            if (monthOfYear == 11) monthOfYear = -1; //11 = prosinec, 0 = leden
            myImage.setImageResource(zodiacPics[++monthOfYear]);
            zodiacName.setText(zodiacNames[monthOfYear]);

        }

        //Add to shared preferences
        editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();

        editor.putString("name", zodiacNames[monthOfYear]);
        editor.putInt("pic", zodiacPics[monthOfYear]);
        editor.putInt("day", dayOfMonth);
        editor.putInt("month", monthOfYear);
        editor.putInt("year", year);
        editor.apply();
    }


}
