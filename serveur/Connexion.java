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
    private int iD;

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

                    if(message.getTypeMessage().compareTo("INSCRIPTION")==0){

                        // Envoyer un message de réponse à l'utilisateur si son idantifiant est déjà utilisé
                        try {
                            RequeteKahoot requeteKahoot = new RequeteKahoot();
                            int idJoueur = requeteKahoot.addJoueur(message.getExpediteur());
                            System.out.println("ID du nouveau joueur "+idJoueur);

                            if(idJoueur==-1){
                                Message messageRetour = new Message("server","Ton pseudo est déjà utilisé","ERROR");
                                this.oos.writeObject(messageRetour);
                            }else{
                                Message messageRetour = new Message("server","Ton pseudo est bon","VALID");
                                this.oos.writeObject(messageRetour);
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
/*
                    if(message.getTypeMessage().equals("REPONSE")){
                        System.out.println("reponse ok : "+message.getMessage());
                        // Envoyer un message de réponse
                    }
*/

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    public  synchronized void startGame(){
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


    public Connexion(Socket socket) throws IOException {

        this.socket=socket;
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
