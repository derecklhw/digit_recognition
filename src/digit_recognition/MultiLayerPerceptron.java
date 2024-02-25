package digit_recognition;

import java.io.FileNotFoundException;

public class MultiLayerPerceptron {
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

    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        NetworkBase network = new NetworkBase(new int[] { INPUT_LAYER_NODE_AMOUNT, FIRST_HIDDEN_LAYER_NODE_AMOUNT,
                SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10 });
        try {
            TrainingSet set = createSet(firstDataset);
            TrainingSet testSet = createSet(secondDataset);

            System.out.println("Training neural network...");
            network.train(set, TRAINING_EPOCHS_VALUE, TRAINING_LOOPS_VALUE,
                    TRAINING_BATCH_SIZE);

            System.out.println("Testing neural network...\n");
            network.evaluate(testSet);

        } catch (FileNotFoundException ex) {
            System.out.println("Error when reading NN test or train file!");
        }
    }

    public static TrainingSet createSet(int[][] dataset) throws FileNotFoundException {
        TrainingSet set = new TrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);

        for (int[] row : dataset) {
            // The label is the last element in the row
            int label = row[row.length - 1];

            // Initialize an array to hold the input features
            double[] inputFeatures = new double[row.length - 1];
            for (int i = 0; i < row.length - 1; i++) {
                inputFeatures[i] = row[i];
            }

            // Initialize an array to hold the output (label) for the input features
            double[] output = new double[10];
            output[label] = 1d;

            set.addData(inputFeatures, output);
        }
        return set;

    }

}
