package com.example.naina.newproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton, mCheatButton;
    private Button mFalseButton, mNextButton, mPrevButton,showResultBtn;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "INDEX_INSTANCE";
    private static final int REQUEST_CODE_CHEAT =0;
    public static boolean mIsCheater;
    private int cheatCount= 0;
    private TextView countQuestion;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate(Bundle) called");
        mQuestionTextView = findViewById(R.id.question_text_view);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        int questionResID = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(questionResID);
        mTrueButton = findViewById(R.id.true_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);

        countQuestion=findViewById(R.id.count_question);
//        countQuestion.setText((mCurrentIndex+1)+"/"+ (mQuestionBank.length));
        setCountQuestion();

        showResultBtn = findViewById(R.id.show_result_btn);
        showResultBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int count=0;
                        for (Question q: mQuestionBank)
                        {
                            count = q.chosenCorrectAnswer?++count:count;

                        }
                        Toast.makeText(getApplicationContext(),"" +
                                "Your results are ["+count+"/"+mQuestionBank.length+"]",Toast.LENGTH_LONG).show();

                    }
                }
        );
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }


        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              calculateAnswer(true);
            }
        });


        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              calculateAnswer(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                setCountQuestion();
//                countQuestion.setText((mCurrentIndex+1)+"/"+ (mQuestionBank.length));

                cheatStatusCheck();
                if (mCurrentIndex == (mQuestionBank.length-1))
                {
                    showResultBtn.setVisibility(View.VISIBLE);
                }
                else showResultBtn.setVisibility(View.GONE);
                int question = mQuestionBank[mCurrentIndex].getmTextResId();
                mQuestionTextView.setText(question);
                if(mCurrentIndex==mQuestionBank.length-1){
                    mNextButton.setVisibility(View.GONE);


                }else{
                    mPrevButton.setVisibility(View.VISIBLE);
                }

//                mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getmTextResId());
//                Toast.makeText(QuizActivity.this, R.string.next_button, Toast.LENGTH_SHORT).show();
            }
        });
        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (mCurrentIndex - 1) < 0 ? 0 : mCurrentIndex - 1;
                        setCountQuestion();
//                        countQuestion.setText((mCurrentIndex+1)+"/"+ (mQuestionBank.length));

                        cheatStatusCheck();
                        int question = mQuestionBank[mCurrentIndex].getmTextResId();
                        mQuestionTextView.setText(question);
                        if(mCurrentIndex==0){
                           mPrevButton.setVisibility(View.GONE);

                        }else{
                            mNextButton.setVisibility(View.VISIBLE);
                       }
                        if (mCurrentIndex == (mQuestionBank.length-1))
                        {
                            showResultBtn.setVisibility(View.VISIBLE);
                        }
                        else showResultBtn.setVisibility(View.GONE);

//                        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getmTextResId());
//                        mPrevButton.setVisibility(View.GONE);
//                        Toast.makeText(QuizActivity.this, R.string.prev_button,Toast.LENGTH_SHORT).show();

                    }
                }
        );

        mCheatButton=findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
//                Log.d("lol", "onClick: "+answerIsTrue);
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//               intent.putExtra("answer", answerIsTrue);
                intent.putExtra("cheatcount", cheatCount);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

              //  startActivity(intent);

            }


        });
//        boolean userHasCheated= true;

//        Log.d("TAG",userHasCheated  +"");


    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }

            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].userHasCheated = mIsCheater;
            cheatStatusCheck();
            cheatCount=mIsCheater?++cheatCount:cheatCount;
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId =0;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(QuizActivity.this, "" + getResources().getString(messageResId), Toast.LENGTH_SHORT).show();


    }

    private void calculateAnswer(boolean chosenAnswer)
    {
        if (mQuestionBank[mCurrentIndex].isAnswered) {

            Toast.makeText(QuizActivity.this, R.string.cannot_press, Toast.LENGTH_SHORT).show();

        } else {
            mQuestionBank[mCurrentIndex].isAnswered = true;
            mQuestionBank[mCurrentIndex].chosenCorrectAnswer= chosenAnswer==mQuestionBank[mCurrentIndex].ismAnswerTrue();
            checkAnswer(chosenAnswer);

        }

    }
    private void cheatStatusCheck(){
        mIsCheater = mQuestionBank[mCurrentIndex].userHasCheated;
        Log.d(TAG, "cheatStatusCheck: "+mIsCheater);
        if (mIsCheater){
            mCheatButton.setVisibility(View.GONE);
        }else{
            mCheatButton.setVisibility(View.VISIBLE);
        }

    }
    private void setCountQuestion(){
        countQuestion.setText((mCurrentIndex+1) + "/" + (mQuestionBank.length));
    }


}

