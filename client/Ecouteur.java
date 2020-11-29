package client;

import data.Question;
import data.Reponse;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Ecouteur extends Thread {

    private JTextArea zoneMessage;
    private Connexion connexion;

    // Ajout des variables suivantes pour les
    private JButton inscriptionButton;
    private JButton connexionButton;
    private JTextField name;
    private JTextField saisieTexte;
    private JPanel logPanel;
    private JPanel buttonReponsePanel;

    private List<Question> lesQuestions;

    public Ecouteur(JTextArea zoneMessage,Connexion connexion,JButton inscriptionButton, JButton connexionButton,JTextField name,JTextField saisieTexte,JPanel logPanel,JPanel buttonReponsePanel,List lesQuestions) {
        this.zoneMessage = zoneMessage;
        this.connexion = connexion;

        // Les nouveaux champs
        this.inscriptionButton = inscriptionButton;
        this.connexionButton = connexionButton;
        this.name=name;
        this.saisieTexte=saisieTexte;
        this.logPanel=logPanel;

        // Les nouveaux ajouts
        this.buttonReponsePanel=buttonReponsePanel;
        this.buttonReponsePanel.setVisible(false);

        this.lesQuestions=lesQuestions;

    }

    public List<Question> getLesQuestions() {
        return lesQuestions;
    }

    @Override
    public void run() {
        while (!this.currentThread().isInterrupted()){
            try {
                Message msg = (Message) connexion.getOis().readObject();
                if (msg!=null){

                    // Permet d'afficher les messages du serveur
                    // To do : Pourquoi pas ajouter un switch case pour gerer les cas d'un maniere plus fine !
                    zoneMessage.append(msg.getExpediteur()+" : "+msg.getMessage()+"\n");

                    // Permet le debug pour savoir ce qu'il se passe entre le serveur et le client !
                    System.out.println("---ECOUTEUR---");
                    System.out.println("TYPEMESSAGE : "+msg.getTypeMessage());
                    System.out.println("MESSAGE : "+msg.getMessage());
                    System.out.println("EXPEDITEUR : "+msg.getExpediteur());


                    // On affiche une question à la fois
                    if(msg.getTypeMessage().compareTo("QUESTION")==0){

                        buttonReponsePanel.setVisible(true); // Afficher les boutons pour jouer

                        // On doit sauvegarder la liste de Question dans Appication client
                        // Ensuite lors d'un clic sur un des boutons a b c d ou
                        lesQuestions=msg.getLesQuestions();

                        // Afficher la premiere question de la liste
                        msg.getLesQuestions().size(); // la taille des questions
                        zoneMessage.setText(msg.getLesQuestions().get(1).getLaQuestion());


                        for(int i=0;i<=msg.getLesQuestions().get(1).getLesPropositions().size();i++){
                            Reponse uneReponse= (Reponse) msg.getLesQuestions().get(1).getLesPropositions().get(i);
                            zoneMessage.append(i+". "+uneReponse.getTexteReponse()+"\n");
                        }


                    }

                    // Faire disparaitre l'interface de connexion & inscription
                    if(msg.getTypeMessage().compareTo("INSCRIPTION_PARTIE_GOOD")==0){
                        logPanel.setVisible(false);
                    }

                    // Indiquer à l'utilisateur son score
                    if(msg.getTypeMessage().compareTo("FINPARTIE")==0){
                        zoneMessage.setText(msg.getMessage());
                    }


                }
            }catch(java.net.SocketException e){
                this.interrupt();
                e.printStackTrace();
            } catch (IOException e) {
                this.interrupt();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.interrupt();
    }


}
