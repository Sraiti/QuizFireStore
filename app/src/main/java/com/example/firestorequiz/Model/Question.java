package com.example.firestorequiz.Model;

public class Question {

    String Question;
    String Answer0, Answer01, Answer02, Answer03;
    String CorrectAnswer;


    int CategoryID, Stage;

    public Question(String question, String answer0, String answer01, String answer02, String answer03, String correctAnswer, int categoryID, int stage) {
        Question = question;
        Answer0 = answer0;
        Answer01 = answer01;
        Answer02 = answer02;
        Answer03 = answer03;
        CorrectAnswer = correctAnswer;
        CategoryID = categoryID;
        Stage = stage;
    }

    public Question() {
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

    public String getAnswer01() {
        return Answer01;
    }

    public void setAnswer01(String answer01) {
        Answer01 = answer01;
    }

    public String getAnswer02() {
        return Answer02;
    }

    public void setAnswer02(String answer02) {
        Answer02 = answer02;
    }

    public String getAnswer03() {
        return Answer03;
    }

    public void setAnswer03(String answer03) {
        Answer03 = answer03;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getStage() {
        return Stage;
    }

    public void setStage(int stage) {
        Stage = stage;
    }
}
