package com.firman.quiz.widget.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.firman.quiz.R;
import com.firman.quiz.adapter.OptionsQuizAdapter;
import com.firman.quiz.helper.AnswerHelper;
import com.firman.quiz.model.Category;
import com.firman.quiz.model.quiz.SelectItemQuiz;

/**
 * Created by Firman on 10/27/2016.
 */

@SuppressLint("ViewConstructor")
public class SelectItemQuizView extends AbsQuizView<SelectItemQuiz> {

    private static final String KEY_ANSWERS = "ANSWERS";

    private boolean[] mAnswers;
    private ListView mListView;

    public SelectItemQuizView(Context context, Category category, SelectItemQuiz quiz) {
        super(context, category, quiz);
        mAnswers = getAnswers();
    }

    @Override
    protected View createQuizContentView() {
        mListView = new ListView(getContext());
        mListView.setDivider(null);
        mListView.setSelector(R.drawable.selector_button);
        mListView.setAdapter(
                new OptionsQuizAdapter(getQuiz().getOptions(), R.layout.item_answer_start,
                        getContext(), true));
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allowAnswer();
                toggleAnswerFor(position);
            }
        });
        return mListView;
    }

    @Override
    protected boolean isAnswerCorrect() {
        final SparseBooleanArray checkedItemPositions = mListView.getCheckedItemPositions();
        final int[] answer = getQuiz().getAnswer();
        return AnswerHelper.isAnswerCorrect(checkedItemPositions, answer);
    }

    @Override
    public Bundle getUserInput() {
        Bundle bundle = new Bundle();
        bundle.putBooleanArray(KEY_ANSWERS, mAnswers);
        return bundle;
    }

    @Override
    public void setUserInput(Bundle savedInput) {
        if (savedInput == null) {
            return;
        }
        mAnswers = savedInput.getBooleanArray(KEY_ANSWERS);
        if (mAnswers == null) {
            return;
        }
        final ListAdapter adapter = mListView.getAdapter();
        for (int i = 0; i < mAnswers.length; i++) {
            mListView.performItemClick(mListView.getChildAt(i), i, adapter.getItemId(i));
        }
    }

    private void toggleAnswerFor(int answerId) {
        getAnswers()[answerId] = !mAnswers[answerId];
    }

    private boolean[] getAnswers() {
        if (null == mAnswers) {
            mAnswers = new boolean[getQuiz().getOptions().length];
        }
        return mAnswers;
    }
}
