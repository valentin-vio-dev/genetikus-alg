package com.vio.genalg.genalg;

import com.vio.genalg.Point;
import com.vio.genalg.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TSPChromosome extends Chromosome {

    private Random random = new Random();
    private ArrayList<Point> data;

    public TSPChromosome(ArrayList<Point> data) {
        super(data.size(), true);
        this.data = data;
    }

    @Override
    public void crossover() {
        List<Chromosome> firstTwo = Chromosome.getBestN(2, true);
        TSPChromosome parent1 = (TSPChromosome) firstTwo.get(0);
        TSPChromosome parent2 = (TSPChromosome) firstTwo.get(1);

        int rIS = random.nextInt(parent1.genes.size());
        int rIE = random.nextInt(parent1.genes.size());

        if (rIE < rIS) {
            int tmp = rIE;
            rIE = rIS;
            rIS = tmp;
        }

        ArrayList<List<Integer>> parent1GeneParts = getParts(parent1, rIS, rIE);
        Collections.shuffle(parent1GeneParts);

        this.genes = mixGenes(parent1GeneParts.get(0), parent1GeneParts.get(1), parent1GeneParts.get(2));
        calculateFitness();
    }

    @Override
    public void mutate() {
        if (random.nextInt(100) > 50) {
            Collections.shuffle(genes);
            calculateFitness();
        }
    }

    @Override
    public void calculateFitness() {
        double sum = 0;
        for (int i = 0; i < this.genes.size() - 1; i++) {
            sum += distance(data.get(this.genes.get(i)), data.get(this.genes.get(i + 1)));
        }

        sum += distance(data.get(this.genes.get(this.length - 1)), data.get(this.genes.get(0)));
        this.fitness = sum + (hasIntersectedSegment(getSegments(data, this.genes)) ? 10000 : 0);

    }

    public static double distance(Point p1, Point p2) {
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

    public static boolean hasIntersectedSegment(ArrayList<Segment> segments) {
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
}
