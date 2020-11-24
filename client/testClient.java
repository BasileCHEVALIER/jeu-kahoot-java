package client;

import java.io.*;
import java.net.Socket;

public class testClient extends Thread {

    public void connect() throws IOException {

        Socket s = new Socket("127.0.0.1",4242);

        Writer p = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

        p.write("cc");

        p.flush();

        p.close();


    }
}
