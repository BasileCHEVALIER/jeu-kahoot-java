package serveur;

import client.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Connexion extends Thread{

    Socket socket;

    private ObjectInputStream ois;
    private ObjectOutputStream oos ;

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    private String iD;



    @Override
    public void run() {
        while (true){
            try {
                Object line = ois.readObject();
                if (line!=null) {

                    distriuberMessage(line);
                    System.out.println("RUN SERVER ");

                    // Cast de notre objet
                    Message message = (Message)line;

                    System.out.println(message.getMessage());
                    System.out.println(message.getTypeMessage());


                    // Traitement du le serveur a faire ici
                    if(message.getEtape()==2){
                        System.out.println("egale 2 ");

                    }


                    if(message.getTypeMessage().equals("INSCRIPTION")){
                        System.out.println("okk");
                        // Envoyer un message de réponse

                    }




                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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

    private  synchronized void distriuberMessageOneClient(Object message){
                try {
                    this.getOos().writeObject(message);
                    this.getOos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }




    public Connexion(Socket socket) throws IOException {
        this.socket=socket;
        OutputStream output = socket.getOutputStream();
        oos = new ObjectOutputStream(output);
        InputStream is = socket.getInputStream();
        ois = new ObjectInputStream(is);

        // Montrer aux autres qui se connecte à la partie
        distriuberMessage(new client.Message("Serveur","--Nouvelle personne--"));

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
