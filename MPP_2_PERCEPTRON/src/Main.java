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
                case 1 -> handleTestDataFromFile(scanner);
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

    private static void handleTestDataFromFile(Scanner scanner) {
        // train set file selection
        System.out.println("\nYou selected to load test data from file");
        System.out.println("Enter the train set file name: ");
        List<Vector> trainSetVectors = FileHandler.getVectorsListFromFile(scanner);

        System.out.println("Enter the learning rate: ");
        double learningRate = InputValidator.getValidLearningRate(scanner);

        // Initializing and training the perceptron
        int weightsLength = trainSetVectors.getFirst().vectorValues.size();
        Perceptron perceptron = new Perceptron(weightsLength, learningRate);
//        System.out.println(perceptron);
        perceptron.trainOnTrainSet(trainSetVectors, irisMap);

        // test set file selection
        System.out.println("Enter the test set file name: ");
        List<Vector> testSetVectors = FileHandler.getVectorsListFromFile(scanner);

        // Testing the perceptron
        Map<Integer, Map.Entry<Integer, Integer>> classSummary = perceptron.testOnTestSetAndGetAmountOfCorrectGuesses(testSetVectors, irisMap);

        // Printing the results
        printResults(classSummary);
    }

    private static void printResults(Map<Integer, Map.Entry<Integer, Integer>> classSummary) {
        double classZeroAccuracy = (double) classSummary.get(0).getValue() / classSummary.get(0).getKey() * 100;
        String classZeroName = getClassFromMap(irisMap, 0);

        double classOneAccuracy = (double) classSummary.get(1).getValue() / classSummary.get(1).getKey() * 100;
        String classOneName = getClassFromMap(irisMap, 1);

        double totalAccuracy = (double) classSummary.get(2).getValue() / classSummary.get(2).getKey() * 100;

        System.out.println("Summary: ");
        System.out.printf("%s: %s out of %s correct guesses, accuracy: %.2f%%%n", classZeroName, classSummary.get(0).getValue(), classSummary.get(0).getKey(), classZeroAccuracy);
        System.out.printf("%s: %s out of %s correct guesses, accuracy: %.2f%%%n", classOneName, classSummary.get(1).getValue(), classSummary.get(1).getKey(), classOneAccuracy);
        System.out.printf("Total: %s out of %s correct guesses, accuracy: %.2f%%%n", classSummary.get(2).getValue(), classSummary.get(2).getKey(), totalAccuracy);
        System.out.println();
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

    public static String getClassFromMap(Map<String, Integer> targetMap, int value) {
        return targetMap.entrySet().stream().filter(entry -> entry.getValue() == value).findFirst().get().getKey();
    }
}
