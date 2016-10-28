package com.firman.quiz.model.quiz;

import android.os.Parcel;

import com.firman.quiz.helper.AnswerHelper;

import java.util.Arrays;

/**
 * Created by Firman on 10/26/2016.
 */

public class FillTwoBlanksQuiz extends Quiz<String[]> {

    public FillTwoBlanksQuiz(String question, String[] answer, boolean solved) {
        super(question, answer, solved);
    }

    @SuppressWarnings("unused")
    public FillTwoBlanksQuiz(Parcel in) {
        super(in);
        String answer[] = in.createStringArray();
        setAnswer(answer);
    }

    @Override
    public QuizType getType() {
        return QuizType.FILL_TWO_BLANKS;
    }

    @Override
    public String getStringAnswer() {
        return AnswerHelper.getAnswer(getAnswer());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(getAnswer());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FillTwoBlanksQuiz)) {
            return false;
        }

        FillTwoBlanksQuiz quiz = (FillTwoBlanksQuiz) o;
        final String[] answer = getAnswer();
        final String question = getQuestion();
        if (answer != null ? !Arrays.equals(answer, quiz.getAnswer()) : quiz.getAnswer() != null) {
            return false;
        }
        if (!question.equals(quiz.getQuestion())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(getAnswer());
        return result;
    }

}
