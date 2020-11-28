package client;

import javax.swing.*;
import java.io.IOException;


public class Ecouteur extends Thread {

    private JTextArea zoneMessage;
    private Connexion connexion;


    // Ajout des variables suivantes pour les
    private JButton inscriptionButton;
    private JButton connexionButton;
    private JTextField name;
    private JTextField saisieTexte;


    public Ecouteur(JTextArea zoneMessage,Connexion connexion,JButton inscriptionButton, JButton connexionButton,JTextField name,JTextField saisieTexte) {
        this.zoneMessage = zoneMessage;
        this.connexion = connexion;

        // Les nouveaux champs
        this.inscriptionButton = inscriptionButton;
        this.connexionButton = connexionButton;
        this.name=name;
        this.saisieTexte=saisieTexte;


    }



    @Override
    public void run() {
        while (!this.currentThread().isInterrupted()){
            try {
                Message msg = (Message) connexion.getOis().readObject();
                if (msg!=null){

                    zoneMessage.append(msg.getExpediteur()+" : "+msg.getMessage()+"\n");

                    System.out.println("---ECOUTEUR---");
                    System.out.println("TYPEMESSAGE : "+msg.getTypeMessage());
                    System.out.println("MESSAGE : "+msg.getMessage());
                    System.out.println("EXPEDITEUR : "+msg.getExpediteur());


                    if(msg.getTypeMessage()==("startGame")){
                        zoneMessage.setText(msg.getMessage());
                    }

                    if(msg.getTypeMessage()==("FULLSERVER")){
                        // Bloquer les boutons
                        // Pour emepeche l'utilisateur de faire une nouvelle requete

                    }

                    // Faire disparaitre l'interface de connexion
                    if(msg.getTypeMessage().compareTo("INSCRIPTION_PARTIE_GOOD")==0){
                        System.out.println("inx");
                        inscriptionButton.setVisible(false);;
                        connexionButton.setVisible(false);
                        name.setVisible(false);
                        saisieTexte.setVisible(false);

                    }



                    /*
                    TO DO....
                    if(msg.getTypeMessage()==("QUITTER")){
                        // Bloquer les boutons
                        // Pour emepeche l'utilisateur de faire une nouvelle requete
                        System.out.println("quiter");
                        this.connexion.close();

                        this.interrupt();
                    }
                    */




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
