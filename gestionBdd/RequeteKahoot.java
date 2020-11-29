package gestionBdd;


import data.Question;
import data.Reponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RequeteKahoot {

    private static Connection connect;
    private static String url="jdbc:mysql://localhost:3306/kahoot2";
    private static String user = "gautier";
    private static String mdp = "26102000";

    public RequeteKahoot() throws SQLException {
        connect = DriverManager.getConnection(url, user, mdp);

    }


    /*
    * Fonction : addJoueur(String login,String mdp)
     *
     * Objectif(s) : Inscrire un joueur dans la BDD
     *
     * Retour : Identifiant du joueur
    * */
    public int addJoueur(String login,String mdp) throws SQLException {
        try {
            String requete = "INSERT INTO joueur (login,mdp) VALUES (?,?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, login);
            pstmt.setString(2, mdp);

            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);

            }
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
        return -1;
    }


    /*
    * Fonction : seConnecter(String login,String mdp)
    *
    * Objectif(s) : Verifier si un utilisateur à le bon password et login
    *
    * Retour : Renvoie -1 si le login ou bien password est mauvais sinon retourne l'identifiant du joueur
    * */
    public int seConnecter(String login,String mdp) throws SQLException {
        try {
            String requete = "select idJOUEUR from joueur WHERE login like ? and mdp like ?;";
            PreparedStatement pstmt = connect.prepareStatement(requete);
            pstmt.setString(1, login);
            pstmt.setString(2, mdp);

            ResultSet res = pstmt.executeQuery();
            int id = -1;
            if (res.next()) {
                id = res.getInt(1);

            }
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
        return -1;
    }



    public int creationNouvellePartie() throws SQLException {
        try {
            String requete = "INSERT INTO `partie` (`ID_PARTIE`) VALUES (NULL); ";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);

            }
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
        return -1;
    }

    /*
    * Fonction : addJoueurHasAParie(int idJoueur,int idPartie)
    *
    * Objectif(s) : Ajouter un utilisateur à la partie
    *
    * Retour : -1 si il y a une erreur et 0 si pas de soucis
    * */
    public int addJoueurHasAParie(int idJoueur,int idPartie) throws SQLException {
        try {
            String requete = "INSERT INTO `partie_has_joueur`(`ID_PARTI`, `ID_JOUEUR`, `score_partie`) VALUES (?,?,0) ";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(2,idJoueur);
            pstmt.setInt(1, idPartie);

            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
        return -1;
    }

    /*
    * Fonction : chargerLesQuestionDeLaPartie(int idPartie)
    *
    * Objectif(s) : Inserer dans la table question_has_a_partie les questions !
    *
    * Retour : Pas de retour
     * */
    public void chargerLesQuestionDeLaPartie(int idPartie) throws SQLException {

        String requete = "INSERT INTO question_has_a_partie SELECT ?,ID_QUESTION FROM question LIMIT 10";

        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, idPartie);

        pstmt.executeUpdate();
        ResultSet res = pstmt.getGeneratedKeys();
        res.close();
        pstmt.close();

    }


    /*
    * Fonction : getLesQuestionsDeLaPartie(int idPartie)
    *
    * Objectif(s) : Renvoyer une liste avec tous les objets questions
    *
    * Retour : la liste avec les questions
    *
    * */
    public List getLesQuestionsDeLaPartie(int idPartie) throws SQLException {

        List<Question> lesQuestions=new ArrayList<Question>();

        List <Integer> idDesQuestions=new ArrayList<>();

        // Requete SQL qui va remplir le tableau avec les ID des question à gérer
        String requete = "SELECT ID_QUESTION FROM `question_has_a_partie` WHERE ID_PARTIE=? ";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idPartie);
        ResultSet res = pstmt.executeQuery();
        while (res.next()) {
            idDesQuestions.add(res.getInt(1));
        }
        res.close();
        pstmt.close();

        // Requete SQL parcours des questions
        for (int i=0;i<idDesQuestions.size();i++){

            // Creation de mon objet question et reponse qui est la bonne réponse et du tableau les propostions
            Question maQuestion = new Question(getTextQuestion(idDesQuestions.get(i)),idDesQuestions.get(i));
            int idBonnereponse=getIdLaBonneReponse(idDesQuestions.get(i));
            Reponse laBonneReponse = new Reponse(idBonnereponse,getTexteLaBonneReponse(idDesQuestions.get(i)));
            List<Reponse> lesPropositions = getLesPropositions(idDesQuestions.get(i));

            // Ajouter la bonneRéponse et la bonne reponse a question
            maQuestion.setBonneReponse(laBonneReponse);
            maQuestion.setLesPropositions(lesPropositions);

            lesQuestions.add(maQuestion);
        }

        return lesQuestions;
    }


    /*
    * Fonction : getTextQuestion(int idQuestion)
    *
    * Objectifs : Retourner le texte de la question
    *
    * Retour : un String qui est le texte de la question
    * */
    public String getTextQuestion(int idQuestion) throws SQLException {

        String textQuestion="";

        String requete = "SELECT texteQUESTION FROM `question` WHERE ID_QUESTION=? ";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idQuestion);

        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            textQuestion = res.getString(1);

        }
        res.close();
        pstmt.close();

        return textQuestion;
    }

    /*
     * Fonction : getTextQuestion(int idQuestion)
     *
     * Objectifs : Retourner le texte de la question
     *
     * Retour : un String qui est le texte de la question
     * */
    public String getTextReponse(int idReponse) throws SQLException {

        String textReponse="";

        String requete = "SELECT `texteREPONSE` FROM `reponse` WHERE `ID_REPONSE`=?";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idReponse);

        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            textReponse = res.getString(1);

        }
        res.close();
        pstmt.close();

        return textReponse;
    }

    /*
     * Fonction : getIdLaBonneReponse(int idQuestion)
     *
     * Objectifs : Retourner le texte de la question en fonction de son identifiant
     *
     * */
    public int getIdLaBonneReponse(int idQuestion) throws SQLException {

        int id=-1;
        String requete = "SELECT ID_BONNE_REPONSE FROM `question` WHERE ID_QUESTION=?";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idQuestion);
        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            id = res.getInt(1);
        }
        res.close();
        pstmt.close();

        return id;
    }



    /*
     * Fonction : getTexteLaBonneReponse(int idQuestion)
     *
     * Objectifs : Retourner le texte de la la bonne réponse en fonction de son identifiant
     *
     * */
    public String getTexteLaBonneReponse(int idQuestion) throws SQLException {

        String texteBonneReponse="";
        String requete = "SELECT `texteREPONSE` FROM `reponse` WHERE ID_REPONSE=? ";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idQuestion);
        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            texteBonneReponse = res.getString(1);
        }
        res.close();
        pstmt.close();

        return texteBonneReponse;
    }

    /*
     * Fonction : getTexteLaBonneReponse(int idQuestion)
     *
     * Objectifs : Retourner le tableau avec les propositions
     *
     * */
    public List<Reponse> getLesPropositions(int idQuestion) throws SQLException {

        List<Reponse> lesPropositions= new ArrayList<>();
        List<Integer> idDesBonnesReponses = new ArrayList<>();

        // Requete SQL : Selectionner les reponses dans propositions pour avoir les idantifants des reponses
        String requete = "SELECT `ID_REPONSE` FROM `propositions` WHERE ID_QUESTION=?";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, idQuestion);
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            idDesBonnesReponses.add(res.getInt(1));
        }

        res.close();
        pstmt.close();

        // Requete SQL :
        for (int j=0;j<idDesBonnesReponses.size();j++){

            String texteReponse=getTextReponse(idDesBonnesReponses.get(j));
            Reponse laReponse = new Reponse(idDesBonnesReponses.get(j),texteReponse);

            lesPropositions.add(laReponse);

        }
        return lesPropositions;
    }






    /*
    public int addCategorie(Categorie categorie) {
        try {
            String requete = "INSERT INTO categorie (texteCATEGORIE) VALUES (?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categorie.getTexteCategorie());
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);

            }
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();


        }
        return -1;

    }

    public int addReponse(Reponse reponse) {
        try {
            String requete = "INSERT INTO reponse (texteReponse) VALUES (?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            String test = reponse.getTexteOption();
            pstmt.setString(1, reponse.getTexteOption());
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);

            }
            res.close();
            pstmt.close();
            return id;

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println(se);

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();


        }
        return -1;

    }

    public int addQuestion(String texteQuestion, Reponse bonneReponse, Categorie categorie) {
        try {
            String requete = "INSERT INTO question (texteQUESTION,ID_BONNE_REPONSE,ID_CATEGORIE) VALUES (?,?,?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, texteQuestion);
            pstmt.setInt(2, bonneReponse.getIdentifiant());
            pstmt.setInt(3, categorie.getIdentifiant());
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);
                return id;
            }
            res.close();
            pstmt.close();


        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println(se);
            return -1;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return -1;

        }
        return -1;

    }


    public boolean addProposition(Question question, Reponse reponse) {
        try {
            String requete = "INSERT INTO propositions (ID_QUESTION,ID_REPONSE) VALUES (?,?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, question.getIdentifiant());
            pstmt.setInt(2, reponse.getIdentifiant());
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id = 0;
            if (res.next()) {
                id = res.getInt(1);
                return true;
            }
            res.close();
            pstmt.close();


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;

        }
        return true;

    }

    public void lectureJson(String nomDuFichier) {
        //Lecture d'un ensemble
        int noOption = 1;
        JSONObject o, o2;
        Categorie categorie;

        JSONParser jsonP2 = new JSONParser();
        try {
            RequeteKahoot requeteKahoot = new RequeteKahoot();

            JSONObject json2 = (JSONObject) jsonP2.parse(new FileReader(nomDuFichier));

            JSONObject tabQuizz = (JSONObject) json2.get("quizz");
            JSONObject tabFr1 = (JSONObject) tabQuizz.get("fr");
            JSONArray tabFrDebutant = (JSONArray) tabFr1.get("débutant");
            categorie = new Categorie((String) json2.get("thème"));
            int idCategorie = requeteKahoot.addCategorie(categorie);
            categorie.setIdentifiant(idCategorie);

            Iterator iterator = tabFrDebutant.iterator();
            while (iterator.hasNext()) {

                o = (JSONObject) iterator.next();
                Reponse r = new Reponse((String) o.get("réponse"));
                int idreponse = requeteKahoot.addReponse(r);
                r.setIdentifiant(idreponse);
                String bonneRep=r.getTexteOption();
                Question q = new Question((String) o.get("question"));
                //q.setCategorie(categorie);
                int idQuestion = requeteKahoot.addQuestion(q.getTexteOption(), r, categorie);
                q.setIdentifiant(idQuestion);
                JSONArray tabPropositions = (JSONArray) o.get("propositions");
                requeteKahoot.addProposition(q,r);
                //q.setBonneReponse(r);

                for (int i = 0; i < tabPropositions.size(); i++) {
                    String s1 = (String) tabPropositions.get(i);
                    if (!bonneRep.equals(s1)){
                        Reponse reponse = new Reponse(s1);
                        int idProposition=requeteKahoot.addReponse(reponse);
                        reponse.setIdentifiant(idProposition);
                        requeteKahoot.addProposition(q,reponse);

                    }

                }

            }

        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Initialisation de la liste des questions et réponses


    }

    */
}
