package ie.whichoperator.whichoperator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    //constants
    final private static String HIGH_SCORE="high_score";
    final private String HIGH_SCORE_SHARED_PREFERENCE ="high_Score_shared_preference";
    final private int COUNTDOWN_START = 60000; // 60000 -> 1 MINUTE

    //class attributes
    private int currentScore;
    private boolean running;
    private Question currentQuestion;
    private GameTimer gameTimer;
    private SharedPreferences highScorePreferences;
    private Random rand;

    //constructor
    public Game(SharedPreferences highScorePreferences, Random rand) {
        this.highScorePreferences = highScorePreferences;
        this.rand = rand;
        currentScore=0;
        running=false;
    }
    public void increaseScore() {
        this.currentScore++;
    }
    public int getCurrentScore() {
        return this.currentScore;
    }
    public boolean isRunning() {
        return this.running;
    }
    public void setRunningState(boolean running) {
        this.running=running;
    }
    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }
    public void setCurrentQuestion(Question q) {
        this.currentQuestion = q;
    }
    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public static void setNewHighScore(int highScore, SharedPreferences sharedPrefeeneces) {
        SharedPreferences.Editor editor = sharedPrefeeneces.edit();
        editor.putInt(HIGH_SCORE, highScore);
        editor.apply();
    }
    public static int getHighScore(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(HIGH_SCORE, 0);
    }

    @SuppressLint("NewApi")
    public void gameOver() {
        LeaderBoardClient client = new LeaderBoardClient(BuildConfig.USERNAME, BuildConfig.PASSWORD, BuildConfig.URL);
        new Thread(() -> {
            try {
                client.submitScore("unknown", this.getCurrentScore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        if (this.getCurrentScore() > Game.getHighScore( this.highScorePreferences))
            Game.setNewHighScore(this.getCurrentScore(), this.highScorePreferences);

        if (gameTimer != null)
            gameTimer=null;
    }
    public boolean answerQuestion(char answer) {
        this.getCurrentQuestion().setProvidedAnswer(answer);
        if (this.getCurrentQuestion().getAnsweredCorrectly()) { // correctly answered
            if (this.isRunning()) {
                gameTimer.addTime(1000);
            }
            this.increaseScore();
        } else if (!this.isRunning()){ // incorrectly answered first question
            // end the game with a score of 0!
            return false;
        } else { // incorrectly answered non first question
            //subtract 5 seconds from remaining time
            gameTimer.addTime(-5000);
        }
        //get the next question
        this.setCurrentQuestion(Game.getQuestion(this.rand.nextInt(40),this.rand.nextInt(40),this.rand.nextInt(3+1)));
        return true;
    }

    public static Question getQuestion(int num1, int num2, int ranNum) {

        char[] possibleAnswers = {'/', '*','-','+'};
        String questionText = "";
        int num3=0;
        ArrayList<Character> answers= new ArrayList<Character>();
        if (ranNum == 0) { // Division is a special case, numbers must whole ints
            if (num1 % 3 == 0) {
                num2 = 3;
            } else if (num1 % 5 == 0) {
                num2 = 5;
            } else {
                num2 = num1;
            }
            num3 = num1 / num2;
        } else { // multiplication, subtraction, addition

            if (ranNum == 1) { // multiplication
                num3 = num1*num2;
            } else if (ranNum == 2) { // subtraction
                num3 = num1-num2;
            } else if (ranNum == 3) { //addition
                num3 = num1+num2;
            }
        }
        //add the answer to possible answers
        answers.add(possibleAnswers[ranNum]);
        //check if any other answers work
        try {
            if (ranNum != 0 && (num3 == num1 / num2)) answers.add('/');
        } catch (ArithmeticException e) {
            System.out.println("Exception thrown while checking answer on division");
        }
        if (ranNum != 1 && (num3==num1*num2)) answers.add('*');
        if (ranNum != 2 && (num3==num1-num2)) answers.add('-');
        if (ranNum != 3 && (num3==num1+num2)) answers.add('+');

        questionText = num1 + " ? " + num2 + " = " + num3;

        //convert correct answers list to array
        char[] arrayOfAnswers = new char[answers.size()];
        for (int index=0;index<answers.size();index++) {
            arrayOfAnswers[index] = answers.get(index);
        }

        return new Question(questionText, arrayOfAnswers);
    }
}
