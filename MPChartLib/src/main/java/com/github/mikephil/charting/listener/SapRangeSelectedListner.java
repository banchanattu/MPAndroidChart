package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.highlight.Highlight;

public interface SapRangeSelectedListner {
    void onRangeSelected(Highlight[] range);
    void onRangeUnselected();
}
