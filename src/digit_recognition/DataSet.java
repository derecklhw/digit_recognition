package digit_recognition;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the dataset.
 */
public class DataSet {
    // The size of the input and output
    private final int inputSize;
    private final int outputSize;
    private List<DataPoint> data;

    /**
     * Constructor for the DataSet class.
     * 
     * @param inputSize  The size of the input feature array.
     * @param outputSize The size of the output array.
     */
    public DataSet(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.data = new ArrayList<>();
    }

    /**
     * Adds a new data point to the dataset.
     * 
     * @param input    The input features of the data point.
     * @param expected The expected output of the data point.
     */
    public void addData(double[] input, double[] expected) {
        // Check if the input and output size match the expected size
        if (input.length != inputSize || expected.length != outputSize) {
            throw new IllegalArgumentException("Input or output size does not match the expected size.");
        }
        // Add the data point to the list
        data.add(new DataPoint(input, expected));
    }

    /**
     * Extracts a batch of data points from the training set.
     * 
     * @param size The size of the batch.
     * @return A batch of data points.
     */
    public DataSet extractBatch(int size) {
        // Create a new dataset
        DataSet batch = new DataSet(inputSize, outputSize);
        // Add random data points to the batch
        if (size > 0 && size <= this.size()) {
            // Get random indices
            Integer[] ids = Utility.randomValues(0, this.size() - 1, size);
            // Add the data points to the batch
            for (Integer id : ids) {
                DataPoint point = data.get(id);
                batch.addData(point.getInput(), point.getOutput());
            }
        }
        return batch;
    }

    /**
     * Gets the size of the dataset.
     * 
     * @return The size of the dataset.
     */
    public int size() {
        return data.size();
    }

    /**
     * Retrieves the input features of a data point by index.
     * 
     * @param index The index of the data point.
     * @return The input features of the specified data point.
     */
    public double[] getInput(int index) {
        // Check if the index is valid
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        // Return the input features
        return data.get(index).getInput();
    }

    /**
     * Retrieves the expected output of a data point by index.
     * 
     * @param index The index of the data point.
     * @return The expected output of the specified data point.
     */
    public double[] getOutput(int index) {
        // Check if the index is valid
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        // Return the output
        return data.get(index).getOutput();
    }

    /**
     * Gets the size of the input feature array.
     * 
     * @return The input size.
     */
    public int getInputSize() {
        return inputSize;
    }

    /**
     * Gets the size of the output array.
     * 
     * @return The output size.
     */
    public int getOutputSize() {
        return outputSize;
    }

    /**
     * Inner class to represent a single data point in the training set.
     */
    private static class DataPoint {
        private final double[] input;
        private final double[] output;

        // Constructor for the DataPoint class
        public DataPoint(double[] input, double[] output) {
            this.input = input;
            this.output = output;
        }

        // Getters for the input and output
        public double[] getInput() {
            return input;
        }

        public double[] getOutput() {
            return output;
        }
    }
}
