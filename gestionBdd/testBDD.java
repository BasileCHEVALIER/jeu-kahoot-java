package gestionBdd;

import data.Categorie;
import data.Joueur;

import java.io.File;
import java.io.FileFilter;
import java.sql.SQLException;

import static gestionBdd.importBDD.importJson;

public class testBDD {
    /*public static void main(String[] args) throws SQLException {
        RequeteKahoot requeteKahoot = new RequeteKahoot();

        //test importBDD
        importJson("openquizzdb_40.json");

    }*/

    public static void main(String[] args) throws SQLException {

        //INITIALISATION : récupération des différents quizz présents
        //Structure de lecture des fichiers (propriétaire : I. Le Glaz)
        File rep = new File(".");
        File[]  lesFichiers = rep.listFiles( new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".json");
            }}
        );

        for ( File f : lesFichiers)
        {
            //Mise du fichier en BDD
            importJson(f.getName());
        }
        System.out.println("finish");
    }
}
