package com.example.naina.newproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.android.NewProject.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.android.NewProject.answer_shown";

//    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
//        Intent intent = new Intent(packageContext, CheatActivity.class);
//        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
//        return intent;
//    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if (getIntent().getExtras() != null){
            mAnswerIsTrue = getIntent().getBooleanExtra("answer", false);
            Log.d("lol", "onCreate: "+getIntent().getExtras().getBoolean("answer"));
        }


        mShowAnswerButton=findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

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