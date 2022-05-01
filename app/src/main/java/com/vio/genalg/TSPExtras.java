package com.vio.genalg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class TSPExtras implements Serializable {
    private int numOfCities;
    private int popSize;
    private int iterations;
    private ArrayList<Point> points;

    public TSPExtras(int numOfCities, int popSize, int iterations, ArrayList<Point> points) {
        this.numOfCities = numOfCities;
        this.popSize = popSize;
        this.iterations = iterations;
        this.points = points;
    }

    public int getNumOfCities() {
        return numOfCities;
    }

    public void setNumOfCities(int numOfCities) {
        this.numOfCities = numOfCities;
    }

    public int getPopSize() {
        return popSize;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
}
