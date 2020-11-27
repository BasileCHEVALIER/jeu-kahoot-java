package data;

public class Reponse {

    private String texteReponse;
    private int idReponse;

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

    public void setTexteReponse(String texteReponse) {
        this.texteReponse = texteReponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "texteReponse='" + texteReponse + '\'' +
                '}';
    }
}
