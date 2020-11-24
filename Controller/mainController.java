package Controller;
import serveur.*;
import client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class mainController {

    public static void main(String[] args) throws InterruptedException, IOException {
        testServer t = new testServer();
        t.start();
        testClient c = new testClient();
        c.connect();


    }



}
