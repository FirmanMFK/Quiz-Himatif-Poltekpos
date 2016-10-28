package com.firman.quiz.model.quiz;

import android.os.Parcel;

import com.firman.quiz.helper.AnswerHelper;

/**
 * Created by Firman on 10/26/2016.
 */

public final class SelectItemQuiz extends OptionsQuiz<String> {

    public SelectItemQuiz(String question, int[] answer, String[] options, boolean solved) {
        super(question, answer, options, solved);
    }

    @SuppressWarnings("Unused")
    public SelectItemQuiz(Parcel in) {
        super(in);
        String[] options = in.createStringArray();
        setOptions(options);
    }

    @Override
    public QuizType getType() {
        return QuizType.SINGLE_SELECT;
    }

    @Override
    public String getStringAnswer() {
        return AnswerHelper.getAnswer(getAnswer(), getOptions());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(getOptions());
    }
}
