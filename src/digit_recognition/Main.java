package digit_recognition;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java -jar digit_recognition.jar <dataset1> <dataset2>");
			if (args.length == 0) {
				System.out.println("No datasets provided.");
			} else {
				System.out.println("Only one dataset provided. Please provide two datasets for training and testing");
			}
			return;
		}

		String firstDatasetPath = args[0];
		String secondDatasetPath = args[1];

		if (firstDatasetPath.equals(secondDatasetPath)) {
			System.out.println(
					"Both datasets are the same. Please provide different datasets. Usage: java -jar digit_recognition.jar <dataset1> <dataset2>");
			return;
		}

		try {
			int[][] firstDataset = DatasetReader.readDataset(firstDatasetPath);
			int[][] secondDataset = DatasetReader.readDataset(secondDatasetPath);
			System.out.println("Both datasets loaded successfully.\n");

			try (Scanner scanner = new Scanner(System.in)) {
				int choice = UserInterface.getUserChoice(scanner);
				executeChoice(choice, firstDataset, secondDataset);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: "
					+ (e.getMessage().equals(firstDatasetPath) ? firstDatasetPath : secondDatasetPath)
					+ ". Please provide a valid file path.");
		}

	}

	private static void executeChoice(int choice, int[][] firstDataset, int[][] secondDataset) {
		switch (choice) {
			case 1:
				System.out.println("\nUsing Nearest Neighbour...");
				NearestNeighbour.execute(firstDataset, secondDataset);
				break;
			case 2:
				System.out.println("\nUsing Multilayer Layer Perceptron...");
				MultiLayerPerceptron.execute(firstDataset, secondDataset);
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
