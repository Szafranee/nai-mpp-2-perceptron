import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    protected static int getValidIntInput(Scanner scanner, int min, int max) {
        int choice = 0;
        boolean isChoiceCorrect = false;
        while (!isChoiceCorrect) {
            try {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    isChoiceCorrect = true;
                } else {
                    System.out.print("Invalid choice. Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
                scanner.next();
            }
        }
        return choice;
    }

    protected static boolean getYesOrNo(Scanner scanner) {
        String continueChoice = scanner.next();

        while (true) {
            if (continueChoice.equalsIgnoreCase("y")) {
                return true;
            } else if (continueChoice.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.print("Invalid choice. Please enter 'y' or 'n': ");
                continueChoice = scanner.next();
            }
        }
    }

    protected static double getValidLearningRate(Scanner scanner) {
        double learningRate = 0;
        boolean isChoiceCorrect = false;
        while (!isChoiceCorrect) {
            try {
                learningRate = scanner.nextDouble();
                if (learningRate > 0) {
                    isChoiceCorrect = true;
                } else {
                    System.out.print("Invalid choice. Please enter a positive number: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a positive number: ");
                scanner.next();
            }
        }
        return learningRate;
    }
}
