package com.firman.quiz.widget.fab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.firman.quiz.R;
import com.firman.quiz.widget.outlineprovider.RoundOutlineProvider;

/**
 * Created by Firman on 10/27/2016.
 */

public class FloatingActionButton extends ImageView {

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        setClickable(true);
        setClipToOutline(true);
        setScaleType(ScaleType.CENTER_INSIDE);
        setBackgroundResource(R.drawable.fab_background);
        setElevation(getResources().getDimension(R.dimen.elevation_fab));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setOutlineProvider(new RoundOutlineProvider(Math.min(w, h)));
        }
    }
}

