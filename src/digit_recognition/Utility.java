package digit_recognition;

import java.util.Arrays;

/**
 * Class that contains utility methods for the application
 */
public class Utility {

    /**
     * Method that calculates the Euclidean distance between two points
     * 
     * @param point1
     * @param point2
     * @return
     */
    public static double calculateEuclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Method that creates a random array of specified size and fills it with
     * values of specified bounds
     * 
     * @param range    size of an array
     * @param smallest lower bound
     * @param biggest  higher bound
     * @return random array
     */
    public static double[] buildRandomArray(int range, double smallest, double biggest) {
        if (range < 1) {
            return new double[0];
        }
        double[] returnArray = new double[range];
        for (int index = 0; index < range; index++) {
            returnArray[index] = generateRandomValue(smallest, biggest);
        }
        return returnArray;
    }

    /**
     * Method that creates a 2 dimensional array of specified size and
     * fills it with values of specified bounds
     * 
     * @param rangeX   1st dimension size
     * @param rangeY   2nd dimension size
     * @param smallest lower bound
     * @param biggest  higher bound
     * @return 2D random array
     */
    public static double[][] buildRandomArray(int rangeX, int rangeY, double smallest, double biggest) {
        if (rangeX < 1 || rangeY < 1) {
            return null;
        }
        double[][] returnArray = new double[rangeX][rangeY];
        for (int index = 0; index < rangeX; index++) {
            returnArray[index] = buildRandomArray(rangeY, smallest, biggest);
        }
        return returnArray;
    }

    /**
     * Method that creates a random value of specified bounds
     * will be used when generating a random array
     * 
     * @param smallest lowest bound
     * @param biggest  highest bound
     * @return random number
     */
    public static double generateRandomValue(double smallest, double biggest) {
        return Math.random() * (biggest - smallest) + smallest;
    }

    /**
     * Generates an array of integers of specified bounds and size, but also limits
     * the values to once in array
     * 
     * @param smallest lower bound
     * @param biggest  higher bound
     * @param size     size of random array
     * @return array of random values
     */
    public static Integer[] randomValues(int smallest, int biggest, int size) {
        smallest--;

        if (size > (biggest - smallest)) {
            return null;
        }

        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++) {
            int number = (int) (Math.random() * (biggest - smallest + 1) + smallest);
            while (containsValue(values, number)) {
                number = (int) (Math.random() * (biggest - smallest + 1) + smallest);
            }
            values[index] = number;
        }
        return values;
    }

    /**
     * Checks if array contains a value comparable extension
     * 
     * @param <T>   object
     * @param array array of comparison
     * @param value value to compare
     * @return result
     */
    public static <T> boolean containsValue(T[] array, T value) {
        return Arrays.asList(array).contains(value);
    }

    /**
     * Return the index of the highest value in the provided array
     * 
     * @param input array to get the highest value index
     * @return index of the highest value
     */
    public static int returnIndexOfHighestValue(double[] input) {
        int returnIndex = 0;
        for (int iterationIndex = 1; iterationIndex < input.length; iterationIndex++) {
            if (input[iterationIndex] > input[returnIndex]) {
                returnIndex = iterationIndex;
            }
        }
        return returnIndex;
    }

}
