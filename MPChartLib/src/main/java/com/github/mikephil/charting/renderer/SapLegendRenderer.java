package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

public class SapLegendRenderer extends LegendRenderer {




    private SelectedValues selectedValues = null;
    private static final int LABELTOVALUEGAP = 30;


    public SapLegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler, legend);
    }




    public void setSelectedValue(SelectedValues selVal) {
        selectedValues = selVal;
    }


    public float getLegendHeaderTextHeight() {
        Rect bounds = new Rect();
        this.mLegendLabelPaint.getTextBounds("AyDEMO", 0, "AyDEMO".length(), bounds);
        float textHeight = bounds.bottom - bounds.top;//Utils.convertDpToPixel(bounds.bottom - bounds.top);
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

    /**
     * Responsible rendering the legend
     * @param c
     */
    @Override
    public void renderLegend(Canvas c) {

        if (!mLegend.isEnabled())
            return;

        Typeface tf = mLegend.getTypeface();

        if (tf != null)
            mLegendLabelPaint.setTypeface(tf);

        mLegendLabelPaint.setTextSize(mLegend.getTextSize());
        mLegendLabelPaint.setColor(mLegend.getTextColor());

        float labelLineHeight = Utils.getLineHeight(mLegendLabelPaint, legendFontMetrics);
        float labelLineSpacing = Utils.getLineSpacing(mLegendLabelPaint, legendFontMetrics)
                + Utils.convertDpToPixel(mLegend.getYEntrySpace());
        float formYOffset = labelLineHeight - Utils.calcTextHeight(mLegendLabelPaint, "ABC") / 2.f;

        LegendEntry[] entries = mLegend.getEntries();

        float formToTextSpace = Utils.convertDpToPixel(mLegend.getFormToTextSpace());
        float xEntrySpace = Utils.convertDpToPixel(mLegend.getXEntrySpace());
        Legend.LegendOrientation orientation = mLegend.getOrientation();
        Legend.LegendHorizontalAlignment horizontalAlignment = mLegend.getHorizontalAlignment();
        Legend.LegendVerticalAlignment verticalAlignment = mLegend.getVerticalAlignment();
        Legend.LegendDirection direction = mLegend.getDirection();
        float defaultFormSize = Utils.convertDpToPixel(mLegend.getFormSize());

        // space between the entries
        float stackSpace = Utils.convertDpToPixel(mLegend.getStackSpace());
        float gap = Utils.convertDpToPixel(LABELTOVALUEGAP);
        float yoffset = mLegend.getYOffset();
        float xoffset = mLegend.getXOffset();
        float originPosX = 0.f;

        switch (horizontalAlignment) {
            case LEFT:

                if (orientation == Legend.LegendOrientation.VERTICAL)
                    originPosX = xoffset;
                else
                    originPosX = mViewPortHandler.contentLeft() + xoffset;

                if (direction == Legend.LegendDirection.RIGHT_TO_LEFT)
                    originPosX += mLegend.mNeededWidth;

                break;

            case RIGHT:

                if (orientation == Legend.LegendOrientation.VERTICAL)
                    originPosX = mViewPortHandler.getChartWidth() - xoffset;
                else
                    originPosX = mViewPortHandler.contentRight() - xoffset;

                if (direction == Legend.LegendDirection.LEFT_TO_RIGHT)
                    originPosX -= mLegend.mNeededWidth;

                break;

            case TOP_LEFT:
                if (orientation == Legend.LegendOrientation.VERTICAL)
                    originPosX = xoffset;
                else
                    originPosX = mViewPortHandler.contentLeft() + xoffset;
                if (direction == Legend.LegendDirection.RIGHT_TO_LEFT)
                    originPosX += mLegend.mNeededWidth;

                break;


            case CENTER:

                if (orientation == Legend.LegendOrientation.VERTICAL)
                    originPosX = mViewPortHandler.getChartWidth() / 2.f;
                else
                    originPosX = mViewPortHandler.contentLeft()
                            + mViewPortHandler.contentWidth() / 2.f;

                originPosX += (direction == Legend.LegendDirection.LEFT_TO_RIGHT
                        ? +xoffset
                        : -xoffset);

                // Horizontally layed out legends do the center offset on a line basis,
                // So here we offset the vertical ones only.
                if (orientation == Legend.LegendOrientation.VERTICAL) {
                    originPosX += (direction == Legend.LegendDirection.LEFT_TO_RIGHT
                            ? -mLegend.mNeededWidth / 2.0 + xoffset
                            : mLegend.mNeededWidth / 2.0 - xoffset);
                }

                break;
        }

        switch (orientation) {
            case HORIZONTAL: {

                List<FSize> calculatedLineSizes = mLegend.getCalculatedLineSizes();
                List<FSize> calculatedLabelSizes = mLegend.getCalculatedLabelSizes();
                List<Boolean> calculatedLabelBreakPoints = mLegend.getCalculatedLabelBreakPoints();

                float posX = originPosX;
                float posY = 0.f;

                switch (verticalAlignment) {
                    case TOP:
                        posY = yoffset;
                        break;

                    case BOTTOM:
                        posY = mViewPortHandler.getChartHeight() - yoffset - mLegend.mNeededHeight;
                        break;

                    case CENTER:
                        posY = (mViewPortHandler.getChartHeight() - mLegend.mNeededHeight) / 2.f + yoffset;
                        break;
                }

                /** If a value is selected, draw the Y Value as Header **/
                Rect bounds = new Rect();
                this.mLegendLabelPaint.getTextBounds("AyDemo", 0, "AyDemo".length(), bounds);
                float textLength  = Utils.calcTextWidth(mLegendLabelPaint, entries[0].label);
                if (this.selectedValues != null) {
                    float legendSize = Float.isNaN(entries[0].formSize) ? defaultFormSize : Utils.convertDpToPixel(entries[0].formSize);
                    drawXValHeader(c, originPosX + legendSize + textLength + formToTextSpace + gap, posY);
                }
                posY += (bounds.bottom - bounds.top);

                int lineIndex = 0;

                for (int i = 0, count = entries.length; i < count; i++) {

                    LegendEntry e = entries[i];
                    boolean drawingForm = e.form != Legend.LegendForm.NONE;
                    float formSize = Float.isNaN(e.formSize) ? defaultFormSize : Utils.convertDpToPixel(e.formSize);

                    if (i < calculatedLabelBreakPoints.size() && calculatedLabelBreakPoints.get(i)) {
                        posX = originPosX;
                        posY += labelLineHeight + labelLineSpacing;
                    }

                    if (posX == originPosX &&
                            horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER &&
                            lineIndex < calculatedLineSizes.size()) {
                        posX += (direction == Legend.LegendDirection.RIGHT_TO_LEFT
                                ? calculatedLineSizes.get(lineIndex).width
                                : -calculatedLineSizes.get(lineIndex).width) / 2.f;
                        lineIndex++;
                    }

                    boolean isStacked = e.label == null; // grouped forms have null labels

                    if (drawingForm) {
                        if (direction == Legend.LegendDirection.RIGHT_TO_LEFT)
                            posX -= formSize;

                        drawForm(c, posX, posY + formYOffset, e, mLegend);

                        if (direction == Legend.LegendDirection.LEFT_TO_RIGHT)
                            posX += formSize;
                    }

                    if (!isStacked) {
                        if (drawingForm)
                            posX += direction == Legend.LegendDirection.RIGHT_TO_LEFT ? -formToTextSpace :
                                    formToTextSpace;

                        if (direction == Legend.LegendDirection.RIGHT_TO_LEFT)
                            posX -= calculatedLabelSizes.get(i).width;

                        drawLabel(c, posX, posY + labelLineHeight, e.label);
                        if (this.selectedValues != null) {
                            posX += textLength + gap;
                            drawYValWithLabel(c, posX, posY + labelLineHeight , selectedValues.getYVal() );
                        }

                        if (direction == Legend.LegendDirection.LEFT_TO_RIGHT)
                            posX += calculatedLabelSizes.get(i).width;

                        posX += direction == Legend.LegendDirection.RIGHT_TO_LEFT ? -xEntrySpace : xEntrySpace;
                    } else
                        posX += direction == Legend.LegendDirection.RIGHT_TO_LEFT ? -stackSpace : stackSpace;
                }

                break;
            }

            case VERTICAL: {
                // contains the stacked legend size in pixels
                float stack = 0f;
                boolean wasStacked = false;
                float posY = 0.f;

                switch (verticalAlignment) {
                    case TOP:
                        posY = (((horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER) ||
                                (horizontalAlignment == Legend.LegendHorizontalAlignment.TOP_LEFT) )
                                ? 0.f
                                : mViewPortHandler.contentTop());
                        posY += yoffset;
                        break;

                    case BOTTOM:
                        posY = (horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER
                                ? mViewPortHandler.getChartHeight()
                                : mViewPortHandler.contentBottom());
                        posY -= mLegend.mNeededHeight + yoffset;
                        break;

                    case CENTER:
                        posY = mViewPortHandler.getChartHeight() / 2.f
                                - mLegend.mNeededHeight / 2.f
                                + mLegend.getYOffset();
                        break;
                }

                /** If a value is selected, draw the Y Value as Header **/
                Rect bounds = new Rect();
                this.mLegendLabelPaint.getTextBounds("AyDemo", 0, "AyDemo".length(), bounds);
                float textLength  = Utils.calcTextWidth(mLegendLabelPaint, entries[0].label);
                if (this.selectedValues != null) {
                    float legendSize = Float.isNaN(entries[0].formSize) ? defaultFormSize : Utils.convertDpToPixel(entries[0].formSize);
                    drawXValHeader(c, originPosX + legendSize + textLength + formToTextSpace + gap, posY);
                }
                posY += (bounds.bottom - bounds.top);


                for (int i = 0; i < entries.length; i++) {

                    LegendEntry e = entries[i];
                    boolean drawingForm = e.form != Legend.LegendForm.NONE;
                    float formSize = Float.isNaN(e.formSize) ? defaultFormSize : Utils.convertDpToPixel(e.formSize);

                    float posX = originPosX;

                    if (drawingForm) {
                        if (direction == Legend.LegendDirection.LEFT_TO_RIGHT)
                            posX += stack;
                        else
                            posX -= formSize - stack;

                        drawForm(c, posX, posY + formYOffset, e, mLegend);

                        if (direction == Legend.LegendDirection.LEFT_TO_RIGHT)
                            posX += formSize;
                    }

                    if (e.label != null) {

                        if (drawingForm && !wasStacked)
                            posX += direction == Legend.LegendDirection.LEFT_TO_RIGHT ? formToTextSpace
                                    : -formToTextSpace;
                        else if (wasStacked)
                            posX = originPosX;

                        if (direction == Legend.LegendDirection.RIGHT_TO_LEFT)
                            posX -= Utils.calcTextWidth(mLegendLabelPaint, e.label);

                        if (!wasStacked) {
                            drawLabel(c, posX, posY + labelLineHeight, e.label);
                            if (this.selectedValues != null) {
                                posX += textLength + gap;
                                drawYValWithLabel(c, posX, posY + labelLineHeight , selectedValues.getYVal() );
                            }
                        } else {
                            posY += labelLineHeight + labelLineSpacing;
                            drawLabel(c, posX, posY + labelLineHeight, e.label);
                            if (this.selectedValues != null) {
                                posX += textLength + gap;
                                drawYValWithLabel(c, posX, posY + labelLineHeight, selectedValues.getYVal() );
                            }
                        }

                        // make a step down
                        posY += labelLineHeight + labelLineSpacing;
                        stack = 0f;
                    } else {
                        stack += formSize + stackSpace;
                        wasStacked = true;
                    }
                }

                break;

            }
        }
    }


    private Path mLineFormPath = new Path();
    /**
     * Draws the Legend-form at the given position with the color at the given
     * index.
     *
     * @param c      canvas to draw with
     * @param x      position
     * @param y      position
     * @param entry  the entry to render
     * @param legend the legend context
     */
    @Override
    protected void drawForm(
            Canvas c,
            float x, float y,
            LegendEntry entry,
            Legend legend) {

        if (entry.formColor == ColorTemplate.COLOR_SKIP ||
                entry.formColor == ColorTemplate.COLOR_NONE ||
                entry.formColor == 0)
            return;

        int restoreCount = c.save();

        Legend.LegendForm form = entry.form;
        if (form == Legend.LegendForm.DEFAULT)
            form = legend.getForm();

        mLegendFormPaint.setColor(entry.formColor);

        final float formSize = Utils.convertDpToPixel(
                Float.isNaN(entry.formSize)
                        ? legend.getFormSize()
                        : entry.formSize);
        final float half = formSize / 2f;

        switch (form) {
            case NONE:
                // Do nothing
                break;

            case EMPTY:
                // Do not draw, but keep space for the form
                break;

            case DEFAULT:
            case CIRCLE:
                mLegendFormPaint.setStyle(Paint.Style.FILL);
                c.drawCircle(x + half, y, half, mLegendFormPaint);
                break;

            case SQUARE:
                mLegendFormPaint.setStyle(Paint.Style.FILL);
                c.drawRect(x, y - half, x + formSize, y + half, mLegendFormPaint);
                break;

            case LINE:
            {
                final float formLineWidth = Utils.convertDpToPixel(
                        Float.isNaN(entry.formLineWidth)
                                ? legend.getFormLineWidth()
                                : entry.formLineWidth);
                final DashPathEffect formLineDashEffect = entry.formLineDashEffect == null
                        ? legend.getFormLineDashEffect()
                        : entry.formLineDashEffect;
                mLegendFormPaint.setStyle(Paint.Style.STROKE);
                mLegendFormPaint.setStrokeWidth(formLineWidth);
                mLegendFormPaint.setPathEffect(formLineDashEffect);

                mLineFormPath.reset();
                mLineFormPath.moveTo(x, y);
                mLineFormPath.lineTo(x + formSize, y);
                c.drawPath(mLineFormPath, mLegendFormPaint);
            }
            break;
        }

        c.restoreToCount(restoreCount);
    }

    public Paint.FontMetrics getLegendFortMetrics() {
        return legendFontMetrics;
    }

    protected void drawYValWithLabel(Canvas c, float x, float y, String labelText) {
        Paint p = new Paint(mLegendLabelPaint);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        c.drawText(labelText, x, y, p);

    }

    protected void drawXValHeader(Canvas c, float x, float y) {
        this.drawTextOnHeader(c, x, y , this.selectedValues.getXVal(), this.mLegendLabelPaint.getTextSize(), this.mLegendLabelPaint.getColor());
    }
    /**,
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
