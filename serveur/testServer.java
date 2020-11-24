package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class testServer extends Thread {

    @Override
    public void run() {
        ServerSocket s = null;



        try {
            s = new ServerSocket(4242);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            s.setSoTimeout(5000);
            while(!isInterrupted()) {
                System.out.println("en attente...");
                Socket socket = s.accept();
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                System.out.println("connecté");
                String line = in.readLine();
                if (line != null) {
                    System.out.println(line);
                }
                in.close();
                socket.close();
                System.out.println("fermeture du client");
            }

        } catch (IOException e) {
            try {
                s.close();
                System.out.println("fermeture serveur");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            interrupt();
            System.out.println("interruption du thread");
            //e.printStackTrace();
        }
        interrupt();


    }
}
