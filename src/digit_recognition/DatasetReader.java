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
        int[][] result = new int[DATA_SIZE][SINGLE_INPUT_SIZE];
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int resultIndex = 0;
            while (scanner.hasNextLine()) {
                String[] splitLine = scanner.nextLine().split(",");
                int[] dataLine = new int[SINGLE_INPUT_SIZE];
                for (int index = 0; index < splitLine.length; index++) {
                    dataLine[index] = Integer.parseInt(splitLine[index]);
                }
                result[resultIndex++] = dataLine;
            }
        }
        return result;
    }
}
