package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.SapLegendRenderer;
import com.github.mikephil.charting.renderer.SapLineChartRenderer;
import com.github.mikephil.charting.renderer.SapXAxisLabelRenderer;
import com.github.mikephil.charting.utils.Utils;

public class SapLineChart extends LineChart {

    private SapXAxisLabelRenderer mXAxisLabelRenderer;
    private float mAxisLabeTextSize = 18f;

    /**
     * Title for the chart
     */
    protected String mChartTitle = null;

    public void setXAxisLabel(String label) {
        this.mXAxisLabel = label;
    }

    private String mXAxisLabel = null;

    public SapLineChart(Context context) {
        super(context);
    }

    public SapLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SapLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * Set the Chart Title
     */
    public void setYAxisLabel(String title) {
        this.mChartTitle = title;
    }

    /**
     * Get the Chart Title
     */
    public String getYAxisLabel() {
        return this.mChartTitle;
    }

    /**
     * Set Axis labels text Size
     */
    public void setAxisLabeTextSize(float textSize) {
        this.mAxisLabeTextSize = textSize;
        mXAxisLabelRenderer.setTextSize(textSize);
    }

    public void setLabelFontType(Typeface tf) {
        mXAxisLabelRenderer.setFont(tf);
    }


    @Override
    protected void init() {
        super.init();
        //float legendTitleDp = Utils.calcTextHeight(this.getLegendRenderer().getLabelPaint(), "ABC");
        //float legendTitlePixel = Utils.convertDpToPixel(legendTitleDp);
        //mLegend.setYOffset(legendTitlePixel);
        mRenderer = new SapLineChartRenderer(this, mAnimator, mViewPortHandler);
        mLegendRenderer = new SapLegendRenderer(mViewPortHandler, mLegend);

        //float textSize = mLegend.getTextSize();
        int textColor = this.getXAxis().getTextColor();
        Typeface textTypeface = this.getXAxis().getTypeface();

        mXAxisLabelRenderer = new SapXAxisLabelRenderer(mViewPortHandler, mAxisLabeTextSize, textColor, textTypeface);
        float headerExtraSpace = ((SapLegendRenderer)mLegendRenderer).getLegendHeaderTextHeight();// + getLegend().getYOffset();
        this.getLegend().setYOffset( headerExtraSpace * 2f);
    }

    public void setAxisLabelFont(Typeface f) {
        mXAxisLabelRenderer.setFont(f);
    }

    public void calculateOffsets() {

        if (!mCustomViewPortEnabled) {

            float offsetLeft = 0f, offsetRight = 0f, offsetTop = 0f, offsetBottom = 0f;

            calculateLegendOffsets(mOffsetsBuffer);

            offsetLeft += mOffsetsBuffer.left;
            offsetTop += mOffsetsBuffer.top;
            offsetRight += mOffsetsBuffer.right;
            offsetBottom += mOffsetsBuffer.bottom;

            // offsets for y-labels
            if (mAxisLeft.needsOffset()) {
                offsetLeft += mAxisLeft.getRequiredWidthSpace(mAxisRendererLeft
                        .getPaintAxisLabels());
            }

            if (mAxisRight.needsOffset()) {
                offsetRight += mAxisRight.getRequiredWidthSpace(mAxisRendererRight
                        .getPaintAxisLabels());
            }

            if (mXAxis.isEnabled() && mXAxis.isDrawLabelsEnabled()) {

                float xLabelHeight = mXAxis.mLabelRotatedHeight + mXAxis.getYOffset();

                // offsets for x-labels
                if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {

                    offsetBottom += xLabelHeight;

                } else if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {

                    offsetTop += xLabelHeight;

                } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {

                    offsetBottom += xLabelHeight;
                    offsetTop += xLabelHeight;
                }
            }

            offsetTop += getExtraTopOffset();
            offsetRight += getExtraRightOffset();
            offsetBottom += getExtraBottomOffset();
            offsetLeft += getExtraLeftOffset();

            RectF r = mXAxisLabelRenderer.calculateOffsetBounds();
            offsetTop += r.top ;
            offsetBottom += 3 * r.bottom;
            float minOffset = Utils.convertDpToPixel(mMinOffset);

            mViewPortHandler.restrainViewPort(
                    Math.max(minOffset, offsetLeft),
                    Math.max(minOffset, offsetTop),
                    Math.max(minOffset, offsetRight),
                    Math.max(minOffset, offsetBottom));

            if (mLogEnabled) {
                Log.i(LOG_TAG, "offsetLeft: " + offsetLeft + ", offsetTop: " + offsetTop
                        + ", offsetRight: " + offsetRight + ", offsetBottom: " + offsetBottom);
                Log.i(LOG_TAG, "Content: " + mViewPortHandler.getContentRect().toString());
            }
            //Making room form X Axis Label
            //mViewPortHandler.setChartDimens(mViewPortHandler.getChartWidth(), mViewPortHandler.getChartHeight() - titleHeightPixel );
        }

        prepareOffsetMatrix();
        prepareValuePxMatrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mXAxisLabel != null ) {
            float textSize = this.getXAxis().getTextSize();
            int textColor = this.getXAxis().getTextColor();
            Typeface textTypeface = this.getXAxis().getTypeface();
            if (mXAxisLabel != null )
                mXAxisLabelRenderer.renderXAxisLabel(canvas, mXAxisLabel);
            if (mChartTitle != null)
                mXAxisLabelRenderer.renderTitleText(canvas, mChartTitle);
        }

    }
}
