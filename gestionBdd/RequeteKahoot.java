package gestionBdd;


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

    public List<Categorie> getListeCategories() throws SQLException {
        List<Categorie> categories = new ArrayList<Categorie>();
        //connect = DriverManager.getConnection(url, user, mdp);
        String requete = "SELECT texteCategorie FROM categorie";
        Statement stmt = connect.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            //System.out.println(res.getString("texteCategorie"));
            Categorie categorie = new Categorie(res.getString("texteCategorie"));
            categories.add(categorie);
        }
        res.close();
        stmt.close();
        return categories;
    }
    // test new branch
*/


    public int getNbJoueurs() throws SQLException {
        int nombreJoueurs = 0;
        //connect = DriverManager.getConnection(url, user, mdp);
        String requete = "SELECT Count(*) as nombreJoueurs from joueur";
        Statement stmt = connect.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            //System.out.println(res.getString("texteCategorie"));
            nombreJoueurs = (res.getInt("nombreJoueurs"));
            //categories.add(categorie);
        }
        res.close();
        stmt.close();
        //return categories;
        return nombreJoueurs;
    }



   /*
    public Categorie getCategorie(Categorie categorie) throws SQLException {
        String requete = "SELECT idCategorie FROM categorie WHERE texteCategorie = ?";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setString(1, categorie.getTexteCategorie());
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {

            categorie.setIdentifiant(res.getInt("idCategorie"));
            //categorie.setTexteCategorie(categorie.getTexteCategorie());
        }
        res.close();
        pstmt.close();
        return categorie;
    }






    public Joueur getJoueur(int unIdJoueur) throws SQLException {
        String requete = "SELECT * FROM joueur " + "WHERE idJOUEUR = ?";
        PreparedStatement pstmt = connect.prepareStatement(requete);
        pstmt.setInt(1, unIdJoueur);
        ResultSet res = pstmt.executeQuery();
        Joueur joueur = new Joueur();
        while (res.next()) {

            joueur.setLogin((res.getString("login")));
            joueur.setMdp(res.getString("mdp"));
            joueur.setIdentifiant(res.getInt("idJoueur"));

        }
        res.close();
        pstmt.close();
        return joueur;

    }*/
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
