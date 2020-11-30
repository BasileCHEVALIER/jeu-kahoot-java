package data;

import java.io.Serializable;

/*
* REPONSE = ID + TEXTE
 */
public class Reponse implements Serializable {

    private String texteReponse;
    private int idReponse;

    public Reponse(int idReponse, String texteReponse) {
        this.texteReponse = texteReponse;
        this.idReponse = idReponse;
    }

    public Reponse(String texteReponse) {
        this.texteReponse = texteReponse;
    }

    public String getTexteReponse() {
        return texteReponse;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "texteReponse='" + texteReponse + '\'' +
                ", idReponse=" + idReponse +
                '}';
    }
}
