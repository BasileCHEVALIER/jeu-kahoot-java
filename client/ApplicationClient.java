package client;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class ApplicationClient extends JDialog {

    // question 12 les attributs pour l'interface graphique
    private JPanel contentPane;
    private JTextArea textArea1;
    private JTextField textField2;
    private JButton envoyerButton;
    private JButton quitterButton;
    private JTextField textField1;
    private Socket client;
    private PrintWriter writer;


    // question 14
    @Override
    public void dispose() {
        try {
            this.client.close(); // fermeture de la fenêtre proprement
        } catch (IOException e) {
            super.dispose();
            e.printStackTrace();
        }
        super.dispose();
    }


    private void onEnvoyer() throws IOException {

        String nom=textField1.getText();
        String mess=textField2.getText();
        writer.println(nom + " : " + mess);
        writer.flush(); // Permet de vider le buffer

    }

    public ApplicationClient() throws IOException {
        this.client=new Socket("127.0.0.1",4242);
        this.writer=new PrintWriter(new OutputStreamWriter(this.client.getOutputStream()));
        setContentPane(contentPane);
        setModal(true);

        // question 21 démarer et initialiser l'écouteur
        Ecouteur ecouteur = new Ecouteur(textArea1,new BufferedReader(new InputStreamReader(client.getInputStream())));
        ecouteur.start();

        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onEnvoyer();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }




    public static void main(String[] args) throws IOException {
        ApplicationClient dialog = new ApplicationClient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
