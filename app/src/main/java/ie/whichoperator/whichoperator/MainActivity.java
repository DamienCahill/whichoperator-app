package ie.whichoperator.whichoperator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ArrayList<Question> questions;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    int currentScore;
    Question currentQuestion;
    TextView text;
    TextView score;
    TextView highScore;
    TextView timer;
    String HIGHSCORE="highScore";
    MyCountDownTimer counterDownTimer;
    boolean gameRunning = false;
    Timer myTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }
    public void setUp() {
        currentScore = 0;
        currentQuestion = getQuestion();
        text = findViewById(R.id.text);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        score = findViewById(R.id.score);
        timer = findViewById(R.id.timer);
        highScore = findViewById(R.id.highscore);
        highScore.setText("High Score :" +getHighScore());
        populateComponentText();
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(btn0.getText() + "");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(btn1.getText() + "");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(btn2.getText() + "");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(btn3.getText() + "");
            }
        });
    }

    public void answerQuestion(String btnText) {
        currentQuestion.setProvidedAnswer(btnText);
        if (currentQuestion.getAnsweredCorrectly()) {
            currentScore++;
            currentQuestion = getQuestion();
            populateComponentText();
        } else {
            counterDownTimer.addTime(-5000);
        }
        currentQuestion = getQuestion();
        populateComponentText();

        if (!gameRunning) {
            counterDownTimer = new MyCountDownTimer(100000, 1000, timer);
            counterDownTimer.start();
            gameRunning = true;

            myTimer =new Timer();
            myTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (gameRunning && timer.getText().equals("Times Up")) {
                                gameRunning=false;
                                gameOver(true);
                            }
                        }
                    });
                }
            }, 100, 1000);
        }
    }

    public void gameOver(boolean timeUp) {
        if (currentScore > getHighScore())
            saveScore(currentScore);
        myTimer.cancel();
        myTimer=null;
        counterDownTimer=null;
        String gameOverReason = timeUp ? "Times Up! " : "INCORRECT! ";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(gameOverReason + "Your total score was " + currentScore)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setUp();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void populateComponentText() {

        btn0.setText(currentQuestion.getAnswers().get(0));
        btn1.setText(currentQuestion.getAnswers().get(1));
        btn2.setText(currentQuestion.getAnswers().get(2));
        btn3.setText(currentQuestion.getAnswers().get(3));
        text.setText(currentQuestion.getText());
        score.setText("Current Score: " + currentScore);
        highScore.setText("High Score :" +getHighScore());
    }
    public Question getQuestion() {

        Random rand = new Random();
        int ranNum = rand.nextInt(3+1);
        String[] options = {"*", "/", "+", "-"};
        String answer = options[ranNum];
        String questionText = "";
        int num1,num2,num3;
        switch (ranNum) {
            case 0:
                num1 = rand.nextInt(0 + 40);
                num2 = rand.nextInt(0 + 40);
                num3 = num1 * num2;
                questionText = num1 + " ? " + num2 + " = " + num3;
                break;
            case 1:
                num1 = rand.nextInt(0 + 40);
                if (num1 % 3 == 0) {
                    num2 = 3;
                } else if (num1 % 5 == 0) {
                    num2 = 5;
                } else {
                    num2 = num1;
                }
                num3 = num1 / num2;
                questionText = num1 + " ? " + num2 + " = " + num3;
                break;

            case 2:
                num1 = rand.nextInt(0 + 40);
                num2 = rand.nextInt(0 + 40);
                num3 = num1 + num2;
                questionText = num1 + " ? " + num2 + " = " + num3;
                break;
            case 3:
                num1 = rand.nextInt(0 + 40);
                num2 = rand.nextInt(0 + 40);
                num3 = num1 - num2;
                questionText = num1 + " ? " + num2 + " = " + num3;
                break;
        }
        return new Question(questionText, options, answer);
    }

    public void saveScore(int score) {
        SharedPreferences mySharedPref = getSharedPreferences(HIGHSCORE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putInt("score", score);
        editor.apply();
    }
    public int getHighScore() {
        SharedPreferences mySharedPref = getSharedPreferences(HIGHSCORE, Context.MODE_PRIVATE);
        return mySharedPref.getInt("score", 0);
    }
}