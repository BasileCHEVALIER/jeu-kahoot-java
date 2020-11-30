package serveur;

public class DataPartie {

    private int idPartie; // numero unique donné par la BDD
    private int start;  // 0
    private int nbJoueurDansLaPartie=0; //
    private int nbMaxJoueur = 2 ;


    public DataPartie(int idPartie, int start, int nbMaxJoueur) {
        this.idPartie = idPartie;
        this.start = start;
        this.nbMaxJoueur = nbMaxJoueur ;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public int getNbJoueurDansLaPartie() {
        return nbJoueurDansLaPartie;
    }

    public int getNbMaxJoueur() {
        return nbMaxJoueur;
    }

    public void incrementerNbJoueurDansLaPartie(){
        this.nbJoueurDansLaPartie++;
    }

}
