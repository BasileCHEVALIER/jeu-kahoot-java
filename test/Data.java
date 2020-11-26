package test;

public class Data {

    private String laQuestion ;
    private String laReponse;

    public Data(){
        this.laQuestion="";
        this.laReponse="";
    }

    public Data(String laQuestion,String laReponse){
        this.laQuestion=laQuestion;
        this.laReponse=laReponse;
    }

    public String getLaQuestion() {
        return laQuestion;
    }

    public void setLaQuestion(String laQuestion) {
        this.laQuestion = laQuestion;
    }
}
