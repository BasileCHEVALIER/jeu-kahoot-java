package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Ecouteur extends Thread {
    private JTextArea textArea;
    private BufferedReader bufferReader;


    // question 20, ajoutez une classe Ecouteur
    // qui représente un écouteur en attente sur un flux de texte
    // en entrée (de type BufferedReader), et qui affiche le texte reçu
    // sur un objet de type JTextArea.
    public Ecouteur(JTextArea textArea, BufferedReader bufferReader) {

        // pourquoi pas faire la même chose avec des objets
        this.textArea = textArea;
        this.bufferReader = bufferReader;


    }

    @Override
    public void run() {
            try {
                // ecoute tout le temps
                while(true) {
                    String line = bufferReader.readLine();
                    if(line!=null){
                        textArea.append(line+'\n');
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

