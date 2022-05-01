package com.vio.genalg;

import java.util.ArrayList;
import java.util.Random;

public class TSPDNA {
    private Random random = new Random();

    private Point[] cities;
    private int[] order;

    private double distance;
    private double fitness;




    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double calcDistance() {
        int sum = 0;
        for (int i = 0; i < this.order.length - 1; i++) {
            int cityAIndex = this.order[i];
            Point cityA = cities[cityAIndex];
            int cityBIndex = this.order[i + 1];
            Point cityB = cities[cityBIndex];
            double d = dist(cityA.getX(), cityA.getY(), cityB.getX(), cityB.getY());
            sum += d;
        }
        this.distance = sum;
        return this.distance;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void shuffle() {
        int i = random.nextInt(order.length);
        int j = random.nextInt(order.length);
        swap(order, i, j);
    }

    public double scaleBetween(double unscaledNum, int minAllowed, int maxAllowed, int min, int max) {
        return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
    }

    public double mapFitness(int minD, int maxD) {
        this.fitness = scaleBetween(this.distance, minD, maxD, 1, 0);
        return this.fitness;
    }

}
