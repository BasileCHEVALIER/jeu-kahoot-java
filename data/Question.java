package data;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private List lesPropositions = new ArrayList<Reponse>();
    private Reponse bonneReponse;
    private String laQuestion;
    private int idQuestion;

    public List getLesPropositions() {
        return lesPropositions;
    }

    public void setLesPropositions(List lesPropositions) {
        this.lesPropositions = lesPropositions;
    }

    public Reponse getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(Reponse bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public String getLaQuestion() {
        return laQuestion;
    }

    public void setLaQuestion(String laQuestion) {
        this.laQuestion = laQuestion;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public String toString() {
        return "Question{" +
                "lesPropositions=" + lesPropositions +
                ", bonneReponse=" + bonneReponse +
                ", laQuestion='" + laQuestion + '\'' +
                ", idQuestion=" + idQuestion +
                '}';
    }
}
