package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLegendRenderer extends LegendRenderer {




    private SelectedValues selectedValues = null;


    public SapLegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler, legend);
    }




    public void setSelectedValue(SelectedValues selVal) {
        selectedValues = selVal;
    }


    public float getLegendHeaderTextHeight() {
        Rect bounds = new Rect();
        this.mLegendLabelPaint.getTextBounds("AyDEMO", 0, "AyDEMO".length(), bounds);
        float textHeight = Utils.convertDpToPixel(bounds.bottom - bounds.top);
        return textHeight;
    }

    public void drawTextOnHeader(Canvas c, float x, float y, String labelText, float textSize, int textColor) {
        Paint p = new Paint();
        p.setTextAlign(Paint.Align.LEFT);
        p.setTextSize(textSize);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.setColor(textColor);
        c.drawText(labelText, x, y, p);
    }


    public Paint.FontMetrics getLegendFortMetrics() {
        return legendFontMetrics;
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
    protected void drawLabel(Canvas c, float x, float y, String label) {
        c.drawText(label, x, y, this.mLegendLabelPaint);
        Rect bounds;
        if (this.selectedValues != null) {
            bounds = new Rect();
            this.mLegendLabelPaint.getTextBounds(label, 0, label.length(), bounds);
            float labelLineSpacing = Utils.getLineSpacing(mLegendLabelPaint, legendFontMetrics)
                    + Utils.convertDpToPixel(mLegend.getYEntrySpace());
            float textHeight = Utils.convertDpToPixel(bounds.bottom - bounds.top + labelLineSpacing);
            this.drawTextOnHeader(c, x + (float)bounds.right * 1.2F, y - textHeight, this.selectedValues.getXVal(), this.mLegendLabelPaint.getTextSize(), this.mLegendLabelPaint.getColor());
            this.drawTextOnHeader(c, x + (float)bounds.right * 1.2F, y, this.selectedValues.getYVal(), this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
        }

    //The following lines are used only for debugging purpose
    drawBigRect(c);
    drawChartBorderLine(c);
}

/** This code is only used for debugging purpose
 * @param c Canvas
 */
public void drawChartBorderLine(Canvas c) {
        RectF r = mViewPortHandler.getContentRect();
        int maxX = c.getWidth();
        int maxY = c.getHeight();
        Rect rect = new Rect(Math.round(r.left), Math.round(r.top), Math.round(r.right), Math.round(r.bottom));
        Paint  p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);
        c.drawRect(rect,p);

        }

/**
 * This code is only used for debugging purpose
 * @param c Canvas
 */
public void drawBigRect(Canvas c) {
        int maxX = c.getWidth();
        int maxY = c.getHeight();
        Rect r = new Rect(0,0,maxX, maxY);
        Paint  p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);
        c.drawRect(r,p);
        }


}
