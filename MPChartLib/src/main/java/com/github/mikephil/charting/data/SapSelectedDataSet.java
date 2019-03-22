package com.github.mikephil.charting.data;

import java.text.DecimalFormat;

public class SapSelectedDataSet {
    float [] yValues = null;
    float xValue = 0f;
    public SapSelectedDataSet(float xVal, float[] yVals ) {
        this.xValue = xVal;
        this.yValues = yVals;
    }

    public  float getYvalueForDataSetIndex(int idx) {
        return yValues[idx];
    }
    public static String getDecimalFormattedData(float x) {
        DecimalFormat format = new DecimalFormat("#,###,###.##");
        return format.format(x);
    }
    public float getXValue() {
        return xValue;
    }
}
