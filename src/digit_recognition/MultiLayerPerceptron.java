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
    private static final boolean SHOW_LABELS = false;

    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        NetworkBase network = new NetworkBase(new int[] { INPUT_LAYER_NODE_AMOUNT, FIRST_HIDDEN_LAYER_NODE_AMOUNT,
                SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10 });
        try {
            System.out.println("Creating training set...");
            TrainingSet set = createSet(firstDataset);
            trainData(network, set, TRAINING_EPOCHS_VALUE, TRAINING_LOOPS_VALUE,
                    TRAINING_BATCH_SIZE);
            System.out.println("Creating testing set...");
            TrainingSet testSet = createSet(secondDataset);
            System.out.println("Testing neural network...");
            testTrainSet(network, testSet);
        } catch (FileNotFoundException ex) {
            System.out.println("Error when reading NN test or train file!");
        }
    }

    /**
     * This method tests the network, also keeps tracks of the the guessed results
     * and provided
     * the final output.
     * 
     * @param net - RecognizingHandwrittenDigits.NetworkBase object (NN
     *            implementation)
     * @param set - testing set object (Till uses
     *            RecognizingHandwrittenDigits.TrainingSet class)
     */
    public static void testTrainSet(NetworkBase net, TrainingSet set) {
        int correct = 0;
        for (int i = 0; i < set.size(); i++) {
            double highest = Utility.returnIndexOfHighestValue(net.calculationFunction(set.getInput(i)));
            double actualHighest = Utility.returnIndexOfHighestValue(set.getOutput(i));
            if (SHOW_LABELS) {
                System.out.println("Guess: " + highest + " Real number: " + actualHighest);
            }
            if (highest == actualHighest) {
                correct++;
            }
        }
        Utility.printFinalResults(correct, set.size());
    }

    /**
     * Method that reads a training file, build a TrainingSet object from the inputs
     * 
     * @return the newly created training set
     * @throws FileNotFoundException
     */
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

    /**
     * This is a method for training the net for a specified amount of epochs.
     * 
     * @param net        - RecognizingHandwrittenDigits.NetworkBase object
     * @param set        - The training set object
     * @param epochs     - amount of epochs for the training
     * @param loops      - amount of loops for the training within one epoch
     * @param batch_size - batch size, only use with bigger data sets ( should
     *                   improve accuracy )
     */
    public static void trainData(NetworkBase net, TrainingSet set, int epochs, int loops, int batch_size) {
        System.out.println("Training neural network...");
        for (int epoch = 0; epoch < epochs; epoch++) {
            net.train(set, loops, batch_size);
            if (SHOW_LABELS) {
                System.out.println("Epochs : " + epoch + "/" + epochs);
            }
        }
    }
}
