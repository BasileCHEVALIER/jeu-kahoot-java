package gestionBdd;

import data.Categorie;
import data.Joueur;

import java.io.File;
import java.io.FileFilter;
import java.sql.SQLException;

import static gestionBdd.importBDD.importJson;

/*
 * Cette classe a pour but de mettre à jour la BDD, c'est-à-dire ajouter tout les fichiers JSON présent dans le fichier en BDD
 * Une vérification est effectué dans chacune des fonctions d'ajout en BDD,
 * ainsi si le fichier a déjà été ajouté, rien ne serai ajouté en double en BDD
 */
public class MiseAJourBDD {

    public static void main(String[] args) throws SQLException {
        //INITIALISATION : récupération des différents quizz JSON présents
        File rep = new File(".");
        File[]  lesFichiers = rep.listFiles( new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".json");
            }}
        );

        System.out.println("Début de l'import des fichiers");

        //itération de la fonction d'import sur chacun des fichiers
        for ( File f : lesFichiers)
        {
            //Mise du fichier en BDD
            importJson(f.getName());
        }
        System.out.println("Mise à jour BDD terminée");
    }
}
