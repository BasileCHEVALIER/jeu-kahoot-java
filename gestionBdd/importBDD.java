package gestionBdd;

import data.Categorie;
import data.Question;
import data.Reponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;


public class importBDD {

    /*
    * Fonction : importJson(String nomDuFichier)
    * Objectif : Importer un fichier json dans la BDD
    * Retour : //
     */
    public static void importJson(String nomDuFichier) throws SQLException {
        //Completer la BDD
        System.out.println("Debut import de : " + nomDuFichier);
        JSONParser jsonP2 = new JSONParser();
        Categorie categorie;
        JSONObject o;

        //Objet pour lancer les requêtes
        RequeteKahoot rqKahoot = new RequeteKahoot();

        try {
            //Pour parser le fichier Json
            JSONObject json2 = (JSONObject) jsonP2.parse(new FileReader(nomDuFichier));

            //CATEGORIE
            categorie = new Categorie((String) json2.get("thème"));
            try {
                //Test si la catégorie existe déjà en base de données
                //int retour =rqKahoot.findCategorie(categorie);
                //if (retour == 0)
                //la fonction add Catégorie vérifie d'abord si la catégorie existe déjà en BDD
                    categorie.setIdCategorie(rqKahoot.addCategorie(categorie));
                //else
                    //categorie.setIdCategorie(retour);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //QUESTION (+PROPOSITION + REPONSE) (on prendra les quizz FR en débutant)
            JSONObject tabQuizz = (JSONObject) json2.get("quizz");
            JSONObject tabFr1 = (JSONObject) tabQuizz.get("fr");
            JSONArray tabFrDebutant = (JSONArray) tabFr1.get("débutant");
            //iterer sur chaque question
            Iterator iterator = tabFrDebutant.iterator();
            while (iterator.hasNext()) {

                o = (JSONObject) iterator.next();
                Question q = new Question((String) o.get("question"));
                q.setCat(categorie);

                // Création de la bonne réponse + ajout en BDD
                Reponse bonneReponse = new Reponse((String)o.get("réponse"));
                bonneReponse.setIdReponse(rqKahoot.addReponse(bonneReponse));
                q.setBonneReponse(bonneReponse);

                //ajout question en BDD
                q.setIdQuestion(rqKahoot.addQuestion(q.getLaQuestion(),q.getBonneReponse(),q.getCat()));

                //gestion des propositions
                JSONArray tabPropositions = (JSONArray) o.get("propositions");
                for (int i=0;i<tabPropositions.size();i++)
                {
                    String r= (String)tabPropositions.get(i);//rep sur laquelle on itère
                    String br= bonneReponse.getTexteReponse();//Bonne rep
                    if (r.equals(br)){ // si c'est la bonne rep
                        q.addProposition(bonneReponse);
                        //Ajout de la proposition en BDD
                        rqKahoot.addProposition(q,bonneReponse,1); /*car c'est la bonne rep*/
                    }else{
                        Reponse p = new Reponse(r);
                        p.setIdReponse(rqKahoot.addReponse(p));//ajout de la rep en bdd
                        q.addProposition(p);
                        //ajout de la proposition en BDD
                        rqKahoot.addProposition(q,p,0);/*car c'est pas la bonne rep*/
                    }

                }

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return;
    }
}
