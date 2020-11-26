package serveur;

import test.Data;

import java.io.*;
import java.net.Socket;

public class Connexion extends Thread{

    private Socket socket;
    private ObjectInputStream writer;
    private ObjectOutputStream reader;

    //private PrintWriter writer;
    //private BufferedReader reader;
    //private Nettoyeur clean;


    // question 16, important le synchronized pour accéder à une ressource commune
    private synchronized void envoyerMessage(Data message) throws IOException {

        //writer.println(message);
        //writer.flush(); // forcer ecriture

        try {
            writer.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       // writer.flush();

    }



    @Override
    public void run() {

            try {
                while(true) {

                    //String message = reader.readLine();
                    //Data message = new Data()
                    // message = reader.defaultWriteObject();

                    Data message;

                    if (message != null)
                        for (Connexion client : Server.getConnexions()) {
                            client.envoyerMessage(message);
                            System.out.println(message);
                        }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                for(Connexion client:Server.getConnexions()){
                    try {
                        client.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }



    // question 8
    public Connexion(Socket socket) throws IOException {

        this.socket = socket;

        //this.writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.writer = new ObjectInputStream(this.socket.getInputStream());
        this.reader = new ObjectOutputStream(this.socket.getOutputStream());
    }


}
