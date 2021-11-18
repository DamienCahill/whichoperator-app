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
    private Boolean answeredCorrectly;

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

    public Boolean getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(Boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
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
