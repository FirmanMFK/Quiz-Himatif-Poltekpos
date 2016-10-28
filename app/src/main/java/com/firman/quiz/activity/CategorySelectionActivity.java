package com.firman.quiz.activity;

/**
 * Created by Firman on 10/27/2016.
 */

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firman.quiz.R;
import com.firman.quiz.fragment.CategorySelectionFragment;
import com.firman.quiz.helper.PreferencesHelper;
import com.firman.quiz.model.Player;
import com.firman.quiz.persistence.TopekaDatabaseHelper;
import com.firman.quiz.widget.AvatarView;

public class CategorySelectionActivity extends Activity {

    private static final String EXTRA_PLAYER = "player";

    public static void start(Context context, Player player, ActivityOptions options) {
        Intent starter = new Intent(context, CategorySelectionActivity.class);
        starter.putExtra(EXTRA_PLAYER, player);
        context.startActivity(starter, options.toBundle());
    }

    public static void start(Context context, Player player) {
        Intent starter = new Intent(context, CategorySelectionActivity.class);
        starter.putExtra(EXTRA_PLAYER, player);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_selection);
        Player player = getIntent().getParcelableExtra(EXTRA_PLAYER);
        setUpToolbar(player);
        if (savedInstanceState == null) {
            attachCategoryGridFragment();
        } else {
            setProgressBarVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView scoreView = (TextView) findViewById(R.id.score);
        final int score = TopekaDatabaseHelper.getScore(this);
        scoreView.setText(getString(R.string.x_points, score));
    }

    private void setUpToolbar(Player player) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        setActionBar(toolbar);
        //noinspection ConstantConditions
        getActionBar().setDisplayShowTitleEnabled(false);
        final AvatarView avatarView = (AvatarView) toolbar.findViewById(R.id.avatar);
        avatarView.setImageDrawable(getDrawable(player.getAvatar().getDrawableId()));
        ((TextView) toolbar.findViewById(R.id.title)).setText(getDisplayName(player));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out: {
                signOut();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        PreferencesHelper.signOut(this);
        TopekaDatabaseHelper.reset(this);
        SignInActivity.start(this, false, null);
        finishAfterTransition();
    }

    private String getDisplayName(Player player) {
        return getString(R.string.player_display_name, player.getFirstName(),
                player.getLastInitial());
    }

    private void attachCategoryGridFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.quiz_container, CategorySelectionFragment.newInstance())
                .commit();
        setProgressBarVisibility(View.GONE);
    }

    private void setProgressBarVisibility(int visibility) {
        findViewById(R.id.progress).setVisibility(visibility);
    }
}

