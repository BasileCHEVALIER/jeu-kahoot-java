package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question extends Option implements Comparable<Question>  {
    private Categorie categorie;
    private List lesPropositions = new ArrayList<Reponse>();
    private Reponse bonneReponse;
    private int identifiant;
    public Question( String texteOption) {
        super( texteOption);
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public Question(String texteOption, Categorie categorie) {
        super( texteOption);
        this.categorie = categorie;
    }

    public Question(String texteOption, Categorie categorie, List<Reponse> lesPropositions, Reponse bonneReponse) {
        super( texteOption);
        this.categorie = categorie;
        this.lesPropositions = lesPropositions;
        this.bonneReponse = bonneReponse;
    }

    @Override
    public int compareTo(Question question) {
        return this.getCategorie().getTexteCategorie().compareTo(question.getCategorie().getTexteCategorie());
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Reponse> getLesPropositions() {
        return lesPropositions;
    }

    public void setLesPropositions(List<Reponse> lesPropositions) {
        this.lesPropositions = lesPropositions;
    }

    public Reponse getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(Reponse bonneReponse) {
        this.bonneReponse = bonneReponse;
    }
    public void addReponse(Reponse r)
    {
        this.bonneReponse=r;
    }
    public void addProposition(Reponse r)
    {
        this.lesPropositions.add(r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return getCategorie().equals(question.getCategorie()) &&
                Objects.equals(getLesPropositions(), question.getLesPropositions()) &&
                getBonneReponse().equals(question.getBonneReponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCategorie(), getLesPropositions(), getBonneReponse());
    }

    @Override
    public String toString() {
        return "\nQuestion{" + getTexteOption() +
                " - categorie=" + categorie +
                ", lesPropositions=" + lesPropositions +
                ", bonneReponse=" + bonneReponse +
                '}';
    }
}
