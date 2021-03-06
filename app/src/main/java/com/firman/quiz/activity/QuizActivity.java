package com.firman.quiz.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.firman.quiz.R;
import com.firman.quiz.fragment.QuizFragment;
import com.firman.quiz.model.Category;
import com.firman.quiz.persistence.TopekaDatabaseHelper;
import com.firman.quiz.widget.fab.FloatingActionButton;

import static com.firman.quiz.adapter.CategoryAdapter.DRAWABLE;

/**
 * Created by Firman on 10/27/2016.
 */

public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String IMAGE_CATEGORY = "image_category_";
    private static final String STATE_IS_PLAYING = "isPlaying";
    private static final int UNDEFINED = -1;
    private static final String FRAGMENT_TAG = "Quiz";
    private Interpolator mInterpolator;
    private String mCategoryId;
    private QuizFragment mQuizFragment;
    private Toolbar mToolbar;
    private ImageView mQuizFab;
    private boolean mSavedStateIsPlaying;
    private ImageView mIcon;
    private Animator mCircularReveal;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.fab_quiz:
                    startQuizFromClickOn(v);
                    break;
                case R.id.submitAnswer:
                    submitAnswer();
                    break;
                case R.id.quiz_done:
                    finishAfterTransition();
                    break;
                case UNDEFINED:
                    final CharSequence contentDescription = v.getContentDescription();
                    if (contentDescription != null && contentDescription.equals(
                            getString(R.string.up))) {
                        onBackPressed();
                        break;
                    }
                default:
                    throw new UnsupportedOperationException(
                            "OnClick has not been implemented for " + getResources().
                                    getResourceName(v.getId()));
            }
        }
    };

    public static Intent getStartIntent(Context context, Category category) {
        Intent starter = new Intent(context, QuizActivity.class);
        starter.putExtra(Category.TAG, category.getId());
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate and set the enter transition for this activity.
        final Transition sharedElementEnterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.quiz_enter);
        getWindow().setSharedElementEnterTransition(sharedElementEnterTransition);

        mCategoryId = getIntent().getStringExtra(Category.TAG);
        mInterpolator = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.fast_out_slow_in);
        if (null != savedInstanceState) {
            mSavedStateIsPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING);
        }
        populate(mCategoryId);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        if (mSavedStateIsPlaying) {
            mQuizFragment = (QuizFragment) getFragmentManager().findFragmentByTag(
                    FRAGMENT_TAG);
            findViewById(R.id.quiz_fragment_container).setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_IS_PLAYING, mQuizFab.getVisibility() == View.GONE);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mIcon == null || mQuizFab == null) {
            // Skip the animation if icon or fab are not initialized.
            super.onBackPressed();
            return;
        }

        // Scale the icon and fab to 0 size before calling onBackPressed if it exists.
        mIcon.animate()
                .scaleX(.7f)
                .scaleY(.7f)
                .alpha(0f)
                .setInterpolator(mInterpolator)
                .start();

        mQuizFab.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setInterpolator(mInterpolator)
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        QuizActivity.super.onBackPressed();
                    }
                })
                .start();
    }

    private void startQuizFromClickOn(final View view) {
        initQuizFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.quiz_fragment_container, mQuizFragment, FRAGMENT_TAG).commit();
        final View fragmentContainer = findViewById(R.id.quiz_fragment_container);
        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;
        int finalRadius = Math.max(fragmentContainer.getWidth(), fragmentContainer.getHeight());
        mCircularReveal = ViewAnimationUtils.createCircularReveal(
                fragmentContainer, centerX, centerY, 0, finalRadius);
        fragmentContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);

        mCircularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIcon.setVisibility(View.GONE);
                mCircularReveal.removeListener(this);
            }
        });

        mCircularReveal.start();

        // the toolbar should not have more elevation than the content while playing
        mToolbar.setElevation(0);
    }

    public void elevateToolbar() {
        mToolbar.setElevation(getResources().getDimension(R.dimen.elevation_header));
    }

    private void initQuizFragment() {
        mQuizFragment = QuizFragment.newInstance(mCategoryId,
                new QuizFragment.SolvedStateListener() {
                    @Override
                    public void onCategorySolved() {
                        elevateToolbar();
                        displayDoneFab();
                    }

                    private void displayDoneFab() {
                        /* We're re-using the already existing fab and give it some
                         * new values. This has to run delayed due to the queued animation
                         * to hide the fab initially.
                         */
                        if (null != mCircularReveal && mCircularReveal.isRunning()) {
                            mCircularReveal.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    showQuizFabWithDoneIcon();
                                    mCircularReveal.removeListener(this);
                                }
                            });
                        } else {
                            showQuizFabWithDoneIcon();
                        }
                    }

                    private void showQuizFabWithDoneIcon() {
                        mQuizFab.setImageResource(R.drawable.ic_tick);
                        mQuizFab.setId(R.id.quiz_done);
                        mQuizFab.setVisibility(View.VISIBLE);
                        mQuizFab.setScaleX(0f);
                        mQuizFab.setScaleY(0f);
                        mQuizFab.animate()
                                .scaleX(1)
                                .scaleY(1)
                                .setInterpolator(mInterpolator)
                                .setListener(null)
                                .start();
                    }
                });
        // the toolbar should not have more elevation than the content while playing
        mToolbar.setElevation(0);
    }

    /**
     * Proceeds the quiz to it's next state.
     */
    public void proceed() {
        submitAnswer();
    }

    private void submitAnswer() {
        elevateToolbar();
        if (!mQuizFragment.showNextPage()) {
            mQuizFragment.showSummary();
            return;
        }
        mToolbar.setElevation(0);
    }

    private void populate(String categoryId) {
        if (null == categoryId) {
            Log.w(TAG, "Didn't find a category. Finishing");
            finish();
        }
        Category category = TopekaDatabaseHelper.getCategoryWith(this, categoryId);
        setTheme(category.getTheme().getStyleId());
        initLayout(category.getId());
        initToolbar(category);
    }

    private void initLayout(String categoryId) {
        setContentView(R.layout.activity_quiz);
        mIcon = (ImageView) findViewById(R.id.icon);
        int resId = getResources().getIdentifier(IMAGE_CATEGORY + categoryId, DRAWABLE,
                getApplicationContext().getPackageName());
        mIcon.setImageResource(resId);
        mIcon.setImageResource(resId);
        mIcon.animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(300)
                .start();
        mQuizFab = (FloatingActionButton) findViewById(R.id.fab_quiz);
        mQuizFab.setImageResource(R.drawable.ic_play);
        mQuizFab.setVisibility(mSavedStateIsPlaying ? View.GONE : View.VISIBLE);
        mQuizFab.setOnClickListener(mOnClickListener);
        mQuizFab.animate()
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(400)
                .start();
    }

    private void initToolbar(Category category) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_quiz);
        mToolbar.setTitle(category.getName());
        mToolbar.setNavigationOnClickListener(mOnClickListener);
        if (mSavedStateIsPlaying) {
            // the toolbar should not have more elevation than the content while playing
            mToolbar.setElevation(0);
        }
    }
}
