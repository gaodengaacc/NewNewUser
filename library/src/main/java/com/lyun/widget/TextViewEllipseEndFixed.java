package com.lyun.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class TextViewEllipseEndFixed extends AppCompatTextView {

    public TextViewEllipseEndFixed(Context context) {
        super(context);
    }

    public TextViewEllipseEndFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewEllipseEndFixed(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onDraw(Canvas canvas) {

        int slh = measureTextViewHeight(".", getTextSize(), getMeasuredWidth());
        int tlh = measureTextViewHeight(".\n.", getTextSize(), getMeasuredWidth());

        setLines(getMeasuredHeight() / (tlh - slh));

        super.onDraw(canvas);
    }

    private int measureTextViewHeight(String text, float textSize, int deviceWidth) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

}