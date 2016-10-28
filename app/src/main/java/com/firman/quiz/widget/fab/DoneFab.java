package com.firman.quiz.widget.fab;

import android.content.Context;
import android.util.AttributeSet;

import com.firman.quiz.R;

/**
 * Created by Firman on 10/27/2016.
 */

public class DoneFab extends FloatingActionButton {

    public DoneFab(Context context) {
        this(context, null);
    }

    public DoneFab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoneFab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setImageResource(R.drawable.ic_tick);
    }
}
