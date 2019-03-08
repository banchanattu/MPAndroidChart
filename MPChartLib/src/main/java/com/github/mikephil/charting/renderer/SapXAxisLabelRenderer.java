package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.TypedValue;

import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class SapXAxisLabelRenderer extends Renderer {

   private float mTextSize;
   //Will decide later how to find out logically.
   private float mTitleTextSize = 36;
   private int mTextColor;
   Typeface mTypeFace;
   public SapXAxisLabelRenderer(ViewPortHandler viewPortHandler, float textSize, int textColor, Typeface typeFace) {
        super(viewPortHandler);
        this.mTextSize = textSize;
        this.mTextColor = textColor;
        this.mTypeFace = typeFace;
    }

    public void  setFont(Typeface f) {
       this.mTypeFace = f;
    }

    public RectF calculateOffsetBounds() {
       RectF rect = new RectF();
        Paint p = new Paint();
        p.setTextSize(mTextSize);
        p.setColor(mTextColor);
        p.setTypeface(mTypeFace);
        p.setTextAlign(Paint.Align.CENTER);

        Rect r = new Rect();
        p.getTextBounds("AyDEMO", 0, "AyDEMO".length(), r);
        rect.bottom =  (r.bottom - r.top);
        p.setTextSize(mTitleTextSize);
        p.getTextBounds("AyDEMO", 0, "AyDEMO".length(), r);
        rect.top = (r.bottom - r.top);;

        return rect;

    }

    public void renderXAxisLabel(Canvas c, String label) {
       // Calculate X and U position for Label.
        RectF rectF = mViewPortHandler.getContentRect();
        float xPosition = (rectF.left + rectF.right)/2.0f;
        Paint p = new Paint();
        p.setTextSize(mTextSize);
        p.setColor(mTextColor);
        p.setTypeface(mTypeFace);
        p.setTextAlign(Paint.Align.CENTER);

        Rect r = new Rect();
        p.getTextBounds(label, 0, label.length(), r);
        float yPosition = rectF.bottom + 2* (r.bottom - r.top);

        c.drawText(label, xPosition, yPosition, p);

    }

    public void renderTitleText(Canvas c, String title) {

        RectF rectF = mViewPortHandler.getContentRect();
        float xPosition = rectF.left ;
        Paint p = new Paint();
        p.setTextSize(mTitleTextSize);
        p.setColor(mTextColor);
        p.setTypeface(mTypeFace);
        p.setTextAlign(Paint.Align.LEFT);

        //Rect r = new Rect();
        //p.getTextBounds(title, 0, title.length(), r);
        Paint.FontMetrics fm = p.getFontMetrics();
        //fm.
        float yPosition = rectF.top - fm.descent ;

        c.drawText(title, xPosition, yPosition, p);



    }
}
