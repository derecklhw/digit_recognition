package digit_recognition;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (!validateArguments(args)) {
			return;
		}
		String trainingSetPath = args[0];
		String testingSetPath = args[1];

		loadAndExecute(trainingSetPath, testingSetPath);
	}

	private static boolean validateArguments(String[] args) {
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

		if (args[0].equals(args[1])) {
			System.out.println("Training and testing datasets must be different.");
			return false;
		}
		return true;
	}

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
			System.out.println(e.getMessage() + "Please provide a valid path to the dataset.");
		}
	}

	private static boolean executeChoice(int choice, int[][] trainingSet, int[][] testingSet) {
		switch (choice) {
			case 1:
				System.out.println("\nUsing Nearest Neighbour...\n");
				NearestNeighbour.execute(trainingSet, testingSet);
				break;
			case 2:
				System.out.println("\nUsing Multilayer Layer Perceptron...\n");
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
