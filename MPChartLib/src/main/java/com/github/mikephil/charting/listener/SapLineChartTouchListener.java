package com.github.mikephil.charting.listener;

import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.SapLineChart;
import com.github.mikephil.charting.highlight.Highlight;

public class SapLineChartTouchListener extends BarLineChartTouchListener {
    private SapLineChart controllingChart = null;
    public SapLineChartTouchListener(SapLineChart chart, Matrix touchMatrix, float dragTriggerDistance) {
        super(chart, touchMatrix, dragTriggerDistance);
        this.controllingChart = chart;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (controllingChart.isHighlightBegun() == true) {
            //We need to handle it as special. And if Successful return false
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    controllingChart.setHighlightBegun(false);
                    break;

                default:

                    int count = event.getPointerCount();
                    if (count != 2) {
                        //controllingChart.highlightBegun = false;
                        return true;
                    }
                    Highlight[] highLighted = new Highlight[count];//controllingChart.getHighlighted();
                    float X1 = event.getX(0);
                    float Y1 = event.getY(0);
                    float X2 = event.getX(1);
                    float Y2 = event.getY(1);
                    highLighted[0] = controllingChart.getHighlightByTouchPoint(X1, Y1);
                    highLighted[1] = controllingChart.getHighlightByTouchPoint(X2, Y2);
                    controllingChart.setHighlightIndeces(highLighted);
                    //controllingChart.drawHighlightingSquare(X1, X2);
                    controllingChart.invalidate();
                    break;
            }

            return true;
        }
        controllingChart.setHighlightBegun(false);
        return super.onTouch(v, event);
    }
}
