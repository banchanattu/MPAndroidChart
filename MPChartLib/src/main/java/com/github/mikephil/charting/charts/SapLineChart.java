package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.components.SapLegend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.SapSelectedDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.SapMultiValueSelectedListener;
import com.github.mikephil.charting.renderer.SapLegendRenderer;
import com.github.mikephil.charting.renderer.SapLineChartRenderer;
import com.github.mikephil.charting.renderer.SapXAxisLabelRenderer;
import com.github.mikephil.charting.utils.Utils;


public class SapLineChart extends LineChart {

    private SapMultiValueSelectedListener mMultiValueSelectedListener =null;

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
        mLegend = new SapLegend();
        mLegendRenderer = new SapLegendRenderer(mViewPortHandler, mLegend);

        //float textSize = mLegend.getTextSize();
        int textColor = this.getXAxis().getTextColor();
        Typeface textTypeface = this.getXAxis().getTypeface();

        mXAxisLabelRenderer = new SapXAxisLabelRenderer(mViewPortHandler, this, mAxisLabeTextSize, textColor, textTypeface);
        float headerExtraSpace =Utils.convertPixelsToDp(((SapLegendRenderer)mLegendRenderer).getLegendHeaderTextHeight());// + getLegend().getYOffset();
        //setYOffset need the dp value so let us supply
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
    protected void calculateLegendOffsets(RectF offsets) {

        offsets.left = 0.f;
        offsets.right = 0.f;
        offsets.top = 0.f;
        offsets.bottom = 0.f;

        // setup offsets for legend
        if (mLegend != null && mLegend.isEnabled() && !mLegend.isDrawInsideEnabled()) {
            switch (mLegend.getOrientation()) {
                case VERTICAL:

                    switch (mLegend.getHorizontalAlignment()) {
                        case LEFT:
                            offsets.left += Math.min(mLegend.mNeededWidth,
                                    mViewPortHandler.getChartWidth() * mLegend.getMaxSizePercent())
                                    + mLegend.getXOffset();
                            break;

                        case RIGHT:
                            offsets.right += Math.min(mLegend.mNeededWidth,
                                    mViewPortHandler.getChartWidth() * mLegend.getMaxSizePercent())
                                    + mLegend.getXOffset();
                            break;

                        case TOP_LEFT:
                        case CENTER:

                            switch (mLegend.getVerticalAlignment()) {
                                case TOP:
                                    offsets.top += Math.min(mLegend.mNeededHeight,
                                            mViewPortHandler.getChartHeight() * mLegend.getMaxSizePercent())
                                            + mLegend.getYOffset();
                                    break;

                                case BOTTOM:
                                    offsets.bottom += Math.min(mLegend.mNeededHeight,
                                            mViewPortHandler.getChartHeight() * mLegend.getMaxSizePercent())
                                            + mLegend.getYOffset();
                                    break;

                                default:
                                    break;
                            }
                    }

                    break;

                case HORIZONTAL:

                    switch (mLegend.getVerticalAlignment()) {
                        case TOP:
                            offsets.top += Math.min(mLegend.mNeededHeight,
                                    mViewPortHandler.getChartHeight() * mLegend.getMaxSizePercent())
                                    + mLegend.getYOffset();
                            break;

                        case BOTTOM:
                            offsets.bottom += Math.min(mLegend.mNeededHeight,
                                    mViewPortHandler.getChartHeight() * mLegend.getMaxSizePercent())
                                    + mLegend.getYOffset();
                            break;

                        default:
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mXAxisLabel != null ) {
           mXAxisLabelRenderer.renderXAxisLabel(canvas, mXAxisLabel);
        }
        if (mChartTitle != null) {
           mXAxisLabelRenderer.renderTitleText(canvas,  mChartTitle);
        }

    }


    public void setMultiValueSelectedListener(SapMultiValueSelectedListener mMultiValueSelectedListener) {
        this.mMultiValueSelectedListener = mMultiValueSelectedListener;
    }

    /**
     * Highlights the value selected by touch gesture. Unlike
     * highlightValues(...), this generates a callback to the
     * OnChartValueSelectedListener.
     *
     * @param high         - the highlight object
     * @param callListener - call the listener
     */
    @Override
    public void highlightValue(Highlight high, boolean callListener) {

        Entry e = null;



        if (high == null)
            mIndicesToHighlight = null;
        else {

            if (mLogEnabled)
                Log.i(LOG_TAG, "Highlighted: " + high.toString());

            e = mData.getEntryForHighlight(high);
            if (e == null) {
                mIndicesToHighlight = null;
                high = null;
            } else {

                // set the indices to highlight
                mIndicesToHighlight = new Highlight[]{
                        high
                };
            }
        }

        setLastHighlighted(mIndicesToHighlight);

        /**
         * Select All the Values
         *
         */
        SapSelectedDataSet selectedDataSet = null;
        if (high != null) {
            float[] selectedYVals = null;
            int selectedIndex =(int)mData.getEntryForHighlight(high).getX();
            int dataSetCount = mData.getDataSetCount();
            selectedYVals = new float[dataSetCount];
            for (int i = 0; i < dataSetCount; i++) {
                selectedYVals[i] = mData.getDataSetByIndex(i).getEntryForIndex(selectedIndex).getY();
            }
            selectedDataSet = new SapSelectedDataSet(mData.getEntryForHighlight(high).getX(), selectedYVals);
        }
        if (callListener && mMultiValueSelectedListener != null) {
            if (!valuesToHighlight())
                mMultiValueSelectedListener.onUnSelected();
            else
                mMultiValueSelectedListener.onMultiValuesSelected(selectedDataSet);
        }

        if (callListener && mSelectionListener != null) {

            if (!valuesToHighlight())
                mSelectionListener.onNothingSelected();
            else {
                // notify the listener
                mSelectionListener.onValueSelected(e, high);
            }
        }

        // redraw the chart
        invalidate();
    }
}
