package digit_recognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class responsible for reading the dataset from a file.
 */
public class DatasetReader {
    // Constants for the dataset
    private static final int DATA_SIZE = 2810;
    private static final int SINGLE_INPUT_SIZE = 65;

    /**
     * Reads the dataset from a file.
     *
     * @param filePath path to the file with the dataset
     * @return 2D array with the dataset
     * @throws FileNotFoundException if the file is not found
     */
    public static int[][] readDataset(String filePath) throws FileNotFoundException {
        // Create a 2D array to store the dataset
        int[][] result = new int[DATA_SIZE][SINGLE_INPUT_SIZE];
        // Read the dataset from the file
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int resultIndex = 0;
            // Read the dataset line by line
            while (scanner.hasNextLine()) {
                // Split the line by commas and parse the values to integers
                String[] splitLine = scanner.nextLine().split(",");
                // Create an array to store the parsed values
                int[] dataLine = new int[SINGLE_INPUT_SIZE];
                // Parse the values to integers
                for (int index = 0; index < splitLine.length; index++) {
                    dataLine[index] = Integer.parseInt(splitLine[index]);
                }
                // Store the parsed values in the result array
                result[resultIndex++] = dataLine;
            }
        }
        return result;
    }
}
