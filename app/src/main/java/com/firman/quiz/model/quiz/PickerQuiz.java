package com.firman.quiz.model.quiz;

import android.os.Parcel;

/**
 * Created by Firman on 10/26/2016.
 */

public final class PickerQuiz extends Quiz<Integer> {

    private final int mMin;
    private final int mMax;
    private final int mStep;

    public PickerQuiz(String question, Integer answer, int min, int max, int step, boolean solved) {
        super(question, answer, solved);
        mMin = min;
        mMax = max;
        mStep = step;
    }

    public PickerQuiz(Parcel in) {
        super(in);
        setAnswer(in.readInt());
        mMin = in.readInt();
        mMax = in.readInt();
        mStep = in.readInt();
    }

    public int getMin() {
        return mMin;
    }

    public int getMax() {
        return mMax;
    }

    public int getStep() {
        return mStep;
    }

    @Override
    public QuizType getType() {
        return QuizType.PICKER;
    }

    @Override
    public String getStringAnswer() {
        return getAnswer().toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(getAnswer());
        dest.writeInt(mMin);
        dest.writeInt(mMax);
        dest.writeInt(mStep);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PickerQuiz)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PickerQuiz that = (PickerQuiz) o;

        if (mMin != that.mMin) {
            return false;
        }
        if (mMax != that.mMax) {
            return false;
        }
        return mStep == that.mStep;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mMin;
        result = 31 * result + mMax;
        result = 31 * result + mStep;
        return result;
    }
}
