package test;

public class Reponse extends Option {
    private int identifiant;

    public Reponse(String texteOption) {
        super(texteOption);
    }

    @Override
    public String toString() {
        return this.getTexteOption();
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}
