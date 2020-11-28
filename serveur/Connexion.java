package serveur;

import client.Message;
import gestionBdd.RequeteKahoot;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class Connexion extends Thread {

    Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos ;
    private int idPartie;
    private int iD; // ID de la connexion

    public ObjectInputStream getOis() {
        return ois;
    }
    public ObjectOutputStream getOos() {
        return oos;
    }

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

                    // Lister les connexions
                    listerLesConnexions();


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
                            // Warning specific
                            int idJoueur = requeteKahoot.addJoueur(message.getExpediteur(),message.getMessage());
                            System.out.println("ID du nouveau joueur "+idJoueur);

                            if(idJoueur==-1){
                                Message messageRetour = new Message("server","Ton pseudo est déjà utilisé","ERROR");
                                this.oos.writeObject(messageRetour);
                            }else{
                                // ADD user in table partie_has_joueur
                                int retourAddJoueurHasAParie =requeteKahoot.addJoueurHasAParie(idJoueur,idPartie);
                                Message messageRetour = new Message("server","Tu es inscris au site pour une partie","INSCRIPTION_PARTIE_GOOD");
                                this.oos.writeObject(messageRetour);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
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
                                int retourAddJoueurHasAParie =requeteKahoot.addJoueurHasAParie(idJoueur,idPartie);
                                Message messageRetour = new Message("server","Tu es connecté","INSCRIPTION_PARTIE_GOOD");
                                this.oos.writeObject(messageRetour);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }





                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    public  synchronized void sendMessageStartGame(){
        Message message = new Message("SERVER","Nous allons commencer la partie","startGame");
        synchronized (Serveur.getListConnexion()){
            List<Connexion> liste  =Serveur.getListConnexion();
            for (Connexion con:liste) {
                try {
                    con.getOos().writeObject(message);
                    con.getOos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private  synchronized void distriuberMessage(Object message){
        synchronized (Serveur.getListConnexion()){
            List<Connexion> liste  =Serveur.getListConnexion();
            for (Connexion con:liste) {
                try {
                    con.getOos().writeObject(message);
                    con.getOos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public  synchronized void distriuberMessagePourUnClient(Object message){
                try {
                    this.getOos().writeObject(message);
                    this.getOos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        distriuberMessage(new client.Message("Serveur","--Nouvelle personne--"));

    }

    @Override
    public String toString() {
        return "Connexion{" +
                "socket=" + socket +
                ", ois=" + ois +
                ", oos=" + oos +
                ", iD='" + iD + '\'' +
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
