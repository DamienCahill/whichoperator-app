package ie.whichoperator.whichoperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Question {
    private String text;
    private ArrayList<String> answers;
    private String answer;
    private String providedAnswer;
    private Boolean answeredCorrectly;

    public Question(String text, String[] answerOptions, String answer) {
        this.text = text;
        this.answers = new ArrayList<String>(Arrays.asList(answerOptions));
        this.answer = answer;

        //shuffle the answer options
        Collections.shuffle(this.answers);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getProvidedAnswer() {
        return providedAnswer;
    }

    public void setProvidedAnswer(String providedAnswer) {
        this.providedAnswer = providedAnswer;
        setAnsweredCorrectly(this.providedAnswer.equals(this.answer));
    }

    public Boolean getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(Boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }
}
