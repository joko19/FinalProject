package com.jackshaw.proyekakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    private RadioGroup rgBahasa;
    private RadioButton indonesia, english;
    private Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        rgBahasa = findViewById(R.id.rgbahasa);
        indonesia = findViewById(R.id.btnIndonesia);
        english = findViewById(R.id.btnEnglish);
        simpan = findViewById(R.id.btnsimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = rgBahasa.getCheckedRadioButtonId();
                switch (selected) {
                    case R.id.btnIndonesia:
                        setLocale("in");
                        Intent inIntent = new Intent(SettingActivity.this, MainActivity.class);
                        startActivity(inIntent);
                        break;
                    case R.id.btnEnglish:
                        setLocale("en");
                        Intent enIntent = new Intent(SettingActivity.this, MainActivity.class);
                        startActivity(enIntent);
                        break;
                }
            }
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "in");
        setLocale(language);
    }
}