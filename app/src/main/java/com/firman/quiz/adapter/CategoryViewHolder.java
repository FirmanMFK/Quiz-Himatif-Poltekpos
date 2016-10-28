package com.firman.quiz.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firman.quiz.R;

/**
 * Created by Firman on 10/27/2016.
 */

public class CategoryViewHolder {

    protected TextView title;
    protected ImageView icon;

    public CategoryViewHolder(LinearLayout container) {
        icon = (ImageView) container.findViewById(R.id.category_icon);
        title = (TextView) container.findViewById(R.id.category_title);
    }
}
