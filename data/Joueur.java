package data;

/*
* JOUEUR = login + mot de passe
 */
public class Joueur {
    private String login;
    private String mdp;

    public Joueur(String login, String mdp) {
        this.login = login;
        this.mdp=mdp;
    }
}
