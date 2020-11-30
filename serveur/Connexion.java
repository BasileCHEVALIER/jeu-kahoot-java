package serveur;

import client.Message;
import gestionBdd.RequeteKahoot;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import static serveur.Serveur.DemanderDeParticipationAUnePartie;

public class Connexion extends Thread {

    Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos ;

    private int idPartie; // ID de la parie
    private int iD; // ID de la connexion
    private int idJoueur; // ID du joueur

    public ObjectInputStream getOis() {
        return ois;
    }
    public ObjectOutputStream getOos() {
        return oos;
    }

    private int score = 0;

    @Override
    public void run() {
        while (true){
            try {
                Object line = ois.readObject();
                if (line!=null) {

                    //distriuberMessage(line);
                    System.out.println("---RUN SERVER---");

                    // Cast de notre objet
                    Message message = (Message)line;
                    System.out.println("MESSAGE : "+message.getMessage());
                    System.out.println("TYPE MESSAGE : "+message.getTypeMessage());
                    System.out.println("EXPEDITEUR : "+message.getExpediteur());

                    /*
                     * Part : Inscription
                     * RequeteKahoot allows connection with database
                     * addJoueur insert informations in database
                     * addJoueur return idJoueur
                     *
                     * parameters required :
                     * login type String ==> expediteur
                     * password type String ==> message
                     *
                     *
                     * Warning :
                     * If you login is already used. Server send you a message  !!
                     * idJoueur woth -1 in that case !
                     *

                     * Add check retourAddJoueurHasAParie if we have 0 all is good
                     * but if we have -1 add joueur isn't good
                     *
                     * */
                    if(message.getTypeMessage().compareTo("INSCRIPTION")==0){
                        try {
                            RequeteKahoot requeteKahoot = new RequeteKahoot();

                            int idJoueur = requeteKahoot.addJoueur(message.getExpediteur(),message.getMessage());

                            if(idJoueur==-1){
                                Message messageRetour = new Message("server","Ton pseudo est déjà utilisé","ERROR");
                                this.oos.writeObject(messageRetour);
                            }else{
                                this.idJoueur=idJoueur;
                                this.idPartie=DemanderDeParticipationAUnePartie(idJoueur);
                                Message messageRetour = new Message("server","Tu es inscris, en attente des autres joueurs","INSCRIPTION_PARTIE_GOOD");
                                this.oos.writeObject(messageRetour);

                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }



                    /*
                    * Part : Question
                    * L'utilisateur recoit toutes les questions dans l'objet message
                    * On lui pose la question index 0,
                    * Il demande la nouvelle question, donc on supprime l'ancienne à l'index 0
                    *
                    *
                    * */
                    if(message.getTypeMessage().compareTo("QUESTION")==0){


                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // On va retirer les questions de la liste au fur et a mesure
                        int NbQuestion=message.getLesQuestions().size();

                        if(NbQuestion>1){
                            // Afficher le message
                            System.out.println(message.toString());

                            // On retire la question qui vient d'être posé
                            message.getLesQuestions().remove(0);
                            System.out.println(message.getLesQuestions().toString());

                            this.score=message.getScore();

                            // Creation du nouveau message
                            Message messageRetour = new Message("SERVER","Nous allons commencer la partie ","QUESTION");

                            // Dans le message de retour on maj les question ( pour enlever celle qui est posé la 0)
                            messageRetour.setLesQuestions(message.getLesQuestions());

                            messageRetour.setScore(this.score);
                            this.oos.writeObject(messageRetour);

                        }else {
                            // Renvoyer un message au client avec les questions avec la question en moins
                            Message messageRetour = new Message("server","La fin de la partie ! ","FINPARTIE");
                            this.score=message.getScore();
                            messageRetour.setScore(this.score);
                            this.oos.writeObject(messageRetour);
                        }

                    }




                    /*
                     * Part : Se connecter
                     * RequeteKahoot allows connection with database
                     * seConnecter check informations in database if user have an account
                     * seConnecter return idJoueur
                     *
                     * parameters required :
                     * login type String ==> expediteur
                     * password type String ==> message
                     *
                     * Warning :
                     * If you login or password are false. Server send you a message  !!
                     * idJoueur woth -1 in that case !
                     * */
                    if(message.getTypeMessage().compareTo("SECONNECTER")==0){
                        try {
                            RequeteKahoot requeteKahoot = new RequeteKahoot();
                            // Warning specific
                            int idJoueur = requeteKahoot.seConnecter(message.getExpediteur(),message.getMessage());
                            System.out.println("ID du nouveau joueur "+idJoueur);

                            if(idJoueur==-1){
                                Message messageRetour = new Message("server","Ton pseudo ou mdp n'est pas bon ","ERROR");
                                this.oos.writeObject(messageRetour);
                            }else{
                                this.idJoueur=idJoueur;
                                this.idPartie=DemanderDeParticipationAUnePartie(idJoueur);
                                Message messageRetour = new Message("server","Tu es connecté en attente des autres joueurs","INSCRIPTION_PARTIE_GOOD");
                                this.oos.writeObject(messageRetour);

                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }


                    /*System.out.println("SERVER : Lister les co ");
                    listerLesConnexions();


                     */

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }


    /*
    * Fonction : sendMessageStartGame(int idPartie)
    * Objectifs : Envoyer un message de debut de partie à tous les participants de la partie n
    *
    * Retour : pas de retour
    *
    * */
    public  void sendMessageStartGame(int idPartie,Message message) throws IOException {
            if(this.getIdPartie()==idPartie){
                this.getOos().writeObject(message);
                this.getOos().flush();
            }
    }



    public int getIdPartie() {
        return idPartie;
    }




    private  synchronized void listerLesConnexions(){
        synchronized (Serveur.getListConnexion()){
            List<Connexion> liste  =Serveur.getListConnexion();
            for (Connexion con:liste) {
                System.out.println(con.toString());
            }
        }
    }


    public Connexion(Socket socket,int idPartie) throws IOException {

        this.socket=socket;
        this.idPartie=idPartie;
        OutputStream output = socket.getOutputStream();
        oos = new ObjectOutputStream(output);
        InputStream is = socket.getInputStream();
        ois = new ObjectInputStream(is);
        this.iD=(int)(Math.random() * 999999);

        // Montrer aux autres qui se connecte à la partie
        //distriuberMessage(new client.Message("Serveur","--Nouvelle personne--","VALID"));
    }

    @Override
    public String toString() {
        return "Connexion{" +
                "socket=" + socket +
                ", ois=" + ois +
                ", oos=" + oos +
                ", idPartie=" + idPartie +
                ", iD=" + iD +
                ", idJoueur=" + idJoueur +
                '}';
    }

    public void close(){
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
