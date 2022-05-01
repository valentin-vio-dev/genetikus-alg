package com.vio.genalg.settings;

import java.io.Serializable;
import java.util.Arrays;

public class KnapsackSettings implements Serializable {
    private int numOfItems;
    private int maxWeight;
    private int[] itemsWeightInterval;
    private int[] itemsValueInterval;
    private int populationSize;
    private int numOfIters;

    public KnapsackSettings(int numOfItems, int maxWeight, int[] itemsWeightInterval, int[] itemsValueInterval, int populationSize, int numOfIters) {
        this.numOfItems = numOfItems;
        this.maxWeight = maxWeight;
        this.itemsWeightInterval = itemsWeightInterval;
        this.itemsValueInterval = itemsValueInterval;
        this.populationSize = populationSize;
        this.numOfIters = numOfIters;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int[] getItemsWeightInterval() {
        return itemsWeightInterval;
    }

    public void setItemsWeightInterval(int[] itemsWeightInterval) {
        this.itemsWeightInterval = itemsWeightInterval;
    }

    public int[] getItemsValueInterval() {
        return itemsValueInterval;
    }

    public void setItemsValueInterval(int[] itemsValueInterval) {
        this.itemsValueInterval = itemsValueInterval;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getNumOfIters() {
        return numOfIters;
    }

    public void setNumOfIters(int numOfIters) {
        this.numOfIters = numOfIters;
    }

    @Override
    public String toString() {
        return "KnapsackSettings{" +
                "numOfItems=" + numOfItems +
                ", maxWeight=" + maxWeight +
                ", itemsWeightInterval=" + Arrays.toString(itemsWeightInterval) +
                ", itemsValueInterval=" + Arrays.toString(itemsValueInterval) +
                ", populationSize=" + populationSize +
                ", numOfIters=" + numOfIters +
                '}';
    }
}
