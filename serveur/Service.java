package serveur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Service extends testThread{

    private static List listServeur ;


    public Service() throws IOException, SQLException {
        Serveur serveur = new Serveur();
        serveur.start();

    }

    public static void miseEnPlaceServeurSupplementaire() throws IOException, SQLException {
        Serveur serveur = new Serveur();
        listServeur.add(serveur);
        serveur.start();

    }



    @Override
    public void run() {

            while(true){

                // Trop de connexion mise en place d'un serveur

                }

        }

    public static void main(String[] args) throws IOException, SQLException {

        // Demarage du service avec un serveur
        Service service = new Service();
        service.start();

    }
}
