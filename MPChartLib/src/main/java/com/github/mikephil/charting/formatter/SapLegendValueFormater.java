package com.github.mikephil.charting.formatter;

import android.graphics.Color;

public interface SapLegendValueFormater {
    public class FormatColor {
        public int color;
        public FormatColor(int c) {
            color = c;
        }
    }
    public String formatXValue(float f);
    public String formatYValue(float f);
    public String formatYValueWithColor(float f, FormatColor color);
}
