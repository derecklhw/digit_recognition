package digit_recognition;

import java.io.FileNotFoundException;

/**
 * Class responsible for creating and training the neural network.
 */
public class MultiLayerPerceptron {

    // Constants for the neural network
    public static final double LEARNING_RATE = 0.1;
    public final static double BIAS_RANGE_SMALLEST = -0.5;
    public final static double BIAS_RANGE_BIGGEST = 0.7;
    public final static double WEIGHTS_RANGE_SMALLEST = -1;
    public final static double WEIGHTS_RANGE_BIGGEST = 1;
    final static int TRAINING_EPOCHS_VALUE = 200;
    final static int TRAINING_LOOPS_VALUE = 250;
    final static int TRAINING_BATCH_SIZE = 33;
    final static int FIRST_HIDDEN_LAYER_NODE_AMOUNT = 31;
    final static int SECOND_HIDDEN_LAYER_NODE_AMOUNT = 10;
    final static int INPUT_LAYER_NODE_AMOUNT = 64;

    /**
     * Executes the multi-layer perceptron algorithm.
     *
     * @param firstDataset  the first dataset
     * @param secondDataset the second dataset
     * @return the accuracy of the multi-layer perceptron algorithm
     */
    public static double[] execute(int[][] firstDataset, int[][] secondDataset) {
        // Create the neural network
        NetworkBase network = new NetworkBase(new int[] { INPUT_LAYER_NODE_AMOUNT, FIRST_HIDDEN_LAYER_NODE_AMOUNT,
                SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10 });
        try {
            // Create the training set
            DataSet set = createSet(firstDataset);
            // Create the testing set
            DataSet testSet = createSet(secondDataset);

            System.out.println("Training neural network...");
            // Train the neural network
            double trainingAccuracy = network.train(set, TRAINING_EPOCHS_VALUE, TRAINING_LOOPS_VALUE,
                    TRAINING_BATCH_SIZE);

            System.out.println("Testing neural network...");
            // Test the neural network
            double testingAccuracy = network.evaluate(testSet);

            return new double[] { trainingAccuracy, testingAccuracy };

        } catch (FileNotFoundException ex) {
            System.out.println("Error when reading NN test or train file!");
        }
        return null;
    }

    /**
     * Creates a dataset from the provided 2D array.
     *
     * @param dataset the 2D array with the dataset
     * @return the dataset
     * @throws FileNotFoundException if the file is not found
     */
    public static DataSet createSet(int[][] dataset) throws FileNotFoundException {
        // Create a new dataset
        DataSet set = new DataSet(INPUT_LAYER_NODE_AMOUNT, 10);

        // Add the data to the dataset
        for (int[] row : dataset) {
            // The label is the last element in the row
            int label = row[row.length - 1];

            // Initialize an array to hold the input features
            double[] inputFeatures = new double[row.length - 1];

            // Store the input features in the array
            for (int featureIndex = 0; featureIndex < row.length - 1; featureIndex++) {
                inputFeatures[featureIndex] = row[featureIndex];
            }

            // Initialize an array to hold the output (label) for the input features
            double[] output = new double[10];

            // Set the output (label) for the input features
            output[label] = 1d;

            // Add the input features and the output to the dataset
            set.addData(inputFeatures, output);
        }
        return set;

    }

}
