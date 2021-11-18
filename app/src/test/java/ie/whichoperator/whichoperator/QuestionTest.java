package ie.whichoperator.whichoperator;

import junit.framework.Assert.*;
import junit.framework.TestCase;
import junit.framework.TestResult;

import ie.whichoperator.whichoperator.Question;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class QuestionTest extends TestCase {
    /*
      0 = /
      1 = *
      2 = -
      3 = +
     */
    @Test
    public void testMultiplyQuestionMultipleAnswers() {
        int num1=2;
        int num2=2;
        int num3=1;
        char[] expectedAnswers = new char[]{'*','+'};
        String expectedQuestionText = "2 ? 2 = 4";
        Question expectedQ = new Question(expectedQuestionText,expectedAnswers);
        Question actualQ = Question.getQuestion(num1,num2,num3);
        assertEquals(expectedQuestionText, actualQ.getText());
        assertArrayEquals("Potential Answers Arrays" , expectedQ.getCorrectAnswers(), actualQ.getCorrectAnswers());
    }

    @Test
    public void testMultiplyQuestionWithOneAnswer() {
        int num1=6;
        int num2=2;
        int num3=1;
        char[] expectedAnswers = new char[]{'*'};
        String expectedQuestionText = "6 ? 2 = 12";
        Question expectedQ = new Question(expectedQuestionText,expectedAnswers);
        Question actualQ = Question.getQuestion(num1,num2,num3);
        assertEquals(expectedQuestionText, actualQ.getText());
        assertArrayEquals("Potential Answers Arrays" , expectedQ.getCorrectAnswers(), actualQ.getCorrectAnswers());
    }

    @Test
    public void testDivideQuestionWithNumDivisibleBy3() {
        int num1=12;
        int num2=5;
        int num3=0;
        char[] expectedAnswers = new char[]{'/'};
        String expectedQuestionText = "12 ? 3 = 4";
        Question expectedQ = new Question(expectedQuestionText,expectedAnswers);
        Question actualQ = Question.getQuestion(num1,num2,num3);
        assertEquals(expectedQuestionText, actualQ.getText());
        assertArrayEquals("Potential Answers Arrays" , expectedQ.getCorrectAnswers(), actualQ.getCorrectAnswers());
    }

    @Test
    public void testDivideQuestionWithNumDivisibleBy5() {
        int num1=25;
        int num2=6;
        int num3=0;
        char[] expectedAnswers = new char[]{'/'};
        String expectedQuestionText = "25 ? 5 = 5";
        Question expectedQ = new Question(expectedQuestionText,expectedAnswers);
        Question actualQ = Question.getQuestion(num1,num2,num3);
        assertEquals(expectedQuestionText, actualQ.getText());
        assertArrayEquals("Potential Answers Arrays" , expectedQ.getCorrectAnswers(), actualQ.getCorrectAnswers());
    }
    @Test
    public void testDivideWithNumNotDivisibleBy3Or5() {
        int num1=11;
        int num2=5;
        int num3=0;
        char[] expectedAnswers = new char[]{'/'};
        String expectedQuestionText = "11 ? 11 = 1";
        Question expectedQ = new Question(expectedQuestionText,expectedAnswers);
        Question actualQ = Question.getQuestion(num1,num2,num3);
        assertEquals(expectedQuestionText, actualQ.getText());
        assertArrayEquals("Potential Answers Arrays" , expectedQ.getCorrectAnswers(), actualQ.getCorrectAnswers());
    }

    @Test
    public void TestProvideIncorrectAnswer() {
        int num1=12;
        int num2=6;
        int num3=0;
        char[] answers = new char[]{'/'};
        String questionText = "12 ? 6 = 2";
        Question q = Question.getQuestion(num1,num2,num3);
        q.setProvidedAnswer('*');
        assertEquals(false, q.getAnsweredCorrectly());
    }

    @Test
    public void TestProvideCorrectAnswer() {
        int num1=12;
        int num2=6;
        int num3=0;
        char[] answers = new char[]{'/'};
        String questionText = "12 ? 6 = 2";
        Question q = Question.getQuestion(num1,num2,num3);
        q.setProvidedAnswer('/');
        assertEquals(true, q.getAnsweredCorrectly());
    }
}
