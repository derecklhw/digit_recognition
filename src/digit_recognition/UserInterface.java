package digit_recognition;

import java.util.Scanner;

/**
 * Class responsible for the user interface.
 */
public class UserInterface {
    /**
     * Main method of the application.
     *
     * @param args command line arguments
     */
    public static int getUserChoice(Scanner scanner) {
        int choice = 0;
        // Loop until the user enters a valid choice
        while (true) {
            // Display the menu
            displayMenu();
            // Get the user's choice
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Choice must be between 1 and 3. Please try again.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.\n");
                scanner.next();
            }
        }
    }

    /**
     * Displays the menu for the user.
     */
    private static void displayMenu() {
        System.out.println("Select an algorithm for digit recognition:");
        System.out.println("1. K-Nearest Neighbour");
        System.out.println("2. Multilayer Layer Perceptron");
        System.out.println("3. Exit");
        System.out.print("\nEnter your choice (number): ");
    }

    /**
     * Method that prints the final results of the application.
     *
     * @param correctPredictions the number of correct predictions
     * @param totalInputs        the total number of inputs
     * @return the accuracy of the application
     */
    public static double printFinalResults(int correctPredictions, int totalInputs) {
        double accuracy = (double) ((double) correctPredictions / totalInputs);
        System.out.println("Correct Predictions: " + correctPredictions + " out of " + totalInputs);
        System.out.println("Accuracy: " + accuracy + "\n");
        return accuracy;
    }

}
