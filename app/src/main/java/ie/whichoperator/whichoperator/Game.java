package ie.whichoperator.whichoperator;

import android.content.Context;
import android.content.SharedPreferences;

public class Game {

    //constants
    final private static String HIGH_SCORE="high_score";

    //class attributes
    private int currentScore;
    private boolean running;
    private Question currentQuestion;

    //constructor
    public Game() {
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
    public static void setNewHighScore(int highScore, SharedPreferences sharedPrefeeneces) {
        SharedPreferences.Editor editor = sharedPrefeeneces.edit();
        editor.putInt(HIGH_SCORE, highScore);
        editor.apply();
    }
    public static int getHighScore(SharedPreferences sharedPreferences) {

        return sharedPreferences.getInt(HIGH_SCORE, 0);
    }
}
