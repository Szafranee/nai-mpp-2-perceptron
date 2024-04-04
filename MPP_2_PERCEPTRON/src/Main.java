import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Map<String, Integer> irisMap = Map.of(
            "Iris-versicolor", 0,
            "Iris-setosa", 1
    );

    public static void ui() {
        System.out.println("===========================================");
        System.out.println("           Perceptron classifier"           );
        System.out.println("===========================================");
        Scanner scanner = new Scanner(System.in);
        boolean continueChoice;

        List<Vector> trainSetVectors;
        List<Vector> testSetVectors = null;

        System.out.println("\nFirst, we need to create and train a perceptron! 🦫\n");

        System.out.println("Enter the train set file name: ");
        trainSetVectors = FileHandler.getVectorsListFromFile(scanner);

        //Initializing and training the perceptron
        Perceptron perceptron = createPerceptron(scanner, trainSetVectors);

        do {
            System.out.println("\n-------------------Menu--------------------");
            System.out.println("Please select one of the following options: ");
            System.out.println("1. Load test data from file");
            System.out.println("2. Enter test data from console");
            System.out.println("3. Exit");
            System.out.println("-------------------------------------------\n");
            System.out.print("Enter your choice: ");
            System.out.println("Current perceptron:");

            int choice = InputValidator.getValidIntInput(scanner, 1, 3);

            System.out.print("Do you want to continue? (y/n) ");
            continueChoice = InputValidator.getYesOrNo(scanner);
            if (!continueChoice) {
                continueChoice = true;
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\nYou selected to load test data from file");
                    if (testSetVectors == null) {
                        System.out.println("Enter the test set file name: ");
                        testSetVectors = FileHandler.getVectorsListFromFile(scanner);
                    }
                    handleTestDataFromFile(scanner, testSetVectors, perceptron);
                }
                case 2 -> enterTestDataFromConsole(trainSetVectors, perceptron, scanner);
                case 3 -> {
                    System.out.println("Goodbye! 🫡");
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

    private static Perceptron createPerceptron(Scanner scanner, List<Vector> trainSetVectors) {
        System.out.println("Enter the learning rate: ");
        double learningRate = InputValidator.getValidLearningRate(scanner);

        int weightsLength = trainSetVectors.getFirst().vectorValues.size();
        Perceptron perceptron = new Perceptron(weightsLength, learningRate);

        perceptron.trainOnTrainSet(trainSetVectors, irisMap);
        return perceptron;
    }

    private static void handleTestDataFromFile(Scanner scanner, List<Vector> testSetVectors, Perceptron perceptron) {

        // Testing the perceptron
        Map<Integer, Map.Entry<Integer, Integer>> classSummary = perceptron.testOnTestSetAndGetAmountOfCorrectGuesses(testSetVectors, irisMap);

        // Printing the results
        printResults(classSummary);

        System.out.println("Do you want to do a round of learning and try again? (y/n) ");
        boolean continueChoice = InputValidator.getYesOrNo(scanner);
        if (continueChoice) {
            System.out.println("Did a round of learning!🧠");
            handleTestDataFromFile(scanner, testSetVectors, perceptron);
        }
    }


    private static void printResults(Map<Integer, Map.Entry<Integer, Integer>> classSummary) {
        double classZeroAccuracy = (double) classSummary.get(0).getValue() / classSummary.get(0).getKey() * 100;
        String classZeroName = getClassFromMap(irisMap, 0);

        double classOneAccuracy = (double) classSummary.get(1).getValue() / classSummary.get(1).getKey() * 100;
        String classOneName = getClassFromMap(irisMap, 1);

        double totalAccuracy = (double) classSummary.get(2).getValue() / classSummary.get(2).getKey() * 100;

        System.out.println("Summary: ");
        System.out.printf("%s: %s/%s correct guesses, accuracy: %.2f%%%n", classZeroName, classSummary.get(0).getValue(), classSummary.get(0).getKey(), classZeroAccuracy);
        System.out.printf("%s: %s/%s correct guesses, accuracy: %.2f%%%n", classOneName, classSummary.get(1).getValue(), classSummary.get(1).getKey(), classOneAccuracy);
        System.out.printf("Total: %s/%s correct guesses, accuracy: %.2f%%%n", classSummary.get(2).getValue(), classSummary.get(2).getKey(), totalAccuracy);
        System.out.println();
    }


    private static void enterTestDataFromConsole(List<Vector> trainSetVectors, Perceptron perceptron, Scanner scanner) {
        System.out.println("\nYou selected to enter test data from console");

        do {
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Enter a vector to classify or 'q' to quit:");
            String input = inputScanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }

            Vector vector;
            String[] inputVectorString = input.split(",");
            if (inputVectorString.length < trainSetVectors.getFirst().vectorValues.size()) {
                System.out.println("Invalid input. Please enter a vector with " + trainSetVectors.getFirst().vectorValues.size() + " values.");
                continue;
            } else if (inputVectorString.length > trainSetVectors.getFirst().vectorValues.size() + 1) {
                System.out.println("Invalid input. Please enter a vector with " + trainSetVectors.getFirst().vectorValues.size() + " values.");
                continue;
            } else if (inputVectorString.length == trainSetVectors.getFirst().vectorValues.size()) {
                vector = new Vector(Arrays.stream(inputVectorString).map(Double::parseDouble).collect(Collectors.toList()));
            } else if (inputVectorString.length == trainSetVectors.getFirst().vectorValues.size() + 1)  {
                vector = new Vector(Arrays.stream(inputVectorString).limit(inputVectorString.length - 1).map(Double::parseDouble).collect(Collectors.toList()), inputVectorString[inputVectorString.length - 1]);
            } else {
                System.out.println("Invalid input. Please enter a vector with " + trainSetVectors.getFirst().vectorValues.size() + " values.");
                continue;
            }

            int guess = perceptron.guess(vector);
            if (vector.vectorName != null) {
                System.out.println("Guess: " + getClassFromMap(irisMap, guess) + ", Target: " + vector.vectorName);
                if (irisMap.get(vector.vectorName) == guess) {
                    System.out.println("Guessed correctly! EZ 😎\n");
                } else {
                    System.out.println("Guessed incorrectly! Sadge 😔\n");
                }
            } else {
                System.out.println("Guess: " + getClassFromMap(irisMap, guess));
            }

            System.out.println("Do you want to do a round of learning? (y/n) ");
            boolean continueChoice = InputValidator.getYesOrNo(scanner);
            if (continueChoice) {
                perceptron.trainOnTrainSet(trainSetVectors, irisMap);
                System.out.println("Did a round of learning!🧠\n");
            }

        } while (true);
    }

    public static void main(String[] args) {
        ui();
    }

    public static String getClassFromMap(Map<String, Integer> targetMap, int value) {
        return targetMap.entrySet().stream().filter(entry -> entry.getValue() == value).findFirst().get().getKey();
    }
}
