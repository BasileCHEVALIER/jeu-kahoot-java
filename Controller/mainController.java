package Controller;
import gestionBdd.RequeteKahoot;
import serveur.*;
import client.*;

import java.io.IOException;
import java.sql.SQLException;

public class mainController {

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {

        // Connecter la BDD
        RequeteKahoot requeteKahoot = new RequeteKahoot();

        // Demarage du serveur
        Serveur serv = new Serveur();
        serv.start();




    }

    // add comment test git


}
