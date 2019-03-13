package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SapLineChartRenderer extends LineChartRenderer {
    public SapLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

        LineData lineData = mChart.getLineData();

        for (Highlight high : indices) {

            ILineDataSet set = lineData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            Entry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set))
                continue;

            MPPointD pix = mChart.getTransformer(set.getAxisDependency()).getPixelForValues(e.getX(), e.getY() * mAnimator
                    .getPhaseY());

            high.setDraw((float) pix.x, (float) pix.y);

            // draw the lines
            drawHighlightLines(c, (float) pix.x, (float) pix.y, set);
            drawTriangle(c, (float)pix.x, (float)pix.y, 12f, 25f, true, set);
            //drawTriangle(c, (float)pix.x, (float)pix.y, 12f, 7f, false, set);

        }
    }

    private void drawTriangle(Canvas c, float pivotx, float pivoty, float height, float width, boolean up, ILineDataSet set) {
        // set color and stroke-width
        mHighlightPaint.setColor(set.getHighLightColor() );
        mHighlightPaint.setStrokeWidth(set.getHighlightLineWidth());
        mHighlightPaint.setStyle(Paint.Style.FILL_AND_STROKE);


        // draw highlighted lines (if enabled)
        mHighlightPaint.setPathEffect(set.getDashPathEffectHighlight());
        Path linePath = new Path();
        //if (up) {
            linePath.reset();
            linePath.moveTo(pivotx, this.mViewPortHandler.contentTop() + height);
            linePath.lineTo(pivotx - width/2f, this.mViewPortHandler.contentTop());
            linePath.lineTo(pivotx + width/2f, this.mViewPortHandler.contentTop());
            linePath.lineTo(pivotx, this.mViewPortHandler.contentTop() + height);
        c.drawPath(linePath, mHighlightPaint);
        //}  else {
            linePath.reset();
            linePath.moveTo(pivotx, this.mViewPortHandler.contentBottom()  - height);
            linePath.lineTo(pivotx - width/2f, this.mViewPortHandler.contentBottom());
            linePath.lineTo(pivotx + width/2f, this.mViewPortHandler.contentBottom());
            linePath.lineTo(pivotx, this.mViewPortHandler.contentBottom() - height);

        //}

        c.drawPath(linePath, mHighlightPaint);
    }
}
