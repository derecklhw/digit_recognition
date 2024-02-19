package digit_recognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DatasetReader {
    private static final int dataSize = 2810;
    private static final int singleInputSize = 65;

    public static int[][] readDataset(String filePath) throws FileNotFoundException {
        int[][] result = new int[dataSize][singleInputSize];
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int resultIndex = 0;
            while (scanner.hasNextLine()) {
                String[] splitLine = scanner.nextLine().split(",");
                int[] dataLine = new int[singleInputSize];
                for (int index = 0; index < splitLine.length; index++) {
                    dataLine[index] = Integer.parseInt(splitLine[index]);
                }
                result[resultIndex++] = dataLine;
            }
        }
        return result;
    }
}
