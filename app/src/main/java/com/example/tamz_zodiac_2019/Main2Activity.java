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

        SharedPreferences prefs = getSharedPreferences("zodiacPrefs", MODE_PRIVATE);
        textName = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        int day = prefs.getInt("day", -1);
        int month = prefs.getInt("month", -1);
        int year = prefs.getInt("year", -1);

        myDate.setText(day+". "+(month+1)+". "+year);
        myName.setText(textName);
    }

    public void resultClick(View view) { //na tlačítku "back"
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class); //vytvoří se záměr
        myIntent.putExtra("main2","Zprava z Main2Activity"); //Do záměru se hodí data ve formátu ("key","value")
        setResult(100, myIntent);
        finish();
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
