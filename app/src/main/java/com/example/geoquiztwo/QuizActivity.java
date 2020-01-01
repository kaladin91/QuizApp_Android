package com.example.geoquiztwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private Button mCheatButton;

    private boolean noQuestions = false;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa,true),
            new Question(R.string.question_australia, true),
            new Question (R.string.question_oceans,true),
            new Question (R.string.question_mideast, false),
            new Question (R.string.question_americas,true),
            new Question (R.string.question_asia, true)

    };




    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private static final String  TAG = "QuizActivity";

    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CODE_CHEAT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle)called");
        setContentView(R.layout.activity_main);


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    checkAnswer(true);
                    mQuestionBank[mCurrentIndex].setAnswered(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setAnswered(true);

            }
        });



        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();



            }
        });


        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
              Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
              //startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);

            }
        });



        updateQuestion();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }

            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
    private void updateQuestion() {
        Log.d(TAG,"Updating question text",new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

    }


    public boolean questionRemaining() {

        int startNum = mCurrentIndex;
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        if (!mQuestionBank[mCurrentIndex].isAnswered()){
            return false;
        }
        else{
            startNum = (startNum + 1 )% mQuestionBank.length;

            while(startNum != mCurrentIndex){

                if (!mQuestionBank[startNum].isAnswered()){
                    mCurrentIndex = startNum;
                    return false;

                }
                startNum = (startNum + 1 % mQuestionBank.length);

            }

        }
        noQuestions = true;
        return true;

    }

    /*
    public void displayResults(){
        mNextButton.setVisibility(View.INVISIBLE);
        mTrueButton.setVisibility(View.INVISIBLE);
        mFalseButton.setVisibility(View.INVISIBLE);
        float count = 0;
        float total = mQuestionBank.length;
        for (int i = 0; i < mQuestionBank.length-1; i++){

            if (mQuestionBank[i].getResult()){
                count = count + 1;
            }


        }
       float percentage = (count/total) * 100;
        mQuestionTextView.setText(Float.toString(percentage).substring(0,5)+"%");
    }
    */



    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {

            messageResId = R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setResult(true);

            } else {
                messageResId = R.string.incorrect_toast;
                mQuestionBank[mCurrentIndex].setResult(false);
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Overriding Lifecycle methods

    public void onStart(){
        super.onStart();
        Log.d(TAG, "OnStart() called");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG,"OnResume() called");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG,"OnPause() called");
    }


    // Activity method is overridden so that data is saved during the layout change.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        //The saved state requires a key value pair.
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);


    }
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"OnDestroy() called");
    }

    public void onStop(){
        super.onStop();
        Log.d(TAG,"OnStop() called");
    }

}
