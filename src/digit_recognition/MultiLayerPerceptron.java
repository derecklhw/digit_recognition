package digit_recognition;

import java.io.FileNotFoundException;

/**
 * Class responsible for creating and training the neural network.
 */
public class MultiLayerPerceptron {

    // Constants for the neural network
    public static final double LEARNING_RATE = 0.05;
    public final static double BIAS_RANGE_SMALLEST = -0.5;
    public final static double BIAS_RANGE_BIGGEST = 0.7;
    public final static double WEIGHTS_RANGE_SMALLEST = -1;
    public final static double WEIGHTS_RANGE_BIGGEST = 1;
    final static int TRAINING_EPOCHS_VALUE = 250;
    final static int TRAINING_LOOPS_VALUE = 500;
    final static int TRAINING_BATCH_SIZE = 32;
    final static int FIRST_HIDDEN_LAYER_NODE_AMOUNT = 26;
    final static int SECOND_HIDDEN_LAYER_NODE_AMOUNT = 15;
    final static int INPUT_LAYER_NODE_AMOUNT = 64;

    /**
     * Creates and trains the neural network.
     *
     * @param firstDataset  the first dataset
     * @param secondDataset the second dataset
     */
    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        // Create the neural network
        NetworkBase network = new NetworkBase(new int[] { INPUT_LAYER_NODE_AMOUNT, FIRST_HIDDEN_LAYER_NODE_AMOUNT,
                SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10 });
        try {
            // Create the training set
            DataSet set = createSet(firstDataset);
            // Create the testing set
            DataSet testSet = createSet(secondDataset);

            System.out.println("Training neural network in progress...");
            // Train the neural network
            network.train(set, TRAINING_EPOCHS_VALUE, TRAINING_LOOPS_VALUE,
                    TRAINING_BATCH_SIZE);

            System.out.println("Testing neural network...\n");
            // Test the neural network
            network.evaluate(testSet);

        } catch (FileNotFoundException ex) {
            System.out.println("Error when reading NN test or train file!");
        }
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
