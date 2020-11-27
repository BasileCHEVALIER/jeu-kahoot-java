package client;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

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
    private JButton cButton;
    private JButton dButton;
    private JButton inscriptionButton;
    private Connexion connexion;
    private Ecouteur ecouteur;

    public ApplicationClient() {

        try {
            connexion=new Connexion(new Socket(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ecouteur = new Ecouteur(zoneMessage,connexion);
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

        // Fonction pour envoyer la réponse a comme réponse
        aButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testOnEnvoie();
            }
        });

        // Fonction pour envoyer la réponse b comme réponse
        bButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });


        // Fonction pour s'inscrire à la partie
        // ok
        inscriptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inscription();
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

        if (login.isEmpty()) {
            zoneMessage.setText("MERCI DE S'INSCRIRE AVEC UN NOM NON NULLE \n");
        } else {
            try {
                connexion.getOos().writeObject(new Message(login,login,"INSCRIPTION"));
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


    private void testOnEnvoie(){
        String login=name.getText();
        String textMessage=saisieTexte.getText();

        try {
            // Message(String expediteur, String message, String typeMessage)
            connexion.getOos().writeObject(new Message(login,"A","REPONSE"));
            connexion.getOos().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        zoneMessage.setText("");
        zoneMessage.setText("Envoi de la réponse a ");
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
