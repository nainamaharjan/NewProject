package com.example.naina.newproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private int cheat_count=0;

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.android.NewProject.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.android.NewProject.answer_shown";
    private TextView labelTV;

    private Question question;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public void manageCheatCount(){
        if(cheat_count>=3){
            mShowAnswerButton.setVisibility(View.GONE);
            labelTV.setText("You have already used all your cheat tokens. Try again next time.");
        }/*else {
            mShowAnswerButton.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if (getIntent().getExtras() != null){
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
            cheat_count= getIntent().getIntExtra("cheatcount",0);
////            Log.d("check", "onCreate: "+getIntent().getExtras().getBoolean("answer"));
        }
        mShowAnswerButton=findViewById(R.id.show_answer_button);
        labelTV = findViewById(R.id.label);
        manageCheatCount();


        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    ++cheat_count;
//                    Log.d("haha", cheat_count +"");
                    if (mAnswerIsTrue) {

                        mAnswerTextView.setText(R.string.true_button);
                    } else {
                        mAnswerTextView.setText(R.string.false_button);
                    }

                setAnswerShownResult(true);
                mShowAnswerButton.setVisibility(View.GONE);
/*
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();

                }else{
                    mShowAnswerButton.setVisibility(View.INVISIBLE);

                }*/
            }

        });




        mAnswerTextView=findViewById(R.id.answer_text_view);

    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }





}
