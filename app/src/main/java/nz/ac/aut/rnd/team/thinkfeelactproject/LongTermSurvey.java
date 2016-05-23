package nz.ac.aut.rnd.team.thinkfeelactproject;

/**
 * Created by Aida on 20/05/2016.
 */
public class LongTermSurvey {
    private int ID;
    private String answerTF;
    private int rating;
    private int questionId;

    public LongTermSurvey() {

    }

    public LongTermSurvey(String answerTF, int rating, int questionId) {
        this.answerTF = answerTF;
        this.rating = rating;
        this.questionId = questionId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAnswerTF() {
        return answerTF;
    }

    public void setAnswerTF(String answerTF) {
        this.answerTF = answerTF;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
