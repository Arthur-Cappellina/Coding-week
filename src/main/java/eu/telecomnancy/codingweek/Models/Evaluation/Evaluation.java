package eu.telecomnancy.codingweek.Models.Evaluation;

import eu.telecomnancy.codingweek.Models.User;
import lombok.Getter;
import lombok.Setter;
import eu.telecomnancy.codingweek.Models.DAO.EvalDAO;

import java.util.List;

@Getter
@Setter
public class Evaluation {
    private User evaluator;
    private User evaluated;
    private int note;
    private String comment;

    public Evaluation(User evaluator, User evaluated, int note, String comment) {
        this.evaluator = evaluator;
        this.evaluated = evaluated;
        this.note = note;
        this.comment = comment;
    }

    public Evaluation() {
    }

    public String toString() {
    	return "Evaluation : " + this.getEvaluator() + " -> " + this.getEvaluated() + "\n" + this.getNote() + "\nCommentaire : " + this.getComment();
    }

    public static double average(User user) {
        List<Evaluation> evaluations = user.getEvaluations();
        if (evaluations == null) {
            return 0;
        }
        if (evaluations.size() == 0) {
            return 0;
        }
        double sum = 0;
        for (Evaluation evaluation : evaluations) {
            sum += evaluation.getNote();
        }
        return sum / evaluations.size();
    }

    public void addEvaluation(int note) {
        EvalDAO evalDAO = EvalDAO.getInstance();
        this.setNote(note);
        evalDAO.insertEvaluation(this);
    }

    public void removeEvaluation() {
        EvalDAO evalDAO = EvalDAO.getInstance();
        evalDAO.deleteEvaluation(this);
    }

    public void editEvaluation(int noteInt) {
        this.removeEvaluation();
        this.addEvaluation(noteInt);
    }

    public boolean isEvaluated() {
        EvalDAO evalDAO = EvalDAO.getInstance();
        return evalDAO.isEvaluated(this.getEvaluator(), this.getEvaluated());
    }

    public static boolean isEvaluationValid(int note) {
        return note >= 0 && note <= 5;
    }
}
