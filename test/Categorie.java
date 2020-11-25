package test;

import java.util.Objects;

public class Categorie {
    private String texteCategorie;
    private int identifiant;

    public Categorie(String texteCategorie) {
        this.texteCategorie = texteCategorie;
    }

    public String getTexteCategorie() {
        return texteCategorie;
    }

    public void setTexteCategorie(String texteCategorie) {
        this.texteCategorie = texteCategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie categorie = (Categorie) o;
        return getTexteCategorie().equals(categorie.getTexteCategorie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTexteCategorie());
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "texteCategorie='" + texteCategorie + '\'' +
                ", identifiant=" + identifiant +
                '}';
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}