package com.firman.quiz.model;

import android.support.annotation.ColorRes;
import android.support.annotation.StyleRes;

import com.firman.quiz.R;

/**
 * Created by Firman on 10/26/2016.
 */

public enum Theme {
    topeka(R.color.topeka_primary, R.color.theme_blue_background,
            R.color.theme_blue_text, R.style.Topeka),
    blue(R.color.theme_blue_primary, R.color.theme_blue_background,
            R.color.theme_blue_text, R.style.Topeka_Blue),
    green(R.color.theme_green_primary, R.color.theme_green_background,
            R.color.theme_green_text, R.style.Topeka_Green),
    purple(R.color.theme_purple_primary, R.color.theme_purple_background,
            R.color.theme_purple_text, R.style.Topeka_Purple),
    red(R.color.theme_red_primary, R.color.theme_red_background,
            R.color.theme_red_text, R.style.Topeka_Red),
    yellow(R.color.theme_yellow_primary, R.color.theme_yellow_background,
            R.color.theme_yellow_text, R.style.Topeka_Yellow);

    private final int mColorPrimaryId;
    private final int mWindowBackgroundId;
    private final int mTextColorPrimaryId;
    private final int mStyleId;

    Theme(final int colorPrimaryId, final int windowBackgroundId,
          final int textColorPrimaryId, final int styleId) {
        mColorPrimaryId = colorPrimaryId;
        mWindowBackgroundId = windowBackgroundId;
        mTextColorPrimaryId = textColorPrimaryId;
        mStyleId = styleId;
    }

    @ColorRes
    public int getTextPrimaryColor() {
        return mTextColorPrimaryId;
    }

    @ColorRes
    public int getWindowBackgroundColor() {
        return mWindowBackgroundId;
    }

    @ColorRes
    public int getPrimaryColor() {
        return mColorPrimaryId;
    }

    @StyleRes
    public int getStyleId() {
        return mStyleId;
    }
}
