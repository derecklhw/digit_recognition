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
        for (int dimensionIndex = 0; dimensionIndex < point1.length; dimensionIndex++) {
            sum += Math.pow(point1[dimensionIndex] - point2[dimensionIndex], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Method that calculates the Manhattan distance between two points
     * 
     * @param point1
     * @param point2
     * @return
     */
    public static double calculateManhattanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int dimensionIndex = 0; dimensionIndex < point1.length; dimensionIndex++) {
            sum += Math.abs(point1[dimensionIndex] - point2[dimensionIndex]);
        }
        return sum;
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
        // Check if the range is valid
        if (range < 1) {
            return new double[0];
        }
        // Create the array
        double[] returnArray = new double[range];
        // Fill the array with random values
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
        // Check if the range is valid
        if (rangeX < 1 || rangeY < 1) {
            return null;
        }
        // Create the array
        double[][] returnArray = new double[rangeX][rangeY];
        // Fill the array with random values
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
        // Check if the range is valid
        smallest--;

        if (size > (biggest - smallest)) {
            return null;
        }

        // Create the array
        Integer[] values = new Integer[size];
        // Fill the array with random values
        for (int index = 0; index < size; index++) {
            // Generate a random number
            int number = (int) (Math.random() * (biggest - smallest + 1) + smallest);
            // Check if the number is already in the array
            while (containsValue(values, number)) {
                // Generate a new random number
                number = (int) (Math.random() * (biggest - smallest + 1) + smallest);
            }
            // Add the number to the array
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
        // Check if the array is valid
        int returnIndex = 0;
        // Iterate over the array to find the highest value
        for (int iterationIndex = 1; iterationIndex < input.length; iterationIndex++) {
            // Check if the current value is higher than the previous highest value
            if (input[iterationIndex] > input[returnIndex]) {
                // Update the highest value index
                returnIndex = iterationIndex;
            }
        }
        return returnIndex;
    }

}
