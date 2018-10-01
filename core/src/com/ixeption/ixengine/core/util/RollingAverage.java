package com.ixeption.ixengine.core.util;

/**
 * Created by Christian on 31.05.2015.
 */
public class RollingAverage {

    private int size;
    private float total = 0f;
    private int index = 0;
    private float samples[];

    public RollingAverage(int size) {
        this.size = size;
        samples = new float[size];
    }

    public void add(float x) {
        total -= samples[index];
        samples[index] = x;
        total += x;
        if (++index == size) index = 0; // cheaper than modulus
    }

    public float getAverage() {
        return total / index;
    }

    public void reset() {
        total = 0;
    }
}