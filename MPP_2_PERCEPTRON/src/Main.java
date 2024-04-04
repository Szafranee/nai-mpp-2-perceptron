import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Integer> irisMap = Map.of(
            "Iris-versicolor", 0,
            "Iris-setosa", 1
    );

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
        // train set file selection
        System.out.println("\nYou selected to load test data from file");
        System.out.println("Enter the train set file name: ");
        List<Vector> trainSetVectors = FileHandler.getVectorsListFromFile(scanner);

        System.out.println("Enter the learning rate: ");
        double learningRate = InputValidator.getValidLearningRate(scanner);

        // Initializing and raining the perceptron
        int weightsLength = trainSetVectors.getFirst().vectorValues.size();
        Perceptron perceptron = new Perceptron(weightsLength, learningRate);
        System.out.println(perceptron);

        for (Vector vector : trainSetVectors) {
            int target = irisMap.get(vector.vectorName);
            int guess = perceptron.guess(vector);
            if (guess != target) {
                perceptron.train(vector, guess);
            }
            System.out.println("Guess: " + guess + ", Target: " + target);
        }

        // test set file selection
        System.out.println("Enter the test set file name: ");
        List<Vector> testSetVectors = FileHandler.getVectorsListFromFile(scanner);

        // Testing the perceptron
        int correct = 0;
        int counter = 0;
        for (Vector vector : testSetVectors) {
            int guess = perceptron.guess(vector);
            System.out.println("Guess: " + guess + ", Target: " + vector.vectorName);
            if (irisMap.get(vector.vectorName) == guess) {
                System.out.println("Guessed correctly! EZ 😎");
                correct++;
            } else {
                System.out.println("Guessed incorrectly! Sadge 😔");
            }
            counter++;
            System.out.println(perceptron);
            System.out.println();
        }

        System.out.println("Accuracy: " + (double) correct / testSetVectors.size() * 100 + "%");
    }

    /*private static List<List<Vector>>loadTestDataFromFile(Scanner scanner) {
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
    }*/

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
