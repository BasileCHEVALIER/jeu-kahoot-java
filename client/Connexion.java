package client;

import java.io.*;
import java.net.Socket;

public class Connexion {

    Socket socket;

    private ObjectInputStream ois;
    private ObjectOutputStream oos ;

    private String iD;


    public Connexion(Socket socket) throws IOException {

        this.socket=socket;
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
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
