package client;

import data.Question;

import java.io.Serializable;

public class Message implements Serializable {

    private String expediteur;
    private String message;
    private String typeMessage;

    public String getTypeMessage() {
        return typeMessage;
    }

    private int etape;

    private Question question ;

    public Message(String expediteur, String message) {
        this.expediteur = expediteur;
        this.message = message;
    }

    public Message(String expediteur, String message, String typeMessage) {
        this.expediteur = expediteur;
        this.message = message;
        this.typeMessage = typeMessage;
    }



    public int getEtape() {
        return etape;
    }

    public void setEtape(int etape) {
        this.etape = etape;
    }

    public void incSetEtape() {
        this.etape = this.etape+1;
    }


    public String getExpediteur() {
        return expediteur;
    }

    public String getMessage() {
        return message;
    }

}
