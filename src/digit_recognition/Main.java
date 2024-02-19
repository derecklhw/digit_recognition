package digit_recognition;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java -jar digit_recognition.jar <training_dataset> <testing_dataset>");
			if (args.length == 0) {
				System.out.println("No datasets provided.");
			} else {
				System.out.println("Only one dataset provided. Please provide both training and testing datasets.");
			}
			return;
		}

		String trainingDatasetPath = args[0];
		String testingDatasetPath = args[1];

		if (trainingDatasetPath.equals(testingDatasetPath)) {
			System.out.println(
					"Training and testing datasets are the same. Please provide different datasets. Usage: java -jar digit_recognition.jar <training_dataset> <testing_dataset>");
			return;
		}

		try {
			int[][] trainingDataset = DatasetReader.readDataset(trainingDatasetPath);
			int[][] testingDataset = DatasetReader.readDataset(testingDatasetPath);
			System.out.println("Training and testing datasets loaded successfully.\n");

			try (Scanner scanner = new Scanner(System.in)) {
				int choice = UserInterface.getUserChoice(scanner);
				executeChoice(choice, trainingDataset, testingDataset);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: "
					+ (e.getMessage().equals(trainingDatasetPath) ? trainingDatasetPath : testingDatasetPath)
					+ ". Please provide a valid file path.");
		}

	}

	private static void executeChoice(int choice, int[][] trainingDataset, int[][] testingDataset) {
		switch (choice) {
			case 1:
				System.out.println("\nUsing Nearest Neighbour...");
				break;
			case 2:
				System.out.println("\nUsing Multilayer Layer Perceptron...");
				break;
			case 3:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				return;
		}
	}

}
