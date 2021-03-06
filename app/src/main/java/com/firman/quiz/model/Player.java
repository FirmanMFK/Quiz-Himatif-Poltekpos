package com.firman.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Firman on 10/26/2016.
 */

public class Player implements Parcelable {

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    private final String mFirstName;
    private final String mLastInitial;
    private final Avatar mAvatar;

    public Player(String firstName, String lastInitial, Avatar avatar) {
        mFirstName = firstName;
        mLastInitial = lastInitial;
        mAvatar = avatar;
    }

    protected Player(Parcel in) {
        mFirstName = in.readString();
        mLastInitial = in.readString();
        mAvatar = Avatar.values()[in.readInt()];
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastInitial() {
        return mLastInitial;
    }

    public Avatar getAvatar() {
        return mAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeString(mLastInitial);
        dest.writeInt(mAvatar.ordinal());
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        if (mAvatar != player.mAvatar) {
            return false;
        }
        if (!mFirstName.equals(player.mFirstName)) {
            return false;
        }
        if (!mLastInitial.equals(player.mLastInitial)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mFirstName.hashCode();
        result = 31 * result + mLastInitial.hashCode();
        result = 31 * result + mAvatar.hashCode();
        return result;
    }
}
