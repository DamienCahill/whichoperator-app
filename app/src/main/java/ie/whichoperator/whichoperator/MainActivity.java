package ie.whichoperator.whichoperator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final private String SHARED_PREFERENCE ="high_Score_shared_preference";
    final private int COUNTDOWN_START = 60000; // 60000 -> 1 MINUTE

    Game game;

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btnLeaderboard;

    TextView text;
    TextView score;
    TextView highScore;
    TextView timerText;
    Timer timer;

    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setContentView(R.layout.activity_main);
        setUp();
    }
    public void setUp() {
        game = new Game(getSharedPreferences(SHARED_PREFERENCE,Context.MODE_PRIVATE), new Random());
        game.setCurrentQuestion(Game.getQuestion(rand.nextInt(40),rand.nextInt(40),rand.nextInt(3+1)));

        //Initialise UI Components and set the text on them
        text = findViewById(R.id.text);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnLeaderboard = findViewById(R.id.leaderboardBtn);
        score = findViewById(R.id.score);
        timerText = findViewById(R.id.timer);
        highScore = findViewById(R.id.highscore);
        highScore.setText("High Score :" + Game.getHighScore(getSharedPreferences(SHARED_PREFERENCE,Context.MODE_PRIVATE)));
        populateComponentText();

        btn0.setEnabled(true);
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);

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

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LeaderboardActivity.class));
            }
        });
    }


    public void answerQuestion(String btnText) {

        if (!game.answerQuestion(btnText.charAt(0)) ){
            gameOver(false);
        } else {
            if (!game.isRunning()) {
                //start a new countdown timer
                game.setGameTimer(new GameTimer(COUNTDOWN_START, 1000, timerText));
                game.getGameTimer().start();

                //set the state of the game to be running
                game.setRunningState(true);

                //start a timer schedule to check if time has run out
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (game.isRunning() && timerText.getText().equals("Times Up")) { // end the game
                                    game.setRunningState(false);
                                    gameOver(true);
                                }
                            }
                        });
                    }
                }, 100, 1000);
            }
            populateComponentText();
        }
    }


    @SuppressLint("NewApi")
    public void gameOver(boolean timeUp) {
        btn0.setEnabled(false);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        game.gameOver();

        // set the appropriate ending message
        String gameOverReason = timeUp ? "Times Up! " : "INCORRECT! ";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(gameOverReason + "Your total score was " + game.getCurrentScore())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //set a new game up
                        setUp();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void populateComponentText() {

        btn0.setText(game.getCurrentQuestion().getAnswerOptions().get(0) +"");
        btn1.setText(game.getCurrentQuestion().getAnswerOptions().get(1)+"");
        btn2.setText(game.getCurrentQuestion().getAnswerOptions().get(2)+"");
        btn3.setText(game.getCurrentQuestion().getAnswerOptions().get(3)+"");
        text.setText(game.getCurrentQuestion().getText());
        score.setText("Current Score: " + game.getCurrentScore());
    }

}