package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.Utils;
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

        if (this.chartTitle != null) {
            bounds = new Rect();
            this.mLegendLabelPaint.getTextBounds(label, 0, label.length(), bounds);
            float labelLineSpacing = Utils.getLineSpacing(mLegendLabelPaint, legendFontMetrics)
                    + Utils.convertDpToPixel(mLegend.getYEntrySpace());
            //this.drawTextOnHeader(c, x - this.mLegend.mNeededWidth / 2.0F, y + Utils.convertDpToPixel(this.mLegend.mNeededHeight), this.chartTitle, this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
            float textHeight = Utils.convertDpToPixel(bounds.bottom - bounds.top + labelLineSpacing);
            this.drawTextOnHeader(c, x - this.mLegend.mNeededWidth / 2.0F, y + textHeight, this.chartTitle, this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
            RectF size = this.mViewPortHandler.getContentRect();
            //float labelX = Utils.convertDpToPixel( x  - ( mViewPortHandler.offsetRight() + mViewPortHandler.offsetLeft())/2f);
            //float labelY = Utils.convertDpToPixel(y - (mViewPortHandler.offsetBottom()));
            //this.drawTextOnHeader(c, labelX, labelY, "Days", this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
            //this.drawTextOnHeader(c, (size.left + size.right) / 2.0F, size.bottom, "Days", this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
            //this.drawTextOnHeader(c, (size.left + size.right) / 2.0f, size.bottom + this.mLegend.mNeededHeight/2f, "Days", this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
//            float tx = (this.mViewPortHandler.offsetRight() + this.mViewPortHandler.offsetLeft())/2f;
//            float ty =  this.mViewPortHandler.offsetBottom();
//            this.drawTextOnHeader(c, tx, ty - textHeight , "Days", this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
            this.drawTextOnHeader(c, (size.left + size.right) / 2.0f, size.bottom + textHeight  , "Days", this.mLegendLabelPaint.getTextSize() * 1.5F, this.mLegendLabelPaint.getColor());
        }

    }
}
