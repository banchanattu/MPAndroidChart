package com.github.mikephil.charting.listener;

import android.view.MotionEvent;

import com.github.mikephil.charting.charts.SapLineChart;
import com.github.mikephil.charting.highlight.Highlight;

public class SapLineChartGestureListener implements OnChartGestureListener {
    private SapLineChart controllingChart = null;
    private SapLineChartGestureListener(){}

    public SapLineChartGestureListener(SapLineChart chart) {
        controllingChart = chart;
    }
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

        controllingChart.setHighlightBegun(true);
        Highlight[] highlights = new Highlight[]{controllingChart.getHighlightByTouchPoint(me.getX(), me.getY())};
        controllingChart.setHighlightIndeces(highlights);
        controllingChart.invalidate();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
