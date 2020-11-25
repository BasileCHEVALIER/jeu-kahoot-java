package test;
import java.util.Objects;

public class Option implements InformationGenerale{
    private int noOption;
    private String texteOption;
    //private static int nbOptions = 0;

    public Option(String texteOption) {
        //this.noOption = nbOptions;
        this.texteOption = texteOption;
        //this.nbOptions++;
    }

    public void finalize() { // deprecated
        System.out.println("Objet détruit");
        //nbOptions--;
    }

    public int getNoOption() {
        return noOption;
    }

    public void setNoOption(int noOption) {
        this.noOption = noOption;
    }

    public String getTexteOption() {
        return texteOption;
    }

    public void setTexteOption(String texteOption) {
        this.texteOption = texteOption;
    }

   /* public static int getNbOptions() {
        return nbOptions;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option option = (Option) o;
        return getNoOption() == option.getNoOption() &&
                Objects.equals(getTexteOption(), option.getTexteOption());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoOption(), getTexteOption());
    }

    @Override
    public String toString() {
        return "Option{" +
                "noOption=" + noOption +
                ", texteOption='" + texteOption + '\'' +
                '}';
    }

    @Override
    public boolean estUneQuestion() {
        if (this.getTexteOption().indexOf('?') >= 0)
        {
            return true;
        }
        return false;
    }
}