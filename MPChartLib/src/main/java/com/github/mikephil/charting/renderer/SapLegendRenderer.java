package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLegendRenderer extends LegendRenderer {

    /**
     * Title for the chart
     */
    protected String chartTitle = null;


    private SelectedValues selectedValues = null;


    public SapLegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler, legend);
    }

    /**
     * Set the Chart Title
     */
    public void setChartTitle(String title) {
        this.chartTitle = title;
    }


    public void setSelectedValue(SelectedValues selVal) {
        selectedValues = selVal;
    }
    public void drawTextOnHeader(Canvas c, float x, float y, String labelText, float textSize, int textColor) {
        Paint p = new Paint();
        p.setTextAlign(Paint.Align.LEFT);
        p.setTextSize(textSize);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.setColor(textColor);
        c.drawText(labelText, x, y, p);
    }

    /**
     /**
     * Draws the provided label at the given position.
     *
     * @param c     canvas to draw with
     * @param x
     * @param y
     * @param label the label to draw
     */
    @Override
    protected void drawLabel(Canvas c, float x, float y, String label) {
        c.drawText(label, x, y, mLegendLabelPaint);
        if (selectedValues != null) {
            Rect bounds = new Rect();
            mLegendLabelPaint.getTextBounds(label, 0, label.length(), bounds);
            drawTextOnHeader(c, x + bounds.right * 1.2f, (y + bounds.top * 3f), selectedValues.getXVal(), mLegendLabelPaint.getTextSize(), mLegendLabelPaint.getColor());
            drawTextOnHeader(c, x + bounds.right * 1.2f, (y ), selectedValues.getYVal(), mLegendLabelPaint.getTextSize()*1.5f, mLegendLabelPaint.getColor());

        }
        if (chartTitle != null) {
            Rect bounds = new Rect();
            mLegendLabelPaint.getTextBounds(label, 0, label.length(), bounds);
            drawTextOnHeader(c, x - mLegend.mNeededWidth/2f, y + mLegend.mNeededHeight, chartTitle, mLegendLabelPaint.getTextSize()*1.5f, mLegendLabelPaint.getColor() );
        }
    }
}
