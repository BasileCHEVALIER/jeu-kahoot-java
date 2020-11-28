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

    public Message(String expediteur, String message, String typeMessage) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
        this.lesQuestions=new ArrayList<>();
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
}
