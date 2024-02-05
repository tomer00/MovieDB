package com.android.moviedb.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.android.moviedb.R;

public class BgOval extends View {
    //region CONSTRUCTOR
    private float radius = 0f;
    private final RectF rectOval = new RectF(toPX(12f), toPX(12f), toPX(36f), toPX(36f));

    private final Paint paintBlur = new Paint();

    // Constructors
    public BgOval(Context context) {
        super(context);
        commonInit(null);
    }

    public BgOval(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        commonInit(attributeSet);
    }

    public BgOval(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        commonInit(attributeSet);
    }

    private void commonInit(AttributeSet attrs) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        if (attrs == null) return;
        // Initialize paintBlur with default values
        paintBlur.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paintBlur.setMaskFilter(new BlurMaskFilter(toPX(22f), BlurMaskFilter.Blur.NORMAL));
        paintBlur.setAntiAlias(true);

        // Obtain styled attributes from XML
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BgOval);

        // Set values based on styled attributes
        paintBlur.setColor(typedArray.getColor(R.styleable.BgOval_colorDots, ContextCompat.getColor(getContext(), R.color.white)));
        radius = toPX(typedArray.getDimension(R.styleable.BgOval_radius, 22f));
        paintBlur.setMaskFilter(new BlurMaskFilter(radius / 2, BlurMaskFilter.Blur.NORMAL));

        // Recycle the typed array
        typedArray.recycle();
    }

    //endregion CONSTRUCTOR

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float p12 = toPX(12);
        float x = (w - radius) / 2;
        float y = (h - radius) / 2;
        rectOval.set(x, y, x + radius, y + radius);
    }

    //region DRAW

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(rectOval, paintBlur);
    }

    //endregion DRAW

    private float toPX(float dp) {
        return getContext().getResources().getDisplayMetrics().density * dp;
    }

    public void setCol(int col) {
        paintBlur.setColor(col);
        postInvalidate();
    }
}

