package serveur;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import static serveur.Serveur.lancerUnePartieSiElleEstPleine;

public class Service extends TimerTask implements Serializable {

    @Override
    public void run() {

        // Toutes les 30s verifier si il faut lancer une partie

        try {
            lancerUnePartieSiElleEstPleine();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Timer timer;
        timer = new Timer();
        timer.schedule(new Service(), 20000, 20000);
    }
}



