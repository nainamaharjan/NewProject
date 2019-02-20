package com.example.naina.newproject;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    public boolean isAnswered,chosenCorrectAnswer;


    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getmTextResId() {
        return mTextResId;
    }

    public void setmTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isChosenCorrectAnswer() {
        return chosenCorrectAnswer;
    }

    public void setChosenCorrectAnswer(boolean chosenCorrectAnswer) {
        this.chosenCorrectAnswer = chosenCorrectAnswer;
    }
}