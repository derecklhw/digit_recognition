package digit_recognition;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(
					"No datasets provided. Usage: java -jar digit_recognition.jar <training_dataset> <testing_dataset>");
			return;
		}

		if (args.length == 1) {
			System.out.println(
					"No testing dataset provided. Usage: java -jar digit_recognition.jar <training_dataset> <testing_dataset>");
			return;
		}

		String trainingDatasetPath = args[0];
		try {
			int[][] trainingDataset = DatasetReader.readDataset(trainingDatasetPath);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + trainingDatasetPath);
		}

		String testingDatasetPath = args[1];
		try {
			int[][] testingDataset = DatasetReader.readDataset(testingDatasetPath);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + testingDatasetPath);
		}

	}

}
