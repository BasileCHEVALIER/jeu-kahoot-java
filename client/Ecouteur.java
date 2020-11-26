package client;

import javax.swing.*;
import java.io.IOException;

public class Ecouteur extends Thread {

    private JTextArea zoneMessage;
    private Connexion connexion;

    public Ecouteur(JTextArea zoneMessage,Connexion connexion) {
        this.zoneMessage = zoneMessage;
        this.connexion = connexion;
    }

    @Override
    public void run() {
        while (!this.currentThread().isInterrupted()){
            try {
                Message msg = (Message) connexion.getOis().readObject();
                if (msg!=null){

                    zoneMessage.append(msg.getExpediteur()+" : "+msg.getMessage()+"\n");

                    // Methode rudimentaire pour presenter
                    // On va definir plusieurs étapes !
                    // 1 => Présentation du fonctionnemnt du Kahoot
                    // 2 =>

                    if(msg.getMessage().equals("INSCRIPTION")){
                        zoneMessage.setText("Hello tu es inscris  ");
                        System.out.println("Ecouteur : etape 1 ");
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
