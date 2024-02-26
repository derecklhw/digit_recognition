package digit_recognition;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds sets for training and testing, includes methods for control
 * of a set.
 */
public class TrainingSet {
    private final int inputSize;
    private final int outputSize;
    private List<DataPoint> data;

    public TrainingSet(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.data = new ArrayList<>();
    }

    /**
     * Adds data points to the training set.
     * 
     * @param input    The input features of the data point.
     * @param expected The expected output (label) of the data point.
     */
    public void addData(double[] input, double[] expected) {
        if (input.length != inputSize || expected.length != outputSize) {
            throw new IllegalArgumentException("Input or output size does not match the expected size.");
        }
        data.add(new DataPoint(input, expected));
    }

    /**
     * Extracts a batch of data points for training.
     * 
     * @param size The size of the batch to extract.
     * @return A new TrainingSet instance containing the specified batch size.
     */
    public TrainingSet extractBatch(int size) {
        TrainingSet batch = new TrainingSet(inputSize, outputSize);
        if (size > 0 && size <= this.size()) {
            Integer[] ids = Utility.randomValues(0, this.size() - 1, size);
            for (Integer id : ids) {
                DataPoint point = data.get(id);
                batch.addData(point.getInput(), point.getOutput());
            }
        }
        return batch;
    }

    /**
     * Gets the size of the training set.
     * 
     * @return The size of the training set.
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
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return data.get(index).getInput();
    }

    /**
     * Retrieves the expected output of a data point by index.
     * 
     * @param index The index of the data point.
     * @return The expected output of the specified data point.
     */
    public double[] getOutput(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
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
