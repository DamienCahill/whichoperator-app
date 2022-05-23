package ie.whichoperator.whichoperator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Question {

    private String text;
    private ArrayList<Character> answerOptions;
    private char[] correctAnswers;
    private char providedAnswer;
    private boolean answeredCorrectly;

    public Question(String text, char[] answers) {
        this.answeredCorrectly=false;
        this.text = text;
        this.correctAnswers = answers;
        this.answerOptions = new ArrayList<Character>();
        answerOptions.add('+');
        answerOptions.add('-');
        answerOptions.add('/');
        answerOptions.add('*');

        //shuffle the answer options
        Collections.shuffle(this.answerOptions);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Character> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(ArrayList<Character> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public char[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswersAnswer(char[] answers) {
        this.correctAnswers = answers;
    }

    public char getProvidedAnswer() {
        return providedAnswer;
    }

    public void setProvidedAnswer(char providedAnswer) {
        this.providedAnswer = providedAnswer;
        for (char c : correctAnswers) {
            if (c == providedAnswer) {
                setAnsweredCorrectly(true);
                break;
            }
        }
    }

    public boolean getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(Boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

}
