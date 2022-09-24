package easy.tuto.myquizapplication;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView QuestionsNoTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionsNoTextView = findViewById(R.id.question_no);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;

        switch (clickedButton.getId()){
            case R.id.ans_A:
                ansA.setBackgroundColor(Color.DKGRAY);
                ansA.setTextColor(Color.WHITE);
                ansB.setBackgroundColor(Color.WHITE);
                ansB.setTextColor(Color.BLACK);
                ansC.setBackgroundColor(Color.WHITE);
                ansC.setTextColor(Color.BLACK);
                ansD.setBackgroundColor(Color.WHITE);
                ansD.setTextColor(Color.BLACK);
                break;

            case R.id.ans_B:
                ansB.setBackgroundColor(Color.DKGRAY);
                ansB.setTextColor(Color.WHITE);
                ansA.setBackgroundColor(Color.WHITE);
                ansA.setTextColor(Color.BLACK);
                ansC.setBackgroundColor(Color.WHITE);
                ansC.setTextColor(Color.BLACK);
                ansD.setBackgroundColor(Color.WHITE);
                ansD.setTextColor(Color.BLACK);
                break;

            case R.id.ans_C:
                ansC.setBackgroundColor(Color.DKGRAY);
                ansC.setTextColor(Color.WHITE);
                ansB.setBackgroundColor(Color.WHITE);
                ansB.setTextColor(Color.BLACK);
                ansA.setBackgroundColor(Color.WHITE);
                ansA.setTextColor(Color.BLACK);
                ansD.setBackgroundColor(Color.WHITE);
                ansD.setTextColor(Color.BLACK);
                break;

            case R.id.ans_D:
                ansD.setBackgroundColor(Color.DKGRAY);
                ansD.setTextColor(Color.WHITE);
                ansB.setBackgroundColor(Color.WHITE);
                ansB.setTextColor(Color.BLACK);
                ansC.setBackgroundColor(Color.WHITE);
                ansC.setTextColor(Color.BLACK);
                ansA.setBackgroundColor(Color.WHITE);
                ansA.setTextColor(Color.BLACK);
                break;
        }


        if (clickedButton.getId() != R.id.submit_btn) {

            selectedAnswer = clickedButton.getText().toString();
        } else{
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
                clickedButton.setBackgroundColor(Color.parseColor("#006400"));
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                clickedButton.setBackgroundColor(Color.parseColor("#8b0000"));
                //Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

            ansA.setBackgroundColor(Color.WHITE);
            ansA.setTextColor(Color.BLACK);
            ansB.setBackgroundColor(Color.WHITE);
            ansB.setTextColor(Color.BLACK);
            ansC.setBackgroundColor(Color.WHITE);
            ansC.setTextColor(Color.BLACK);
            ansD.setBackgroundColor(Color.WHITE);
            ansD.setTextColor(Color.BLACK);

            progressBar = findViewById(R.id.progress_bar);

            final int[] i = {0};

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // set the limitations for the numeric
                    // text under the progress bar
                    if (i[0] <= 100) {
                        progressBar.setProgress(i[0]);
                        i[0]++;
                        handler.postDelayed(this, 15);
                    } else {
                        handler.removeCallbacks(this);
                    }
                }
            },200 );

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    clickedButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    currentQuestionIndex++;
                    loadNewQuestion();
                }
            };
            Handler handler2 = new Handler(Looper.getMainLooper());
            handler2.postDelayed(runnable, 2000);
        }


    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }



        QuestionsNoTextView.setText("Question : " + (currentQuestionIndex+1) + "/" + totalQuestion);

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setNegativeButton("Exit" ,((dialogInterface, i) -> System.exit(1)))
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz() )

                //  .setCancelable(false)
                .show();


    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }

}