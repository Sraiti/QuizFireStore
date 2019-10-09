package com.example.firestorequiz.Model;

public class Question {

    String Question ;
    String Answer0,Answer1,Answer2,Answer3;
    String CorrectAnswer;



    public Question( String question, String answer0, String answer1, String answer2, String answer3, String correctAnswer) {

        Question = question;
        Answer0 = answer0;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        CorrectAnswer = correctAnswer;
    }


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer0() {
        return Answer0;
    }

    public void setAnswer0(String answer0) {
        Answer0 = answer0;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public Question() {
    }
}
