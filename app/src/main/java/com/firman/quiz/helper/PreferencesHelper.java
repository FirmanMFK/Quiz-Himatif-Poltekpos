package com.firman.quiz.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.firman.quiz.model.Avatar;
import com.firman.quiz.model.Player;

/**
 * Created by Firman on 10/25/2016.
 */

public class PreferencesHelper {

    private static final String PLAYER_PREFERENCES = "playerPreferences";
    private static final String PREFERENCE_FIRST_NAME = PLAYER_PREFERENCES + ".firstName";
    private static final String PREFERENCE_LAST_INITIAL = PLAYER_PREFERENCES + ".lastInitial";
    private static final String PREFERENCE_AVATAR = PLAYER_PREFERENCES + ".avatar";

    private PreferencesHelper() {
        //no instance
    }

    /**
     * Writes a {@link com.firman.quiz.model.Player} to preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @param player The {@link com.firman.quiz.model.Player} to write.
     */
    public static void writeToPreferences(Context context, Player player) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_FIRST_NAME, player.getFirstName());
        editor.putString(PREFERENCE_LAST_INITIAL, player.getLastInitial());
        editor.putString(PREFERENCE_AVATAR, player.getAvatar().name());
        editor.apply();
    }

    /**
     * Retrieves a {@link com.firman.quiz.model.Player} from preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @return A previously saved player or <code>null</code> if none was saved previously.
     */
    public static Player getPlayer(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String firstName = preferences.getString(PREFERENCE_FIRST_NAME, null);
        final String lastInitial = preferences.getString(PREFERENCE_LAST_INITIAL, null);
        final String avatarPreference = preferences.getString(PREFERENCE_AVATAR, null);
        final Avatar avatar;
        if (null != avatarPreference) {
            avatar = Avatar.valueOf(avatarPreference);
        } else {
            avatar = null;
        }

        if (null == firstName && null == lastInitial && null == avatar) {
            return null;
        }
        return new Player(firstName, lastInitial, avatar);
    }

    /**
     * Signs out a player by removing all it's data.
     *
     * @param context The context which to obtain the SharedPreferences from.
     */
    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_FIRST_NAME);
        editor.remove(PREFERENCE_LAST_INITIAL);
        editor.remove(PREFERENCE_AVATAR);
        editor.apply();
    }

    /**
     * Check whether a user is currently signed in.
     *
     * @param context The context to check this in.
     * @return <code>true</code> if login data exists, else <code>false</code>.
     */
    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCE_FIRST_NAME) &&
                preferences.contains(PREFERENCE_LAST_INITIAL) &&
                preferences.contains(PREFERENCE_AVATAR);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PLAYER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
