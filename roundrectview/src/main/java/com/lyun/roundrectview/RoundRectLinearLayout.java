package com.lyun.roundrectview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RoundRectLinearLayout extends LinearLayout {

    private RoundRectShapeHelper mRoundRectShapeHelper;

    public RoundRectLinearLayout(Context context) {
        this(context, null);
    }

    public RoundRectLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRoundRectShapeHelper = new RoundRectShapeHelper(context, attrs, defStyleAttr, 0, mAttrsDelegate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRoundRectShapeHelper.applyBackgroundDrawable(this);
    }

    private RoundRectAttrsDelegate mAttrsDelegate = new RoundRectAttrsDelegate() {
        @Override
        public int[] getName() {
            return R.styleable.RoundRectLinearLayout;
        }

        @Override
        public int getBackgroundColor() {
            return R.styleable.RoundRectLinearLayout_backgroundColor;
        }

        @Override
        public int getBorderWidth() {
            return R.styleable.RoundRectLinearLayout_borderWidth;
        }

        @Override
        public int getBorderColor() {
            return R.styleable.RoundRectLinearLayout_borderColor;
        }

        @Override
        public int getBorderPadding() {
            return R.styleable.RoundRectLinearLayout_borderPadding;
        }

        @Override
        public int getBorderPaddingLeft() {
            return R.styleable.RoundRectLinearLayout_borderPaddingLeft;
        }

        @Override
        public int getBorderPaddingTop() {
            return R.styleable.RoundRectLinearLayout_borderPaddingTop;
        }

        @Override
        public int getBorderPaddingRight() {
            return R.styleable.RoundRectLinearLayout_borderPaddingRight;
        }

        @Override
        public int getBorderPaddingBottom() {
            return R.styleable.RoundRectLinearLayout_borderPaddingBottom;
        }

        @Override
        public int getRadius() {
            return R.styleable.RoundRectLinearLayout_cornerRadius;
        }

        @Override
        public int getTopLeftRadius() {
            return R.styleable.RoundRectLinearLayout_topLeftRadius;
        }

        @Override
        public int getTopRightRadius() {
            return R.styleable.RoundRectLinearLayout_topRightRadius;
        }

        @Override
        public int getBottomRightRadius() {
            return R.styleable.RoundRectLinearLayout_bottomRightRadius;
        }

        @Override
        public int getBottomLeftRadius() {
            return R.styleable.RoundRectLinearLayout_bottomLeftRadius;
        }

        @Override
        public int getInnerTopLeftRadius() {
            return R.styleable.RoundRectLinearLayout_innerTopLeftRadius;
        }

        @Override
        public int getOuterTopLeftRadius() {
            return R.styleable.RoundRectLinearLayout_outerTopLeftRadius;
        }

        @Override
        public int getInnerTopRightRadius() {
            return R.styleable.RoundRectLinearLayout_innerTopRightRadius;
        }

        @Override
        public int getOuterTopRightRadius() {
            return R.styleable.RoundRectLinearLayout_outerTopRightRadius;
        }

        @Override
        public int getInnerBottomRightRadius() {
            return R.styleable.RoundRectLinearLayout_innerBottomRightRadius;
        }

        @Override
        public int getOuterBottomRightRadius() {
            return R.styleable.RoundRectLinearLayout_outerBottomRightRadius;
        }

        @Override
        public int getInnerBottomLeftRadius() {
            return R.styleable.RoundRectLinearLayout_innerBottomLeftRadius;
        }

        @Override
        public int getOuterBottomLeftRadius() {
            return R.styleable.RoundRectLinearLayout_outerBottomLeftRadius;
        }
    };
}
