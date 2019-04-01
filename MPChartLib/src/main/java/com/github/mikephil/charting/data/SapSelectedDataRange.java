package com.github.mikephil.charting.data;


public class SapSelectedDataRange {
    private float leftX;
    private float[] leftY;
    private float rightX;
    private float[] rightY;
    public SapSelectedDataRange(float x1, float[] y1Values, float x2, float[] y2Values) {

        if (x1 < x2) {
            leftX = x1;
            leftY = y1Values;
            rightX = x2;
            rightY = y2Values;
        } else {
            rightX = x1;
            rightY = y1Values;
            leftX = x2;
            leftY = y2Values;

        }

    }

    public float getDifferentFor(int idx) {
        return (rightY[idx] - leftY[idx]);
    }

    public float getPercentatgeDifference(int idx) {
        return (rightY[idx] - leftY[idx])*100.f/ leftY[idx];
    }
}
