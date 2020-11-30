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

    public static void importJson(String nomDuFichier) throws SQLException {
        //Completer la BDD
        System.out.println("debut import");
        JSONParser jsonP2 = new JSONParser();
        Categorie categorie;
        JSONObject o;

        RequeteKahoot rqKahoot = null;
        rqKahoot = new RequeteKahoot();

        try {
            JSONObject json2 = (JSONObject) jsonP2.parse(new FileReader(nomDuFichier));

            // Intégration de la catégorie
            System.out.println("integraton cat");
            categorie = new Categorie((String) json2.get("thème"));
            try {
                //Test si la catégorie existe déjà en base de données
                int retour =rqKahoot.findCategorie(categorie);
                if (retour == 0)
                    categorie.setIdCategorie(rqKahoot.addCategorie(categorie));
                else
                    categorie.setIdCategorie(retour);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Intégration des questions
            System.out.println("integration question");
            JSONObject tabQuizz = (JSONObject) json2.get("quizz");
            JSONObject tabFr1 = (JSONObject) tabQuizz.get("fr");
            JSONArray tabFrDebutant = (JSONArray) tabFr1.get("débutant");
            Iterator iterator = tabFrDebutant.iterator();
            while (iterator.hasNext()) {

                o = (JSONObject) iterator.next();
                Question q = new Question((String) o.get("question"));
                q.setCat(categorie);

                // Création de la bonne réponse
                Reponse bonneReponse = new Reponse((String)o.get("réponse"));
                bonneReponse.setIdReponse(rqKahoot.addReponse(bonneReponse));
                q.setBonneReponse(bonneReponse);

                //Sauvegarde de la question en base
                q.setIdQuestion(rqKahoot.addQuestion(q.getLaQuestion(),q.getBonneReponse(),q.getCat()));

                JSONArray tabPropositions = (JSONArray) o.get("propositions");
                for (int i=0;i<tabPropositions.size();i++)
                {
                    System.out.println("add proposition num " + i );

                    String s1= (String)tabPropositions.get(i);
                    String s2= bonneReponse.getTexteReponse();
                    if (s1.equals(s2)){ // Cette réponse est la bonne réponse r créée ci-dessus
                        q.addProposition(bonneReponse);
                        rqKahoot.addProposition(q,bonneReponse,1); /*car c'est la bonne rep*/
                    }else{
                        Reponse p = new Reponse(s1);
                        p.setIdReponse(rqKahoot.addReponse(p));
                        q.addProposition(p);
                        rqKahoot.addProposition(q,p,0);
                        System.out.println(q.getIdQuestion() + " et " + p.getIdReponse());
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
