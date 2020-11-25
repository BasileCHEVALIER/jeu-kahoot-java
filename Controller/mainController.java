package Controller;
import bdd.RequeteKahoot;
import serveur.*;
import client.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class mainController {

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {

        // Connecter la BDD
        RequeteKahoot requeteKahoot = new RequeteKahoot();

        // Demarage du serveur
        Server serv = new Server();
        serv.start();

        // Demarage du client
        ApplicationClient dialog = new ApplicationClient();
        dialog.pack();
        dialog.setVisible(true);

        // exit du main
        System.exit(0);

    }

    // add comment test git


}
