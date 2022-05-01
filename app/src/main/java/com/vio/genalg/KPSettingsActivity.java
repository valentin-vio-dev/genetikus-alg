package com.vio.genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vio.genalg.settings.KnapsackSettings;

public class KPSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpsettings);
        setTitle("Knapsack settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public void onStartKP(View view) {
        int numOfItems = Integer.parseInt(((EditText) findViewById(R.id.kpsInputNumOfItems)).getText().toString().trim());
        int maxWeight = Integer.parseInt(((EditText) findViewById(R.id.kpsInputMaxWeight)).getText().toString().trim());
        int itemsMinWeight = Integer.parseInt(((EditText) findViewById(R.id.kpsInputMinItemsWeight)).getText().toString().trim());
        int itemsMaxWeight = Integer.parseInt(((EditText) findViewById(R.id.kpsInputMaxItemsWeight)).getText().toString().trim());
        int itemsMinValue = Integer.parseInt(((EditText) findViewById(R.id.kpsInputMinItemsValue)).getText().toString().trim());
        int itemsMaxValue = Integer.parseInt(((EditText) findViewById(R.id.kpsInputMaxItemsValue)).getText().toString().trim());
        int populationSize = Integer.parseInt(((EditText) findViewById(R.id.kpsInputPopulationSize)).getText().toString().trim());
        int iters = Integer.parseInt(((EditText) findViewById(R.id.kpsInputNumOfIters)).getText().toString().trim());

        KnapsackSettings knapsackSettings = new KnapsackSettings(numOfItems, maxWeight, new int[] { itemsMinWeight, itemsMaxWeight }, new int[] { itemsMinValue, itemsMaxValue }, populationSize, iters);

        Intent intent = new Intent(this, KPActivity.class);
        intent.putExtra("settings", knapsackSettings);
        startActivity(intent);
    }
}