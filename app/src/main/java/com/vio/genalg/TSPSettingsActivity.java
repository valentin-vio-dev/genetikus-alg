package com.vio.genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Random;

public class TSPSettingsActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private LinearLayout container;
    private LinearLayout tspRandom;
    private LinearLayout tspCustom;

    private LinearLayout canvasContainer;

    private EditText inputNumOfCities;
    private EditText inputNumOfPopsize;
    private EditText inputNumOfIters;

    private EditText inputNumOfPopsizeCustom;
    private EditText inputNumOfItersCustom;

    private TSPCanvas canvas;

    private String currentTab;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tspsettings);
        setTitle("TSP settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentTab = "RANDOM";

        container = findViewById(R.id.container);
        tspRandom = findViewById(R.id.tspRandom);
        tspCustom = findViewById(R.id.tspCustom);
        tabLayout = findViewById(R.id.tabLayout);
        inputNumOfCities = findViewById(R.id.numOfCities);
        inputNumOfIters = findViewById(R.id.numOfIters);
        inputNumOfPopsize = findViewById(R.id.numOfPopSize);
        inputNumOfItersCustom = findViewById(R.id.numOfItersCustom);
        inputNumOfPopsizeCustom = findViewById(R.id.numOfPopSizeCustom);

        canvasContainer = findViewById(R.id.canvasContainer);

        canvas = new TSPCanvas(this.getBaseContext());
        canvas.setBackgroundColor(Color.rgb(230, 230, 230));
        canvasContainer.addView(canvas);

        container.removeAllViews();
        container.addView(tspRandom);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getText());
                if (tab.getText().toString().toUpperCase().equals("RANDOM")) {
                    container.removeAllViews();
                    container.addView(tspRandom);
                    currentTab = "RANDOM";
                } else if (tab.getText().toString().toUpperCase().equals("CUSTOM")) {
                    container.removeAllViews();
                    container.addView(tspCustom);
                    currentTab = "CUSTOM";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public void onStartTSP(View view) {
        int numOfCities = Integer.parseInt(inputNumOfCities.getText().toString());
        int popsize = Integer.parseInt(inputNumOfPopsize.getText().toString());
        int iters = Integer.parseInt(inputNumOfIters.getText().toString());

        if (currentTab.equals("CUSTOM")) {
            numOfCities = canvas.points.size();
            popsize = Integer.parseInt(inputNumOfPopsizeCustom.getText().toString());
            iters = Integer.parseInt(inputNumOfItersCustom.getText().toString());
        }

        ArrayList<Point> points = new ArrayList<>();
        if (currentTab.equals("CUSTOM")) {
            points = canvas.points;
        } else if (currentTab.equals("RANDOM")) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            int rw = width - 200;

            for (int i = 0; i < numOfCities; i++) {
                points.add(new Point(random.nextFloat() * rw + 100, random.nextFloat() * 600 + 100));
            }
        }

        if (points.size() < 3) {
            return;
        }

        TSPExtras tspExtras = new TSPExtras(numOfCities, popsize, iters, points);

        Intent intent = new Intent(this, TSPActivity.class);
        intent.putExtra("settings", tspExtras);
        startActivity(intent);
    }

    public void clearCanvas(View view) {
        canvas.clearPoints();
    }
}