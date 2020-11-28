package serveur;

import client.Message;
import data.Question;
import gestionBdd.RequeteKahoot;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class Serveur extends Thread {

    private static final int port=60000; // port
    private ServerSocket serverSocket; // La socket qui va ecouter
    private static List<Connexion> listConnexion; // La list des

    private static RequeteKahoot requeteKahoot ;
    private static List<DataPartie> listeDesPartiesEnCours;

    private static int nbJoueurMaxParPartie = 2 ;



    public Serveur() throws IOException, SQLException {


        this.serverSocket = new ServerSocket(port);
        listConnexion = new ArrayList<>();
        requeteKahoot = new RequeteKahoot();

        int idPartie=requeteKahoot.creationNouvellePartie();

        DataPartie dataPartie = new DataPartie(idPartie,0,nbJoueurMaxParPartie);
        listeDesPartiesEnCours = new ArrayList<>();
        listeDesPartiesEnCours.add(dataPartie);

        System.out.println("La liste des parties :"+listeDesPartiesEnCours);

        // Lance un timer qui va verifier les parties toutes les x secondes

        Timer timer;
        timer = new Timer();
        timer.schedule(new Service(), 20000, 20000);

    }


    /*
    * Fonction : DemanderDeParticipationAUnePartie(int idJoueur)
    * Objectif(s) : Un utilisateur demande à participer à une partie
    * Nous allons l'ajouter dans une partie dont la capacité est inférieur à la capacité maximale
    *
    * Si il n'y a pas de partie en cours nous allons en lancer une avec la fonction ajouterUnePartieALaListeDePartie
    *
    * Retour : La fonction retourne l'identifiant de la partie
    *
     * */
    public static int DemanderDeParticipationAUnePartie(int idJoueur) throws SQLException, IOException {

        int idPartie = -1 ;
        int i=0;
        int compteurPartiePleine=0;

        // Si le tableau de partie est vide
        if(listeDesPartiesEnCours.isEmpty()){
            ajouterUnePartieALaListeDePartie();
        }else {

            // Parcours de toutes les partie, il est possible d'avoir des parties que à 1 ou
            for(i=0;i<listeDesPartiesEnCours.size();i++) {

                DataPartie dataPartie = listeDesPartiesEnCours.get(i);

                if(dataPartie.getStart()==0 && dataPartie.getNbJoueurDansLaPartie()<nbJoueurMaxParPartie){
                    idPartie=dataPartie.getIdPartie();
                    System.out.println("SERVER : l'utilisateur "+idJoueur+" a été ajouté à la partie id="+idPartie);
                    requeteKahoot.addJoueurHasAParie(idJoueur,idPartie);
                    dataPartie.incrementerNbJoueurDansLaPartie();
                    System.out.println(dataPartie.toString());

                    // Voir pour verifier si la partie est pleine alors on va lancer le gamer
                    //lancerUnePartieSiElleEstPleine();

                    break;
                }else{
                    compteurPartiePleine++;
                }
            }if(compteurPartiePleine==listeDesPartiesEnCours.size()){

                System.out.println("SERVER : l'utilisateur +"+idJoueur+"+n'a pas été ajouté à une partie nous allons en créer une ");
                ajouterUnePartieALaListeDePartie();
                for(i=0;i<listeDesPartiesEnCours.size();i++) {

                    DataPartie dataPartie = listeDesPartiesEnCours.get(i);

                    if(dataPartie.getStart()==0 && dataPartie.getNbJoueurDansLaPartie()<nbJoueurMaxParPartie){
                        idPartie=dataPartie.getIdPartie();
                        System.out.println("SERVER : l'utilisateur "+idJoueur+" a été ajouté à la partie id="+idPartie);
                        requeteKahoot.addJoueurHasAParie(idJoueur,idPartie);
                        dataPartie.incrementerNbJoueurDansLaPartie();
                        System.out.println(dataPartie.toString());

                        // Voir pour verifier si la partie est pleine alors on va lancer le gamer
                        //lancerUnePartieSiElleEstPleine();

                        break;
                    }else{
                        compteurPartiePleine++;
                    }
                }
            }



        }
        return idPartie;
    }




    /*
    * Fonction : lancerUnePartieSiElleEstPleine()
    *
    * Objectif(s) : Iteration du le tableau des parties, si une partie est à sa capacité maximale
    * Nous allons mettre son attribut de partie start à 1.
    * Nous allons chercher aleatoirement 10 questions aléatoires dans la table question pour les lier à la partie
    * Enfin nous envoyons un message à l'emsemble des personnes de la partie
    * Nous faisions une list des parties pleines pour les supprimer du tableau
    *
    * Retour : Pas de retour
    * */
    public static void lancerUnePartieSiElleEstPleine() throws SQLException, IOException {

        for (int i=0;i<listeDesPartiesEnCours.size();i++){
            DataPartie dataPartie = listeDesPartiesEnCours.get(i);
            if(dataPartie.getNbJoueurDansLaPartie()== dataPartie.getNbMaxJoueur() && dataPartie.getStart()==0){

                // dataPartie partie vaut 1 dans le tableau comme ça on ne pourra pu ajouter de personne
                dataPartie.setStart(1);

                // Recuperer id de la partie
                int idPartie=dataPartie.getIdPartie();

                // Trouver des questions dans la BDD
                requeteKahoot.chargerLesQuestionDeLaPartie(idPartie);

                // Broadcast aux personnes le message comme quoi nous allons demarer la partie
                System.out.println("SERVER : Lancement de la partie "+idPartie);

                // Creation de l'objet message
                Message message = new Message("SERVER","Nous allons commencer la partie","startGame");

                // Creation de la list qui va contenir toutes les questions
                List <Question> lesQuestions = new ArrayList<>();

                // Recuperation du tableau avec les questions
                lesQuestions=requeteKahoot.getLesQuestionsDeLaPartie(idPartie);

                // Ajout des questions à l'objet message
                message.setLesQuestions(lesQuestions);

                // Envoi des messages aux utilisateurs de la partie
                for(int j=0;j<listConnexion.size();j++){
                    listConnexion.get(j).toString();
                    listConnexion.get(j).sendMessageStartGame(idPartie,message);
                }

            }
        }

    }





    public static void ajouterUnePartieALaListeDePartie() throws SQLException {

        int idPartie=requeteKahoot.creationNouvellePartie();
        DataPartie dataPartie = new DataPartie(idPartie,0,2);
        listeDesPartiesEnCours.add(dataPartie);
        System.out.println("SERVER : Creation nouvelle partie, voici la liste des parties :"+listeDesPartiesEnCours);
    }


    private void fermerSocketEcoute(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("SERVER :Erreur lors de la fermeture de socket d'écoute");
            e.printStackTrace();
        }
    }




    public static synchronized List<Connexion> getListConnexion() {
        return listConnexion;
    }

    @Override
    // Programme veilleur
    public void run() {
        try {
            while(true){

                /*
                * Creation d'un nouveau thread pour le nouveau client
                * Lancement de l'excution du thread
                *
                * Deroulement du serveur
                * 0. Lors du constru
                * 1. Etape de connexion entre le client et le serveur
                * 2. Attribuer une partie qui n'a pas commencé à l'utilisateur.
                *    Pour cela on a une list de partie
                * 3. Lorsqu'une personne est connecté login & password ou bien inscrite on va lui attribuer une partie
                * 4. Tant que la partie n'est pas pleine elle n'est pas lancé !
                *
                * */

                Connexion con = new Connexion(serverSocket.accept(),0);

                synchronized(listConnexion) {

                    listConnexion.add(con); // Ajouter l'utilisateur à la liste de connexion
                    con.start(); // Lancer le thread connexion pour intéragir avec l'utilisateur


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            fermerSocketEcoute();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {

        // Le nombre de joueur max par partie faire un constructeur Serveur par défaut !!!
        Serveur serv = new Serveur();
        serv.start();


    }
}
