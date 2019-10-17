package com.example.tamz_zodiac_2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{
/////////////////// Variables ///////////////////
    DatePicker zodiacDatePicker;
    TextView zodiacNameView;
    ImageView zodiacImageView;
    SharedPreferences.Editor editor;
    int globalYear = 2000;
    int globalMonth = 0;
    int globalDay = 1;

    static final int[] zodiacDays = new int[] {20,20,20,20,21,21,22,22,22,23,22,21};
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
    static final String[] zodiacNames = {
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

        zodiacNameView = findViewById(R.id.zodiacName);
        zodiacImageView = findViewById(R.id.imageView);
        zodiacDatePicker = findViewById(R.id.myDatePicker);

        //get prefs or default 1.1.2000
        SharedPreferences prefs = getSharedPreferences("zodiacPrefs", MODE_PRIVATE);
        String textName = prefs.getString("name", "Kozoroh");//"No name defined" is the default value.
        int pic = prefs.getInt("pic", R.drawable.kozoroh01);
        int day = prefs.getInt("day", 1);
        int month = prefs.getInt("month", 0);
        int year = prefs.getInt("year", 2000);

        zodiacDatePicker.init(year, month, day, this);
        zodiacImageView.setImageResource(pic);
        zodiacNameView.setText(textName);

        zodiacImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) { //parametr Event pomocí něhož můžeme odchytávat co se stalo

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(getApplicationContext(), "Action down", Toast.LENGTH_SHORT).show(); //getAppCont získáme ID aplikace bo tato fce je anonymní
                zodiacImageView.setColorFilter(Color.argb(150,50,255,50));
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                Toast.makeText(getApplicationContext(), "Action up", Toast.LENGTH_SHORT).show();
                zodiacImageView.clearColorFilter();

                Intent myIntent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivityForResult(myIntent, 202);
            }
            return true; //aby poslouchal dál a neskončil na prvním ifu
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //Odchytávač pomocí toho request kódu
        super.onActivityResult(requestCode, resultCode, data); //do proměnné 'data' přijdou ta data a můžeme je číst
        try {
            //String msg = "wrong request code";

            if (requestCode == 202) {
                //msg = data.getStringExtra("main2");

            } else if (requestCode == 420) {
                //msg = Integer.toString((data.getIntExtra("wall", 0))).concat("... back from act4");
                int wallNum = (data.getIntExtra("wall", zodiacPics[0]));
                Drawable wall = ResourcesCompat.getDrawable(getResources(), wallNum, null);
                View mainBackground = findViewById(R.id.background);

                mainBackground.setBackground(wall);
            }

            //Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),"exception try activity result", Toast.LENGTH_LONG).show();
        }
    }

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
            startActivityForResult(myIntent, 420);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        globalMonth = monthOfYear;
        globalDay = dayOfMonth;
        globalYear = year;

        if (dayOfMonth <= zodiacDays[monthOfYear]){

            zodiacNameView.setText(zodiacNames[monthOfYear]);
            zodiacImageView.setImageResource(zodiacPics[monthOfYear]);

        } else {

            if (monthOfYear == 11) monthOfYear = -1; //11 = prosinec, 0 = leden
            zodiacImageView.setImageResource(zodiacPics[++monthOfYear]);
            zodiacNameView.setText(zodiacNames[monthOfYear]);

        }

        editor = getSharedPreferences("zodiacPrefs", MODE_PRIVATE).edit();

        editor.putString("name", zodiacNames[monthOfYear]);
        editor.putInt("pic", zodiacPics[monthOfYear]);
        editor.putInt("day", dayOfMonth);
        editor.putInt("month", monthOfYear);
        editor.putInt("year", year);

        editor.apply();
    }

}
