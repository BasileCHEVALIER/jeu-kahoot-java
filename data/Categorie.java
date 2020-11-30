package data;

import java.util.Objects;

/*
* CATEGORIE = ID + TEXTE
 */
public class Categorie implements Comparable{

    private String texteCategorie;
    private int idCategorie;

    public Categorie(String texteCategorie) {
        this.texteCategorie = texteCategorie;
    }

    public Categorie(String texteCategorie, int idCategorie) {
        this.texteCategorie = texteCategorie;
        this.idCategorie = idCategorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getTexteCategorie() {
        return texteCategorie;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "texteCategorie='" + texteCategorie + '\'' +
                ", idCategorie=" + idCategorie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return Objects.equals(texteCategorie, categorie.texteCategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texteCategorie);
    }

    @Override
    public int compareTo(Object o) {
        Categorie c = (Categorie) o;
        return this.texteCategorie.compareTo(c.texteCategorie);

    }
}
