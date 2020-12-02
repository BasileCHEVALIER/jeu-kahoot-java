package gestionBdd;


import data.Question;
import data.Reponse;
import data.Categorie;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RequeteKahoot {

    private static Connection connect;
    private static String url="jdbc:mysql://localhost:3306/kahootctp";
    private static String user = "root";
    private static String mdp = "";
    /*private static String url = "jdbc:mysql://localhost:3306/kahoot3";
    private static String user = "gautier";
    private static String mdp = "26102000";*/

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
    public int addJoueur(String login, String mdp) throws SQLException {
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
    public int seConnecter(String login, String mdp) throws SQLException {
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
    public int addJoueurHasAParie(int idJoueur, int idPartie) throws SQLException {
        try {
            String requete = "INSERT INTO `partie_has_joueur`(`ID_PARTI`, `ID_JOUEUR`, `score_partie`) VALUES (?,?,0) ";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(2, idJoueur);
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
     * Objectif(s) : Inserer dans la table question_has_a_partie les questions (10 questions d'une même catégorie choisit aléatoirement) !
     *
     * Retour : Pas de retour
     * */
    public void chargerLesQuestionDeLaPartie(int idPartie) throws SQLException {
        //recup un id de catégorie aleatoire
        int idCat = finCategorieAleatoire();

        String requete = "INSERT INTO question_has_a_partie SELECT ?,ID_QUESTION FROM question WHERE ID_CATEGORIE=? ORDER BY rand() LIMIT 10";

        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, idPartie);
        pstmt.setInt(2, idCat);

        pstmt.executeUpdate();
        ResultSet res = pstmt.getGeneratedKeys();
        res.close();
        pstmt.close();

    }

    /*
     * Fonction : finCategorieAleatoire()
     *
     * Objectif(s) : Obtenir un id de catégorie aléatoire parmis ceux de la BDD
     *
     * Retour : un idCategorie (int)
     * */
    public int finCategorieAleatoire() throws SQLException {
        int id = 0;

        String requete = "SELECT idCATEGORIE FROM categorie ORDER BY rand() LIMIT 1";
        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            id = res.getInt(1);
        }
        res.close();
        pstmt.close();

        return id;

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

        List<Question> lesQuestions = new ArrayList<Question>();

        List<Integer> idDesQuestions = new ArrayList<>();

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
        for (int i = 0; i < idDesQuestions.size(); i++) {

            // Creation de mon objet question et reponse qui est la bonne réponse et du tableau les propostions
            Question maQuestion = new Question(getTextQuestion(idDesQuestions.get(i)), idDesQuestions.get(i));
            int idBonnereponse = getIdLaBonneReponse(idDesQuestions.get(i));
            Reponse laBonneReponse = new Reponse(idBonnereponse, getTexteLaBonneReponse(idDesQuestions.get(i)));
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

        String textQuestion = "";

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

        String textReponse = "";

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

        int id = -1;
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

        String texteBonneReponse = "";
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

        List<Reponse> lesPropositions = new ArrayList<>();
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
        for (int j = 0; j < idDesBonnesReponses.size(); j++) {

            String texteReponse = getTextReponse(idDesBonnesReponses.get(j));
            Reponse laReponse = new Reponse(idDesBonnesReponses.get(j), texteReponse);

            lesPropositions.add(laReponse);

        }
        return lesPropositions;
    }

    //mise à jour score serveur dans bdd
    /*
     * Fonction : miseAJourScore(int idPartie, int idJoueur, int score)
     *
     * Objectif(s) : Met à jours dans la BDD le score d'un joueur d'une partie
     *
     * Retour : true si update réussit, false sinon
     * */
    public boolean miseAJourScore(int idPartie, int idJoueur, int score) {
        String requete = "UPDATE partie_has_joueur SET score_partie =(?) WHERE ID_PARTI=(?) AND ID_JOUEUR=(?); ";
        PreparedStatement pstmt = null;
        try {
            pstmt = connect.prepareStatement(requete);
            pstmt.setInt(1, score);
            pstmt.setInt(2, idPartie);
            pstmt.setInt(3, idJoueur);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //FONCTION POUR IMPORT JSON
    /*
     * Fonction : addCategorie(Categorie uneCategorie)
     *
     * Objectif(s) : Ajoute une catégorie dans la BDD
     *
     * Retour : INT = id de la catégorie (l'id de la catégorie insérée ou son id si elle existait déjà)
     * */
    public int addCategorie(Categorie uneCategorie) throws SQLException {

        int nouvelId = findCategorie(uneCategorie); //récupère l'id de la cat si elle existe déjà en BDD
        //System.out.println("ADD CAT : " + nouvelId);
        if (nouvelId == 0) {
            //on ajoute si la cat n'existe pas déjà
            //System.out.println("pas encore en BDD");
            String requete = "INSERT INTO categorie(texteCATEGORIE) VALUES (?)";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, uneCategorie.getTexteCategorie());
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            if (res.next()) {
                nouvelId = res.getInt(1);
                //System.out.println("Id de la nouvelle categorie : " + nouvelId);
            }
            res.close();
            pstmt.close();
        }
        return (nouvelId);
    }

    /*
     * Fonction : findCategorie(Categorie uneCategorie)
     *
     * Objectif(s) : Chercher si une catégorie existe dans la BDD
     *
     * Retour : INT = id de la catégorie si elle existe déjà en BDD, 0 si elle n'est pas trouvé
     * */
    public int findCategorie(Categorie uneCategorie) throws SQLException {
        int idc = 0;
        String requete = "SELECT idCATEGORIE from categorie where texteCATEGORIE =?";
        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, uneCategorie.getTexteCategorie());

        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            idc = res.getInt("idCATEGORIE");
        }
        return idc;
    }

    /*
     * Fonction : addReponse(Reponse rep)
     *
     * Objectif(s) : Ajout d'une réponse en BDD
     *
     * Retour : INT = id de la réponse (l'id de la réponse insérée ou son id si elle existait déjà)
     * */
    public int addReponse(Reponse rep) throws SQLException {
        int id = findReponse(rep);
        if (id == 0) {
            //signifie que la reponse n'existe pas en base
            String requete = "INSERT INTO `reponse` VALUES (NULL,\"" + rep.getTexteReponse() + "\")";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
            res.close();
            pstmt.close();
        }
        return id;
    }

    /*
     * Fonction : findReponse(Reponse uneReponse)
     *
     * Objectif(s) : Chercher si une réponse existe dans la BDD
     *
     * Retour : INT = id de la réponse si elle existe déjà en BDD, 0 si elle n'est pas trouvé
     * */
    public int findReponse(Reponse uneReponse) throws SQLException {
        int idr = 0;
        String requete = "SELECT ID_REPONSE from reponse where texteREPONSE=?";
        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, uneReponse.getTexteReponse());

        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            idr = res.getInt("ID_REPONSE");
        }
        return idr;
    }

    /*
     * Fonction : addQuestion(String texte, Reponse bonneRep, Categorie cat)
     *
     * Objectif(s) : Ajout de la question en BDD
     *
     * Retour : INT = id de la question (l'id de la question insérée ou son id si elle existait déjà)
     * */
    public int addQuestion(String texte, Reponse bonneRep, Categorie cat) throws SQLException {
        Question q = new Question(texte);
        q.setBonneReponse(bonneRep);
        q.setCat(cat);

        //ajout catégorie et add rep
        cat.setIdCategorie(addCategorie(cat));
        bonneRep.setIdReponse(addReponse(bonneRep));

        int id = findQuestion(q);
        if (id == 0) {
            //n'existe pas en base
            String requete = "INSERT INTO `question` VALUES (NULL,\"" + q.getLaQuestion() + "\",\"" + q.getCat().getIdCategorie() + "\",\"" + q.getBonneReponse().getIdReponse() + "\")";
            PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
            res.close();
            pstmt.close();
        }
        return id;
    }


    /*
     * Fonction : findQuestion(Question uneQuestion)
     *
     * Objectif(s) : Chercher si une question existe dans la BDD
     *
     * Retour : INT = id de la question si elle existe déjà en BDD, 0 si elle n'est pas trouvé
     * */
    public int findQuestion(Question uneQuestion) throws SQLException {
        int idq = 0;
        String requete = "SELECT ID_QUESTION from question where texteQUESTION=?";
        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, uneQuestion.getLaQuestion());

        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            idq = res.getInt("ID_QUESTION");
        }
        return idq;
    }

    /*
     * Fonction : addProposition(Question q, Reponse r, int bonneRep)
     *
     * Objectif(s) : Ajout d'une proposition en BDD
     *
     * Retour : TRUE si elle a pu être inséré, FALSE sinon
     * */
    public boolean addProposition(Question q, Reponse r, int bonneRep) throws SQLException {

        int test = findProposition(q, r, bonneRep);

        if (test == 0) {
            String requete = "INSERT INTO propositions(ID_QUESTION,ID_REPONSE,bonneReponse) VALUES(?,?,?)";
            PreparedStatement pstmt = null;
            try {
                pstmt = connect.prepareStatement(requete);
                pstmt.setInt(1, q.getIdQuestion());
                pstmt.setInt(2, r.getIdReponse());
                pstmt.setInt(3, bonneRep);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            //System.out.println("proposition déjà en BDD");
            return false;
        }
    }

    /*
     * Fonction : findProposition(Question q, Reponse r, int bonneRep)
     *
     * Objectif(s) : Chercher si une proposition existe déjà en BDD
     *
     * Retour : INT = id de la question de la propsosition si elle existe déjà en BDD, 0 si elle n'est pas trouvé
     * */
    public int findProposition(Question q, Reponse r, int bonneRep) throws SQLException {
        int idq = 0;
        String requete = "SELECT * from propositions where ID_QUESTION=? AND ID_REPONSE=? AND bonneReponse=?";
        PreparedStatement pstmt = connect.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, q.getIdQuestion());
        pstmt.setInt(2, r.getIdReponse());
        pstmt.setInt(3, bonneRep);

        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            idq = res.getInt("ID_QUESTION");
        }
        return idq;
    }

}