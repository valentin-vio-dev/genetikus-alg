package com.vio.genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vio.genalg.genalg.Chromosome;
import com.vio.genalg.genalg.TSPChromosome;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TSPActivity extends AppCompatActivity {

    public static ArrayList<Point> points;

    private ArrayList<TSPChromosome> population;

    public static TSPExtras settings;

    private LinearLayout tspRun;
    private TSPCanvas canvas;

    private TextView textInfo;

    private static TSPChromosome best;

    private static int currentIter;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tspactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        population = new ArrayList<>();

        Chromosome.CHROMOSOMES.clear();

        currentIter = 0;

        tspRun = findViewById(R.id.tspRun);
        textInfo = findViewById(R.id.textInfo);

        settings = (TSPExtras) getIntent().getSerializableExtra("settings");
        points = settings.getPoints();

        canvas = new TSPCanvas(this.getBaseContext());
        canvas.points = points;
        canvas.setReadonly(true);
        canvas.setBackgroundColor(Color.rgb(230, 230, 230));
        tspRun.addView(canvas);

        for (int i = 0; i < settings.getPopSize(); i++) {
            population.add(new TSPChromosome(points));
            population.get(i).mutate();
            //System.out.println(population.get(i));
        }

        //System.out.println(".................");
        //System.out.println(Chromosome.getBestN(1).get(0));

        //System.out.println("******************************************");

        long startTime = System.currentTimeMillis();

        best = population.get(0);
        thread = new Thread(() -> {
            for (int i = 0; i < settings.getIterations(); i++) {
                currentIter = i;

                /*for (int j = 0; j < settings.getPopSize(); j++) {
                    System.out.println(population.get(j));
                }*/

                //System.out.println(".................");
                //System.out.println(Chromosome.getBestN(1).get(0));

                TSPChromosome currentbest = (TSPChromosome) Chromosome.getBestN(1, true).get(0);
                if (currentbest.getFitness() < best.getFitness()) {
                    best = currentbest;
                }

                canvas.setBest(best);

                for (int j = 0; j < settings.getPopSize(); j++) {
                    if (population.get(j).getId() == best.getId()) {
                        continue;
                    }
                    population.get(j).crossover();
                    population.get(j).mutate();
                }

                //System.out.println("-----------------------------------");

                long ellapsed = System.currentTimeMillis() - startTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                Date resultdate = new Date(ellapsed);
                runOnUiThread(() -> setInfo("Number of cities: " + settings.getNumOfCities() + " - O(" + settings.getNumOfCities() + "!)", "Population size: " + settings.getPopSize(), "Iterations: " + currentIter + " / " + settings.getIterations(), "Best fitness: " + (int) best.getFitness(), "Ellapsed time: " + (sdf.format(resultdate))));

                for (int j = 0; j < population.size(); j++) {
                    canvas.setChromosome(population.get(j));

                    canvas.invalidate();

                    /*try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }

            canvas.setChromosome(null);
            canvas.setEnd(true);
            canvas.invalidate();
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

    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    public static int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val == 0.0) return 0;
        return (val > 0) ? 1 : 2;
    }

    public static boolean onSegment(Point p, Point q, Point r) {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) && q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY())) {
            return true;
        }

        return false;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public static double dis(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public static ArrayList<Segment> getSegments(ArrayList<Point> points, ArrayList<Integer> order) {
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < order.size() - 1; i++) {
            Point p1 = points.get(order.get(i));
            Point p2 = points.get(order.get(i + 1));
            segments.add(new Segment(p1, p2));
        }

        return segments;
    }

    public static  boolean hasIntersectedSegment(ArrayList<Segment> segments) {
        for (int i = 0; i < segments.size(); i++) {
            for (int j = 0; j < segments.size(); j++) {
                if (segments.get(i).getStart().getX() != segments.get(j).getStart().getX() && segments.get(i).getStart().getY() != segments.get(j).getStart().getY() &&
                    segments.get(i).getEnd().getX() != segments.get(j).getEnd().getX() && segments.get(i).getEnd().getY() != segments.get(j).getEnd().getY() &&
                    doIntersect(segments.get(i).getStart(), segments.get(i).getEnd(), segments.get(j).getStart(), segments.get(j).getEnd())
                ) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setInfo(String... strings) {
        String res = "";

        for(String s: strings) {
            res += s + "\n";
        }

        textInfo.setText(res);
    }


}