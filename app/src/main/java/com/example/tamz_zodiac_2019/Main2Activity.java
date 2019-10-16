package com.example.tamz_zodiac_2019;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

public class Main2Activity extends AppCompatActivity {

    TextView myText;
    TextView myDate;
    TextView myName;
    TextView myDesc;

    String textName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myText = findViewById(R.id.textView);
        myDate = findViewById(R.id.textDate);
        myName = findViewById(R.id.textZodiac);
        myDesc = findViewById(R.id.textDesc);

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        int pic = prefs.getInt("pic", -1); //0 is the default value.
        int day = prefs.getInt("day", -1); //0 is the default value.
        int month = prefs.getInt("month", -1); //0 is the default value.
        int year = prefs.getInt("year", -1); //0 is the default value.



        //


      /*  Intent intent = getIntent();
        Integer month = intent.getIntExtra("monthOfYear", -1);
        Integer day = intent.getIntExtra("dayOfMonth", -1);
        Integer year = intent.getIntExtra("year",-1);
        String name = intent.getStringExtra("zodiacName");
        Toast.makeText(this, "monthOfYear: " + month + "dayOfMonth: "+ day, Toast.LENGTH_SHORT).show();*/
        myDate.setText(day+". "+(month+1)+". "+year);
        myName.setText(name);
    }

    public void resultClick(View view) { //na tlačítku "back"

       // Toast.makeText(this, "resultClickkkkkkkkkkkkk", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class); //vytvoří se záměr
        myIntent.putExtra("main2","Zprava z lMain2Activity"); //Do záměru se hodí data ve formátu ("key","value")
        setResult(100, myIntent);
        finish(); //ukončuje druhou aktivitu

    }

    public String flattenToAscii(String s) {
        if(s == null || s.trim().length() == 0)
            return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public void viewUrl(View view) {
        Uri uri = Uri.parse("http://www.horoskopy.cz/"+flattenToAscii(textName).toLowerCase());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
