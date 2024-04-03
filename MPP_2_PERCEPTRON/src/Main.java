import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void ui() {
        System.out.println("================================");
        System.out.println("    Perceptron classifier");
        System.out.println("================================");
        Scanner scanner = new Scanner(System.in);
        boolean continueChoice = false;

        do {
            System.out.println("-------------------Menu--------------------");
            System.out.println("Please select one of the following options: ");
            System.out.println("1. Load test data from file");
            System.out.println("2. Enter test data from console");
            System.out.println("3. Exit");
            System.out.println("-------------------------------------------");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = InputValidator.getValidIntInput(scanner, 1, 3);

            System.out.print("Do you want to continue? (y/n) ");
            continueChoice = InputValidator.getYesOrNo(scanner);
            if (!continueChoice) {
                continueChoice = true;
                continue;
            }

            switch (choice) {
                case 1 -> handleLoadTestDataFromFile(scanner);
                case 2 -> enterTestDataFromConsole(scanner);
                case 3 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
            }

            System.out.println("Do you want to do another classification? (y/n) ");
            continueChoice = InputValidator.getYesOrNo(scanner);
            if (!continueChoice) {
                System.out.println("Goodbye!");
            }

        } while (continueChoice);

    }

    private static void handleLoadTestDataFromFile(Scanner scanner) {
        Map<String, Integer> map = Map.of("Iris-setosa", 0, "Iris-virginica", 1);
        List<List<Vector>> vectors = loadTestDataFromFile(scanner);
        List<Vector> trainSetVectors = vectors.get(0);
        List<Vector> testSetVectors = vectors.get(1);

        System.out.println("Enter the learning rate: ");
        double learningRate = InputValidator.getValidLearningRate(scanner);

        // Training the perceptron
        Perceptron perceptron = new Perceptron(trainSetVectors.getFirst().vectorValues.size(), learningRate);
        for (Vector vector : trainSetVectors) {
            perceptron.train(vector);
        }

        // Testing the perceptron
        int correct = 0;
        for (Vector vector : testSetVectors) {
            double[] inputs = vector.vectorValues.stream().mapToDouble(Double::doubleValue).toArray();
            int guess = perceptron.guess(inputs);
            System.out.println("Guess: " + guess + ", Target: " + vector.vectorName);
            if (map.get(vector.vectorName) == guess) {
                correct++;
            }
        }

        System.out.println("Accuracy: " + (double) correct / testSetVectors.size() * 100 + "%");
    }

    private static List<List<Vector>> loadTestDataFromFile(Scanner scanner) {
        System.out.println("\nYou selected to load test data from file");
        System.out.println("Enter the train set file name: ");
        String trainSetFileName = scanner.next();
        File trainSetFile = FileHandler.validateFile(trainSetFileName);
        List<Vector> trainSetVectors = FileHandler.readVectorsFromFile(trainSetFile);
        //System.out.println("Train set vectors: " + trainSetVectors);

        System.out.println("Enter the test set file name: ");
        String testSetFileName = scanner.next();
        File testSetFile = FileHandler.validateFile(testSetFileName);
        List<Vector> testSetVectors = FileHandler.readVectorsFromFile(testSetFile);
        //System.out.println("Test set vectors: " + testSetVectors);

        //System.out.println(List.of(trainSetVectors, testSetVectors));
        return List.of(trainSetVectors, testSetVectors);
    }

    private static void enterTestDataFromConsole(Scanner scanner) {
        System.out.println("\nYou selected to enter test data from console");
        System.out.println("Enter the train set file name: ");
        String trainSetFileName = scanner.next();
        File trainSetFile = FileHandler.validateFile(trainSetFileName);
        List<Vector> trainSetVectors = FileHandler.readVectorsFromFile(trainSetFile);
    }

    public static void main(String[] args) {
        ui();
    }
}
