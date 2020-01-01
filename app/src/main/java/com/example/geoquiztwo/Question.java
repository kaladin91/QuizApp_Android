package com.example.geoquiztwo;


public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;

    private boolean mResult = false;

    public boolean getResult() {
        return mResult;
    }

    public void setResult(boolean result) {
        mResult = result;
    }




    public Question(int textResId, boolean answerTrue){

        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(boolean answered) {
        mAnswered = answered;
    }
}


