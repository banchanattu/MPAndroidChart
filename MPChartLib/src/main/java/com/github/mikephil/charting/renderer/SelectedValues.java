package com.github.mikephil.charting.renderer;

public class SelectedValues {
    private String xVal;
    private String yVal;
    public SelectedValues(String x, String y) {
        xVal = x;
        yVal = y;
    }

    public String getXVal() {
        return xVal;
    }

    public String getYVal() {
        return yVal;
    }
}