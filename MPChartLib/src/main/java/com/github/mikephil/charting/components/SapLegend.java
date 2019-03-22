package com.github.mikephil.charting.components;

import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLegend extends Legend {


    public void calculateDimensions(Paint labelpaint, ViewPortHandler viewPortHandler) {
      super.calculateDimensions(labelpaint, viewPortHandler);
        //Additional space needed because we have added Y value title in our customization
       //  this.setYOffset(Utils.getLineHeight(labelpaint) * 2);
        mNeededHeight +=   2 *  Utils.getLineHeight(labelpaint);
    }
}
