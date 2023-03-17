import java.util.*;

public class Perceptron {
    Random random = new Random();
    private static final HashMap<String, Integer> checker = new HashMap<>();

    private final ArrayList<Case> training_set;
    private final ArrayList<Case> test_set;
    private final double[] weights;
    private final double threshold;
    private final double alpha;
    private final int iterations;

    public Perceptron(int iterations, double alpha) {
        training_set = Reader.parse("iris_training.txt");
        test_set = Reader.parse("iris_test.txt");
        threshold = random.nextDouble(10);
        this.alpha = alpha;
        System.out.println("Threshold = " + threshold);
        weights = new double[Reader.getLength()];
        for (int d = 0; d < weights.length; d++) {
            weights[d] = random.nextDouble(10);
        }
        this.iterations = iterations;
        checker.put("Iris-setosa", 1);
        checker.put("Iris-virginica/Iris-versicolor", 0);
    }

    public void train() {
        int totalError = 0;
        for (int i = 0; i < iterations; i++) {

            for (Case c : training_set) {
                int y = checkNet(c);
                int d = 0;

                for (Map.Entry<String, Integer> entry : checker.entrySet()) {
                    if (entry.getKey().equals(c.getDecAtt())) {
                        d = entry.getValue();
                    }
                }

                int deltaD = d - y;
                totalError += deltaD;

                for (int j = 0; j < weights.length; j++) {
                    weights[j] += c.getConAtt()[j] * deltaD * alpha;
                }
            }
            if (totalError == 0) break;
        }
    }

    public void run(boolean info) {
        int predictedAnswer;
        String predictedType = null;
        int total = 0;
        int correct = 0;
        for (Case c : test_set) {
            predictedAnswer = checkNet(c);
            for (Map.Entry<String, Integer> entry : checker.entrySet()) {
                if (predictedAnswer == entry.getValue()) {
                    predictedType = entry.getKey();
                }
            }
            total++;
            if (c.getDecAtt().equals(predictedType)) {
                correct++;
            }
            if (info) {
                System.out.println(c + " predicted type --> " + predictedType);
            }
        }
        if (!info) {
            System.out.println("Last entry info:");
            String prediction = null;
            for (Map.Entry<String, Integer> entry : checker.entrySet()) {
                if (checkNet(test_set.get(test_set.size()-1)) == entry.getValue()) {
                    prediction = entry.getKey();
                }
            }
            System.out.println(test_set.get(test_set.size()-1) + " predicted type --> " + prediction);
        }
        double accuracy = (double) correct/total * 100.0;
        System.out.println("Total = " + total + "; Predicted correct = " + correct + "; Accucary = " + accuracy);
    }

    public void addCase(Case c) {
        test_set.add(c);
    }

    private int checkNet(Case c) {
        double net = 0;
        for (int i = 0; i < c.getConAtt().length; i++) {
            net += c.getConAtt()[i] * weights[i];
        }
        return net >= threshold ? 1:0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Perceptron perceptron = new Perceptron(100, 0.5);
        perceptron.train();
        perceptron.run(true);
        System.out.println(checker.entrySet());
        System.out.println("Threshold = " + perceptron.threshold);
        for (double d : perceptron.weights) {
            System.out.print("weight = " + d + " ");
        }
        System.out.println();
        while(true) {
            System.out.print("Entry new case ? (N to exit) --> ");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("N")) {
                break;
            }
            line = line.replaceAll(",", ".").trim();
            String[] strings = line.split("\\s+");

            double[] values = new double[strings.length - 1];
            for (int i = 0; i < values.length; i++) {
                values[i] = Double.parseDouble(strings[i].trim());
            }
            if (strings[strings.length-1].equals("Iris-setosa")) {
                perceptron.addCase(new Case(strings[strings.length-1], values));
            } else {
                perceptron.addCase(new Case("Iris-virginica/Iris-versicolor", values));
            }
            perceptron.run(false);
        }
    }
}
