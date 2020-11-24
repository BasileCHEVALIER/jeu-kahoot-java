package serveur;

import client.ApplicationClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private static List<Connexion> connexions=new ArrayList<>();
    private ServerSocket server;
    //private Nettoyeur clean;

    public static List<Connexion> getConnexions() {
        return connexions;
    }

    // question 9, constructeur par défaut du serveur
    public Server() throws IOException {

        try {
            this.server = new ServerSocket(4242);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // question 10, redefinir la méthode run()
    // afficher par la même occasion le nombre de connexions (cf. taille de la collection)
    // To do : implementer la solution pour enlever une connexion du tableau
    @Override
    public void run() {

        try{
            while (true){

                try {
                    Socket client=this.server.accept();
                    Connexion c =new Connexion(client);
                    this.connexions.add(c); // ajout à la collection
                    c.start(); // question 18, démarer le thread Connexions après l'ajout à la collection
                    System.out.println(this.connexions.size());
                } catch (IOException e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
        }finally {
            try {
                this.fermerSocketEcoute();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private synchronized void fermerSocketEcoute() throws IOException {
        server.close();
    }

    public static void main(String[] args) throws IOException {
        Server serv = new Server();
        serv.start();

        // question 11, tester la connexion avec une boucle
        //for(int i=0; i<5; i++) {
        //    Socket soc = new Socket(InetAddress.getLocalHost(), 4242);
        //}


        // question 15, ne fonctionne pas 2 boite de dialog à la suite
        // pour cela il faut lancer plusieurs ApplicationClient
        ApplicationClient dialog = new ApplicationClient();
        dialog.pack();
        dialog.setVisible(true);


        System.exit(0);
    }
}
