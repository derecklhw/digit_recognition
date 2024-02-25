package digit_recognition;

import java.util.Scanner;

public class UserInterface {
    public static int getUserChoice(Scanner scanner) {
        int choice = 0;

        while (true) {
            displayMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Choice must be between 1 and 3. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Select an algorithm for digit recognition:");
        System.out.println("1. Nearest Neighbour");
        System.out.println("2. Multilayer Layer Perceptron");
        System.out.println("3. Exit");
        System.out.print("\nEnter your choice (number): ");
    }

    /**
     * Prints out the accuracy of an algorithm
     * 
     * @param correctPredictions total of correct guessed results
     * @param totalInputs        total inputs number
     */
    public static void printFinalResults(int correctPredictions, int totalInputs) {
        System.out.println("Correct Predictions: " + correctPredictions + " out of " + totalInputs);
        System.out.println("Accuracy: " + (double) ((double) correctPredictions / (double) totalInputs) + "\n");
    }

}
