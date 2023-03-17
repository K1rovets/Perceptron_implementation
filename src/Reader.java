import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
    private static int length; // number of conditional attributes

    public static ArrayList<Case> parse(String path) {
        ArrayList<Case> cases = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(",", ".").trim();
                String[] strings = line.split("\\s+");
                length = strings.length - 1;
                double[] values = new double[length];
                for (int i = 0; i < values.length; i++) {
                    values[i] = Double.parseDouble(strings[i].trim());
                }
                if (strings[length].equals("Iris-setosa")) {
                    cases.add(new Case(strings[length], values));
                } else {
                    cases.add(new Case("Iris-virginica/Iris-versicolor", values));
                }
            }
        } catch (IOException exception) {
            System.out.println("FILE NOT FOUND");
        }
        return  cases;
    }

    public static int getLength() {
        return length;
    }
}
