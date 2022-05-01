package com.vio.genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.vio.genalg.genalg.Chromosome;
import com.vio.genalg.genalg.KPChromosome;
import com.vio.genalg.genalg.TSPChromosome;
import com.vio.genalg.settings.KnapsackSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class KPActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private KnapsackSettings settings;
    private ArrayList<KPChromosome> population;

    private static KPChromosome best;
    private static int currentIter;
    private Thread thread;

    private TextView textInfoKP;
    private TextView textItemsKP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpactivity);
        setTitle("Knapsack simulation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInfoKP = findViewById(R.id.textInfoKP);
        textItemsKP = findViewById(R.id.textItemsKP);

        settings = (KnapsackSettings) getIntent().getSerializableExtra("settings");
        System.out.println("---------------------------------------------------");
        System.out.println(settings);

        items = new ArrayList<>();

        for (int i = 0; i < settings.getNumOfItems(); i++) {
            int weight = Utils.getRandomNumber(settings.getItemsWeightInterval()[0], settings.getItemsWeightInterval()[1]);
            int price = Utils.getRandomNumber(settings.getItemsValueInterval()[0], settings.getItemsValueInterval()[1]);
            items.add(new Item(weight, price));
        }

        population = new ArrayList<>();
        Chromosome.CHROMOSOMES.clear();
        currentIter = 0;

        for (int i = 0; i < settings.getPopulationSize(); i++) {
            population.add(new KPChromosome(items, settings.getMaxWeight()));
            population.get(i).mutate();
            //System.out.println(population.get(i));
        }

        System.out.println("...............");
        System.out.println(settings.getNumOfIters());
        System.out.println("...............");

        long startTime = System.currentTimeMillis();
        best = population.get(0);

        thread = new Thread(() -> {
            for (int i = 0; i < settings.getNumOfIters(); i++) {
                currentIter = i;

                for (int j = 0; j < settings.getPopulationSize(); j++) {
                    System.out.println(population.get(j));
                }

                //System.out.println(".................");
                //System.out.println(Chromosome.getBestN(1).get(0));

                KPChromosome currentbest = (KPChromosome) Chromosome.getBestN(1, false).get(0);
                if (currentbest.getFitness() > best.getFitness()) {
                    best = currentbest;
                }

                for (int j = 0; j < settings.getPopulationSize(); j++) {
                    if (population.get(j).getId() == best.getId()) {
                        continue;
                    }
                    population.get(j).crossover();
                    population.get(j).mutate();
                }

                System.out.println("-----------------------------------");

                long ellapsed = System.currentTimeMillis() - startTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                Date resultdate = new Date(ellapsed);
                runOnUiThread(() -> {
                    setInfo("Max weight: " + settings.getMaxWeight(), "Population size: " + settings.getPopulationSize(), "Iterations: " + currentIter + " / " + settings.getNumOfIters(), "Best:\n\t\t" + KPChromosome.getStats(best)[0] + " price\n\t\t" + KPChromosome.getStats(best)[1] + " weight", "Ellapsed time: " + (sdf.format(resultdate)));
                    setItemsText();
                });
            }
        });

        thread.start();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            thread.interrupt();
            finish();
            return true;
        }
        return false;
    }

    public void setInfo(String... strings) {
        String res = "";

        for(String s: strings) {
            res += s + "\n";
        }

        textInfoKP.setText(res);
    }

    public void setItemsText() {
        String text = "";
        for (int i = 0; i < items.size(); i++) {
            text += "Price: " + items.get(i).getPrice() + "\t\t\tWeight: " + items.get(i).getWeight() + "\t\t\t\t\t" + (best.getGenes().get(i) == 1 ? "OK" : "-") + "\n";
        }
        textItemsKP.setText(text);
    }
}