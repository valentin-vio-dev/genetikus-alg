package com.vio.genalg.genalg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Chromosome {

    public static ArrayList<Chromosome> CHROMOSOMES = new ArrayList<>();

    protected ArrayList<Integer> genes;
    protected double fitness;
    protected int length;
    protected int id;

    private Random random = new Random();

    public Chromosome() {
    }

    public Chromosome(int length, boolean increment) {
        this.length = length;
        this.genes = new ArrayList<>();
        this.fitness = Integer.MAX_VALUE;
        this.id = Chromosome.CHROMOSOMES.size();

        for (int i = 0; i < length; i++) {
            if (increment) {
                genes.add(i);
            } else {
                if (random.nextInt(100) > 50) {
                    genes.add(0);
                } else {
                    genes.add(1);
                }
            }
        }

        Chromosome.CHROMOSOMES.add(this);
    }

    public abstract void crossover();

    public abstract void mutate();

    public abstract void calculateFitness();

    public static List<Chromosome> getBestN(int n, boolean largerFirst) {
        ArrayList<Chromosome> cs = new ArrayList<>(Chromosome.CHROMOSOMES);
        if (largerFirst) {
            Collections.sort(cs, (o1, o2) -> ((int) o1.fitness) - ((int) o2.fitness));
        } else {
            Collections.sort(cs, (o1, o2) -> ((int) o2.fitness) - ((int) o1.fitness));
        }

        return cs.subList(0, n);
    }

    public ArrayList<List<Integer>> getParts(Chromosome chromosome, int rIS, int rIE) {
        List<Integer> first = new ArrayList<>(chromosome.genes.subList(0, rIS));
        List<Integer> mid = new ArrayList<>(chromosome.genes.subList(rIS, rIE));
        List<Integer> last = new ArrayList<>(chromosome.genes.subList(rIE, chromosome.genes.size()));

        /*if (random.nextInt(100) > 95) {
            Collections.shuffle(mid);
        }*/

        ArrayList<List<Integer>> result = new ArrayList<>();
        result.add(first);
        result.add(mid);
        result.add(last);

        return result;
    }

    public ArrayList<Integer> mixGenes(List<Integer> first, List<Integer> mid, List<Integer> end) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < first.size(); i++) {
            result.add(first.get(i));
        }

        for (int i = 0; i < mid.size(); i++) {
            result.add(mid.get(i));
        }

        for (int i = 0; i < end.size(); i++) {
            result.add(end.get(i));
        }

        return result;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + Arrays.toString(genes.toArray()) +
                ", fitness=" + fitness +
                ", id=" + id +
                '}';
    }
}
