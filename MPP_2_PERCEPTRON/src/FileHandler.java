import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    public static File validateFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist. Please enter a valid file name: ");
            Scanner scanner = new Scanner(System.in);
            String newFileName = scanner.next();
            return validateFile(newFileName);
        }
        return file;
    }


    public static List<Vector> readVectorsFromFile(File file) {
        List<Vector> vectors = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                ArrayList<Double> vector = new ArrayList<>();
                boolean hasName = false;
                try {
                    Double.parseDouble(values[values.length - 1]);
                } catch (NumberFormatException e) {
                    hasName = true;
                }
                for (int i = 0; i < values.length - (hasName ? 1 : 0); i++) {
                    vector.add(Double.parseDouble(values[i]));
                }
                String name = hasName ? values[values.length - 1] : null;
                vectors.add(new Vector(vector, name));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return vectors;
    }

    public static List<Vector> getVectorsListFromFile(Scanner scanner) {
        String fileName = scanner.next();
        File file = validateFile(fileName);
        return readVectorsFromFile(file);
    }
}
