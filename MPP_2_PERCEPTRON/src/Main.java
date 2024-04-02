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
                case 1 -> loadTestDataFromFile(scanner);
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

    private static void enterTestDataFromConsole(Scanner scanner) {
        System.out.println("Entering test data from console...");
    }

    private static void loadTestDataFromFile(Scanner scanner) {
        System.out.println("Loading test data from file...");
    }

    public static void main(String[] args) {
        ui();
    }
}
