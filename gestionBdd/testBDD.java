package gestionBdd;

import data.Categorie;
import data.Joueur;

import java.sql.SQLException;

import static gestionBdd.importBDD.importJson;

public class testBDD {
    public static void main(String[] args) throws SQLException {
        RequeteKahoot requeteKahoot = new RequeteKahoot();

        //test importBDD
        importJson("openquizzdb_40.json");

    }
}
