package com.firman.quiz.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.firman.quiz.R;
import com.firman.quiz.fragment.SignInFragment;
import com.firman.quiz.helper.PreferencesHelper;

/**
 * Created by Firman on 10/27/2016.
 */

public class SignInActivity extends Activity {

    private static final String EXTRA_EDIT = "EDIT";

    public static void start(Activity activity, Boolean edit, ActivityOptions options) {
        Intent starter = new Intent(activity, SignInActivity.class);
        starter.putExtra(EXTRA_EDIT, edit);
        if (options == null) {
            activity.startActivity(starter);
            activity.overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        } else {
            activity.startActivity(starter, options.toBundle());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final boolean edit = isInEditMode();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.sign_in_container, SignInFragment.newInstance(edit)).commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (PreferencesHelper.isSignedIn(this)) {
            finish();
        }
    }

    private boolean isInEditMode() {
        final Intent intent = getIntent();
        boolean edit = false;
        if (null != intent) {
            edit = intent.getBooleanExtra(EXTRA_EDIT, false);
        }
        return edit;
    }
}
