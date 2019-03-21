package com.github.mikephil.charting.components;

import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLegend extends Legend {


    public void calculateDimensions(Paint labelpaint, ViewPortHandler viewPortHandler) {
      super.calculateDimensions(labelpaint, viewPortHandler);
        //Additional space needed because we have added X value title in our customization

        mNeededHeight +=   Utils.getLineHeight(labelpaint);
    }
}
