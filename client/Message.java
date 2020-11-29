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

    private int laQuestionCourante=0;

    private int leNombreDeQuestions=10;

    public Message(String expediteur, String message, String typeMessage) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
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

    public int getLaQuestionCourante() {
        return laQuestionCourante;
    }

    public void setLaQuestionCourante(int laQuestionCourante) {
        this.laQuestionCourante = laQuestionCourante;
    }

    public int getLeNombreDeQuestions() {
        return leNombreDeQuestions;
    }

    public void setLeNombreDeQuestions(int leNombreDeQuestions) {
        this.leNombreDeQuestions = leNombreDeQuestions;
    }

    @Override
    public String toString() {
        return "Message{" +
                "expediteur='" + expediteur + '\'' +
                ", message='" + message + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", lesQuestions=" + lesQuestions +
                ", laQuestionCourante=" + laQuestionCourante +
                ", leNombreDeQuestions=" + leNombreDeQuestions +
                '}';
    }
}
