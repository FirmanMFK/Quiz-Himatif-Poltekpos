package com.firman.quiz.model.quiz;

import android.os.Parcel;

import com.firman.quiz.helper.ParcelableHelper;

/**
 * Created by Firman on 10/26/2016.
 */

public final class TrueFalseQuiz extends Quiz<Boolean> {

    public TrueFalseQuiz(String question, Boolean answer, boolean solved) {
        super(question, answer, solved);
    }

    @SuppressWarnings("unused")
    public TrueFalseQuiz(Parcel in) {
        super(in);
        setAnswer(ParcelableHelper.readBoolean(in));
    }

    @Override
    public String getStringAnswer() {
        return getAnswer().toString();
    }

    @Override
    public QuizType getType() {
        return QuizType.TRUE_FALSE;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        ParcelableHelper.writeBoolean(dest, getAnswer());
    }
}
