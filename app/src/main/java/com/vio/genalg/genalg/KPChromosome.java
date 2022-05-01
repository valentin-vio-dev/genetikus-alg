package com.vio.genalg.genalg;

import com.vio.genalg.Item;
import com.vio.genalg.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KPChromosome extends Chromosome {

    private Random random = new Random();
    private ArrayList<Item> data;
    private int maxWeight;

    public KPChromosome(ArrayList<Item> data, int maxWeight) {
        super(data.size(), false);
        this.data = data;
        this.maxWeight = maxWeight;
    }


    @Override
    public void crossover() {
        List<Chromosome> firstTwo = Chromosome.getBestN(2, false);
        KPChromosome parent1 = (KPChromosome) firstTwo.get(0);
        KPChromosome parent2 = (KPChromosome) firstTwo.get(1);

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
        }

        if (random.nextInt(100) > 50) {
            for (int i = 0; i < genes.size(); i++) {
                if (random.nextInt(100) > 50) {
                    genes.set(i, random.nextInt(100) > 50 ? 1 : 0);
                }
            }
        }

        calculateFitness();
    }

    @Override
    public void calculateFitness() {
        int sumPrice = 0;
        int sumWeight = 0;

        for (int i = 0; i < this.genes.size(); i++) {
            if (genes.get(i) == 1) {
                sumPrice += data.get(i).getPrice();
                sumWeight += data.get(i).getWeight();
            }
        }

        this.fitness = sumPrice + ((sumWeight > maxWeight) ? -10000 : 0);
    }

    public static int[] getStats(KPChromosome chromosome) {
        int sumPrice = 0;
        int sumWeight = 0;

        for (int i = 0; i < chromosome.genes.size(); i++) {
            if (chromosome.genes.get(i) == 1) {
                sumPrice += chromosome.data.get(i).getPrice();
                sumWeight += chromosome.data.get(i).getWeight();
            }
        }

        return new int[]{ sumPrice, sumWeight };
    }
}
