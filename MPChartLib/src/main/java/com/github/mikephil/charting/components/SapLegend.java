package com.github.mikephil.charting.components;

import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLegend extends Legend {


    public void calculateDimensions(Paint labelpaint, ViewPortHandler viewPortHandler) {
      super.calculateDimensions(labelpaint, viewPortHandler);
        /**
         * Original MPAndroid chart had issue where the dimension calculation did not use line spacing.
         * To fix that Add that here. This became an issue only after we are using in a more flexible way
         * Where we can add additional charts dynamically.
         */
        mNeededHeight += this.getEntries().length *   Utils.getLineSpacing(labelpaint, new Paint.FontMetrics());
        //Additional space needed because we have added Y value title in our customization
        mNeededHeight +=   2 *  Utils.getLineHeight(labelpaint);
    }
}
