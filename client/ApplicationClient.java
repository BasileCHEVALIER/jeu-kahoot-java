package client;

import data.Question;
import data.Reponse;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;

public class ApplicationClient extends JDialog {
    private static final String host = "localhost";
    private static final int port = 60000;
    private JPanel contentPane;
    private JButton buttonEnvoie;
    private JTextArea zoneMessage;
    private JTextField name;
    private JTextField saisieTexte;
    private JButton bButton;
    private JButton aButton;
    private JButton a2Button;
    private JButton a3Button;
    private JButton inscriptionButton;
    private JButton connexionButton;
    private JButton quitterButton;
    private JPanel logPanel;
    private JPanel buttonReponsePanel;
    private Connexion connexion;
    private Ecouteur ecouteur;


    public List<Question> lesQuestions ;

    public void maj(List<Question> lesQuestions) {
        this.lesQuestions = lesQuestions;
    }

    public ApplicationClient() {

        try {
            connexion=new Connexion(new Socket(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ecouteur = new Ecouteur(zoneMessage,connexion,inscriptionButton,connexionButton,name,saisieTexte,logPanel,buttonReponsePanel,lesQuestions);
        ecouteur.start();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonEnvoie);



        buttonEnvoie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEnvoie();
            }
        });

        /* Les boutons supplementaires */

        aButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                REPONSE(0);
            }
        });

        bButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                REPONSE(1);
            }
        });

        a2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                REPONSE(2);
            }
        });

        a3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                REPONSE(3);
            }
        });

        inscriptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inscription();
            }
        });

        connexionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seConnecter();
            }
        });


        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connexion.close();
                //ecouteur.join(10);
                ecouteur.interrupt();
                onCancel();

            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }




    public void REPONSE(int intrepUser){

        // cacher les bouton
        buttonReponsePanel.setVisible(false);

        int score = ecouteur.getScore() ; // Par défaut utilisateur a faux !

        System.out.println("L'utilisateur a repondu : "+intrepUser);

        lesQuestions=ecouteur.getLesQuestions();
        System.out.println("okkkk");
        System.out.println(lesQuestions.toString());

        // Prevenir l'utilisateur si il a la bonne réponse
        Question laQuestion=lesQuestions.get(0);

        // On a la reponse de l'utilisateur qui est la place 1 dans la liste
        // On va donc chercher dans les propositions id de la réponse 1
        Reponse uneReponse= (Reponse) laQuestion.getLesPropositions().get(intrepUser);
        System.out.println(uneReponse.getTexteReponse());
        if(laQuestion.getBonneReponse().getIdReponse()==uneReponse.getIdReponse()){
            zoneMessage.setText("Bonne Reponse !!");
            score++;

        }else{
            zoneMessage.setText("Mauvaise reponse");
        }
/*
        try {
            ecouteur.attente();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


 */
        String login = name.getText();
        try {
            connexion.getOos().writeObject(new Message(login,"JE_VEUX_LA_QUESTION_SUIVANTE","QUESTION",lesQuestions,score));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            connexion.getOos().flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    /*
    * Function : private void inscription()
    * When user clic on Insciption this fonction send object message to the server
    * with message INSCRIPTION.
    *
    * parameters required :
    * Login type String
    *
    * Warning :
    * If you login is null. No connexion !!
    * */
    private void inscription() {
        String login = name.getText();
        String mdp = saisieTexte.getText();

        if (login.isEmpty() ){
            zoneMessage.setText("MERCI DE S'INSCRIRE AVEC UN NOM NON NUL \n");
        }else if(mdp.isEmpty()){
            zoneMessage.setText("MERCI DE S'INSCRIRE AVEC UN MDP NON NUL \n");
        } else {
            try {
                connexion.getOos().writeObject(new Message(login,mdp,"INSCRIPTION"));
                connexion.getOos().flush();

                // Rendre invisible la zone inscription à remettre par la suite
                //inscriptionButton.setVisible(false);
                //saisieTexte.setVisible(false);
                //name.setVisible(false);

                System.out.println("Client send inscription ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /*
     * Function : private void seConnecter()
     * When user clic on Connexion this fonction send object message to the server
     * with message SECONNECTER.
     *
     * parameters required :
     * Login type String
     *
     * Warning :
     * If you login is null. No connexion !!
     * */
    private void seConnecter() {
        String login = name.getText();
        String mdp = saisieTexte.getText();

        if (login.isEmpty() ){
            zoneMessage.setText("MERCI DE S'INSCRIRE AVEC UN NOM NON NUL \n");
        }else if(mdp.isEmpty()){
            zoneMessage.setText("MERCI DE S'INSCRIRE AVEC UN MDP NON NUL \n");
        } else {
            try {
                connexion.getOos().writeObject(new Message(login,mdp,"SECONNECTER"));
                connexion.getOos().flush();

                System.out.println("Client send inscription ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void onEnvoie(){
        String login=name.getText();
        String message=saisieTexte.getText();
        try {
            connexion.getOos().writeObject(new Message(login,message,"ENVOYER"));
            connexion.getOos().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saisieTexte.setText("");
    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (connexion!=null)
        {
            System.out.println("DISPOSE");
            ecouteur.interrupt();
            connexion.close();
        }
    }

    public static void main(String[] args) {
        ApplicationClient dialog = new ApplicationClient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


}

