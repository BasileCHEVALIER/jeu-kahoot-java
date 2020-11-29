package client;

import data.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    private String expediteur;
    private String message;
    private String typeMessage;
    private List<Question> lesQuestions ;
    private int score=0;
    private int laQuestionCourante=0;
    private int leNombreDeQuestions=10;

    public Message(String expediteur, String message, String typeMessage) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
    }
    public Message(String expediteur, String message, String typeMessage,List lesQuestions) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
        this.lesQuestions=lesQuestions;
    }

    public Message(String expediteur, String message, String typeMessage,List lesQuestions,int score) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
        this.lesQuestions=lesQuestions;
        this.score=score;
    }


    public int getScore() {
        return score;
    }

    public List<Question> getLesQuestions() {
        return lesQuestions;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getMessage() {
        return message;
    }

    public void addQuestion(Question uneQuestion) {
        this.lesQuestions.add(uneQuestion);
    }

    public void setLesQuestions(List<Question> lesQuestions) {

        this.lesQuestions = lesQuestions;
    }


    @Override
    public String toString() {
        return "Message{" +
                "expediteur='" + expediteur + '\'' +
                ", message='" + message + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", lesQuestions=" + lesQuestions +
                ", score=" + score +
                ", laQuestionCourante=" + laQuestionCourante +
                ", leNombreDeQuestions=" + leNombreDeQuestions +
                '}';
    }
}
