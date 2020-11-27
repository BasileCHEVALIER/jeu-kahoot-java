package serveur;

import client.Message;
import gestionBdd.RequeteKahoot;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Serveur extends Thread{

    private static final int port=60000;
    private ServerSocket serverSocket;
    private static List<Connexion> listConnexion;


    public Serveur() throws IOException, SQLException {
        this.serverSocket = new ServerSocket(port);
        listConnexion = new ArrayList<>();

    }

    private void fermerSocketEcoute(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture de socket d'écoute");
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
                * Lance l'excution du thread
                * */
                Connexion con = new Connexion(serverSocket.accept());
                synchronized(listConnexion) {
                    listConnexion.add(con);
                    con.start();
                    con.toString();


                    // Limiter le nombre de joueur par exemple
                    // Si le nombre de joueur est de 3 alors on lance la partie
                    // 1 serveur = 1 partie
                    // Prevenir les utilisateur que la partie va commencer
                    // To do faire un code plus fin
                    // Une personne peut-être connecté sans avoir réalisé inscription

                    if(listConnexion.size()==2){
                        System.out.println("La partie va commencer il y a 2 personnes sur le serveur");
                        con.sendMessageStartGame();
                    }else if(listConnexion.size()<2){
                        System.out.println("Le serveur est pas complet !!! ");

                    }else{
                        System.out.println("Le serveur est complet !!!");
                        Message message = new Message("SERVER","Nous sommes complet !!!","FULLSERVER");
                        con.distriuberMessagePourUnClient(message);
                    }


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
        Serveur serv = new Serveur();
        serv.start();


    }
}
