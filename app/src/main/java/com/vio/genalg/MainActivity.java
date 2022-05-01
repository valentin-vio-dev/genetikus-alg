package com.vio.genalg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        setTitle("Optimization problems");
    }

    public void onTSPSettings(View view) {
        Intent intent = new Intent(this, TSPSettingsActivity.class);
        startActivity(intent);
    }

    public void onGMSettings(View view) {
        Intent intent = new Intent(this, GMSettingsActivity.class);
        startActivity(intent);
    }

    public void onKPettings(View view) {
        Intent intent = new Intent(this, KPSettingsActivity.class);
        startActivity(intent);
    }
}