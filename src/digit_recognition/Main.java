package digit_recognition;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main class of the application.
 */
public class Main {
	/**
	 * Main method of the application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		// Check if the datasets are provided
		if (!validateArguments(args)) {
			return;
		}
		String datasetOne = args[0];
		String datasetTwo = args[1];

		// Load and execute the user's choice
		loadAndExecute(datasetOne, datasetTwo);
	}

	/**
	 * Validates the command line arguments.
	 *
	 * @param args command line arguments
	 * @return true if the arguments are valid, false otherwise
	 */
	private static boolean validateArguments(String[] args) {
		// Check if the datasets are provided
		if (args.length < 2) {
			System.out.println("Usage: java -jar digit_recognition.jar <dataset1> <dataset2>");
			if (args.length == 0) {
				System.out.println("No datasets provided.");
				return false;
			} else {
				System.out.println(
						"Only one dataset provided. Please provide two datasets. Usage: java -jar digit_recognition.jar <dataset1> <dataset2>");
				return false;
			}
		}

		// Check if the datasets are different
		if (args[0].equals(args[1])) {
			System.out.println(
					"The datasets are the same. Please provide two different datasets. Usage: java -jar digit_recognition.jar <dataset1> <dataset2>");
			return false;
		}
		return true;
	}

	/**
	 * Loads and executes the user's choice.
	 *
	 * @param datasetOnePath the path to the first dataset
	 * @param datasetTwoPath the path to the second dataset
	 */
	private static void loadAndExecute(String datasetOnePath, String datasetTwoPath) {
		try {
			// Read the datasets
			int[][] datasetOne = DatasetReader.readDataset(datasetOnePath);
			int[][] datasetTwo = DatasetReader.readDataset(datasetTwoPath);
			System.out.printf("\nDatasets loaded\nDataset 1: %s\nDataset 2: %s\n\n", datasetOnePath,
					datasetTwoPath);

			// Execute the user's choice
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					int choice = UserInterface.getUserChoice(scanner);
					if (!executeChoice(choice, datasetOne, datasetTwo))
						break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + ". Please provide a valid path to the dataset.");
		}
	}

	/**
	 * Executes the user's choice.
	 *
	 * @param choice     the user's choice
	 * @param datasetOne the first dataset
	 * @param datasetTwo the second dataset
	 * @return true if the user wants to continue, false otherwise
	 */
	private static boolean executeChoice(int choice, int[][] datasetOne, int[][] datasetTwo) {
		switch (choice) {
			// execute the K-Nearest Neighbour algorithm
			case 1:
				System.out.println("--------------------------------------------------");

				System.out.println("Using K-Nearest Neighbour...\n");
				// Perform the first fold test
				System.out.println("First Fold Test");
				double firstFoldTestKNNAccuracy = KNearestNeighbours.execute(datasetOne, datasetTwo);

				// Perform the second fold test
				System.out.println("Second Fold Test");
				double secondFoldTestKNNAccuracy = KNearestNeighbours.execute(datasetTwo, datasetOne);

				// Calculate the average accuracy
				System.out.println(
						"Average Accuracy: " + (firstFoldTestKNNAccuracy + secondFoldTestKNNAccuracy) / 2 + "\n");

				System.out.println("--------------------------------------------------");
				break;
			// execute the Multilayer Layer Perceptron algorithm
			case 2:

				System.out.println("--------------------------------------------------");

				System.out.println("Using Multilayer Layer Perceptron...\n");
				// Perform the first fold test
				System.out.println("First Fold Test\n");
				double[] firstFoldTestMLPAccuracy = MultiLayerPerceptron.execute(datasetOne, datasetTwo);
				double firstFoldTestMLPTrainingAccuracy = firstFoldTestMLPAccuracy[0];
				double firstFoldTestMLPTestingAccuracy = firstFoldTestMLPAccuracy[1];

				// Perform the second fold test
				System.out.println("Second Fold Test\n");
				double[] secondFoldTestMLPAccuracy = MultiLayerPerceptron.execute(datasetTwo, datasetOne);
				double secondFoldTestMLPTrainingAccuracy = secondFoldTestMLPAccuracy[0];
				double secondFoldTestMLPTestingAccuracy = secondFoldTestMLPAccuracy[1];

				// Calculate the average accuracy
				System.out.println("Average Training Accuracy: "
						+ (firstFoldTestMLPTrainingAccuracy + secondFoldTestMLPTrainingAccuracy) / 2);
				System.out.println("Average Testing Accuracy: "
						+ (firstFoldTestMLPTestingAccuracy + secondFoldTestMLPTestingAccuracy) / 2);

				System.out.println("--------------------------------------------------");

				break;
			// exit the application
			case 3:
				System.out.println("Exiting...");
				return false;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
		return true;
	}

}
