package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.SapLegendRenderer;
import com.github.mikephil.charting.utils.Utils;

public class SapLineChart extends LineChart {

    public SapLineChart(Context context) {
        super(context);
    }

    public SapLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SapLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }




//    @Override
//    public void calculateOffsets() {
//
//        super.calculateOffsets();
//        RectF bounds = mViewPortHandler.getContentRect();
//        float minOffset = Utils.convertDpToPixel(mMinOffset);
//        float offsetTop = bounds.top;
//        float offsetRight = bounds.right;
//        float offsetBottom = bounds.bottom;
//        float offsetLeft = bounds.left;
//        float titleHeightDp = Utils.calcTextHeight(this.mDescPaint, "ABC");
//        float titleHeightPixel = Utils.convertDpToPixel(titleHeightDp);
//        //offsetTop += titleHeightPixel;
//        mViewPortHandler.restrainViewPort(
//                Math.max(minOffset, offsetLeft),
//                Math.max(minOffset, offsetTop),
//                Math.max(minOffset, offsetRight),
//                Math.max(minOffset, offsetBottom));
//
//
//        prepareOffsetMatrix();
//        prepareValuePxMatrix();
//        postInvalidate();
//    }
    @Override
    protected void init() {
        super.init();
        float legendTitleDp = Utils.calcTextHeight(this.getLegendRenderer().getLabelPaint(), "ABC");
        float legendTitlePixel = Utils.convertDpToPixel(legendTitleDp);
        mLegend.setYOffset(legendTitlePixel);
        mLegendRenderer = new SapLegendRenderer(mViewPortHandler, mLegend);
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
            Rect bounds = new Rect();
            this.getLegendRenderer().getLabelPaint().getTextBounds("ABC", 0, "ABC".length(), bounds);
            float labelLineSpacing = Utils.getLineSpacing(getLegendRenderer().getLabelPaint(), ((SapLegendRenderer)getLegendRenderer()).getLegendFortMetrics())
                    + Utils.convertDpToPixel(mLegend.getYEntrySpace());
            float titleHeightDp = Utils.convertDpToPixel(bounds.bottom - bounds.top + labelLineSpacing);

            float titleHeightPixel = Utils.convertDpToPixel(titleHeightDp);
            offsetTop += titleHeightPixel ;
            offsetBottom -= titleHeightPixel ;
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
            mViewPortHandler.setChartDimens(mViewPortHandler.getChartWidth(), mViewPortHandler.getChartHeight() - titleHeightPixel );
        }

        prepareOffsetMatrix();
        prepareValuePxMatrix();

    }


}
