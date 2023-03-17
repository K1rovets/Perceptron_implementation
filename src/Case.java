import java.util.Arrays;

public class Case {
    private String DecAtt;
    private double[] ConAtt;

    public Case(String decAtt, double[] conAtt) {
        this.DecAtt = decAtt;
        this.ConAtt = conAtt;
    }

    public String getDecAtt() {
        return DecAtt;
    }

    public double[] getConAtt() {
        return ConAtt;
    }

    @Override
    public String toString() {
        return DecAtt + Arrays.toString(ConAtt);
    }
}
