package com.firman.quiz.widget.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firman.quiz.model.Category;
import com.firman.quiz.model.quiz.FillTwoBlanksQuiz;

/**
 * Created by Firman on 10/27/2016.
 */

@SuppressLint("ViewConstructor")
public class FillTwoBlanksQuizView extends TextInputQuizView<FillTwoBlanksQuiz> {

    private static final String KEY_ANSWER_ONE = "ANSWER_ONE";
    private static final String KEY_ANSWER_TWO = "ANSWER_TWO";
    private static final LinearLayout.LayoutParams CHILD_LAYOUT_PARAMS
            = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
    private EditText mAnswerOne;
    private EditText mAnswerTwo;

    public FillTwoBlanksQuizView(Context context, Category category, FillTwoBlanksQuiz quiz) {
        super(context, category, quiz);
    }

    @Override
    protected View createQuizContentView() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        mAnswerOne = createEditText();
        mAnswerOne.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mAnswerTwo = createEditText();
        addEditText(layout, mAnswerOne);
        addEditText(layout, mAnswerTwo);
        return layout;
    }

    @Override
    public Bundle getUserInput() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ANSWER_ONE, mAnswerOne.getText().toString());
        bundle.putString(KEY_ANSWER_TWO, mAnswerTwo.getText().toString());
        return bundle;
    }

    @Override
    public void setUserInput(Bundle savedInput) {
        if (savedInput == null) {
            return;
        }
        mAnswerOne.setText(savedInput.getString(KEY_ANSWER_ONE));
        mAnswerTwo.setText(savedInput.getString(KEY_ANSWER_TWO));
    }

    private void addEditText(LinearLayout layout, EditText editText) {
        layout.addView(editText, CHILD_LAYOUT_PARAMS);
    }

    @Override
    protected boolean isAnswerCorrect() {
        String partOne = getAnswerFrom(mAnswerOne);
        String partTwo = getAnswerFrom(mAnswerTwo);
        return getQuiz().isAnswerCorrect(new String[]{partOne, partTwo});
    }

    private String getAnswerFrom(EditText view) {
        return view.getText().toString();
    }
}

