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
		if (!validateArguments(args)) {
			return;
		}
		String trainingSetPath = args[0];
		String testingSetPath = args[1];

		loadAndExecute(trainingSetPath, testingSetPath);
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
			System.out.println("Usage: java -jar digit_recognition.jar <trainingSet> <testingSet>");
			if (args.length == 0) {
				System.out.println("No datasets provided.");
				return false;
			} else {
				System.out.println(
						"Only Training dataset provided. Please provide Testing dataset. Usage: java -jar digit_recognition.jar <trainingSet> <testingSet>");
				return false;
			}
		}

		// Check if the datasets are different
		if (args[0].equals(args[1])) {
			System.out.println(
					"Training and testing datasets must be different. Usage: java -jar digit_recognition.jar <trainingSet> <testingSet>");
			return false;
		}
		return true;
	}

	/**
	 * Loads the datasets and executes the user's choice.
	 *
	 * @param trainingSetPath path to the training dataset
	 * @param testingSetPath  path to the testing dataset
	 */
	private static void loadAndExecute(String trainingSetPath, String testingSetPath) {
		try {
			int[][] trainingSet = DatasetReader.readDataset(trainingSetPath);
			int[][] testingSet = DatasetReader.readDataset(testingSetPath);
			System.out.printf("\nDatasets loaded\nTraining set: %s\nTesting set: %s\n\n", trainingSetPath,
					testingSetPath);

			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					int choice = UserInterface.getUserChoice(scanner);
					if (!executeChoice(choice, trainingSet, testingSet))
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
	 * @param choice      user's choice
	 * @param trainingSet training dataset
	 * @param testingSet  testing dataset
	 * @return true if the application should continue, false otherwise
	 */
	private static boolean executeChoice(int choice, int[][] trainingSet, int[][] testingSet) {
		switch (choice) {
			case 1:
				System.out.println("Using Nearest Neighbour...\n");
				NearestNeighbour.execute(trainingSet, testingSet);
				break;
			case 2:
				System.out.println("Using Multilayer Layer Perceptron...\n");
				MultiLayerPerceptron.execute(trainingSet, testingSet);
				break;
			case 3:
				System.out.println("Exiting...");
				return false;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
		return true;
	}

}
