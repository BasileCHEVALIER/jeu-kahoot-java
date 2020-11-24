package serveur;

public class testThread extends Thread {

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                System.out.println("je s'appelle groot");
                sleep(1000);

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        interrupt();


    }
}
