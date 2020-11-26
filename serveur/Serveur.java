package serveur;

import client.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur extends Thread{

    private static final int port=60000;
    private ServerSocket serverSocket;
    private static List<Connexion> listConnexion;

    public Serveur() throws IOException {
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
    public void run() {
        try {
            while(true){
                Connexion con = new Connexion(serverSocket.accept());
                synchronized(listConnexion) {
                    listConnexion.add(con);
                    con.start();

                    // Limiter le nombre de joueur par exemple
                    // Si le nombre de joueur est de 3 alors on lance la partie
                    // 1 serveur = 1 partie
                    // Prevenir les utilisateur que la partie va commencer
                    if(listConnexion.size()==2){

                        Message message = new Message("tt","tt");
                        System.out.println("La partie va commencer il y a 2 personnes sur le serveur");

                        // Lancer la partie il faut distribuer un message à tous les participants
                        // Distribuer un message à un client particulier

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

    public static void main(String[] args) throws IOException {
        Serveur serv = new Serveur();
        serv.start();


    }
}
