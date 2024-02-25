package digit_recognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        final String TRAINING_FILE_PATH = "/home/dereck/Downloads/Digit-Recognition-MDX-Coursework/datasets/cw2DataSet1.csv";
        final String TEST_FILE_PATH = "/home/dereck/Downloads/Digit-Recognition-MDX-Coursework/datasets/cw2DataSet2.csv";

        NetworkBase network = new NetworkBase(new int[] { INPUT_LAYER_NODE_AMOUNT, FIRST_HIDDEN_LAYER_NODE_AMOUNT,
                SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10 });
        try {
            System.out.println("Creating training set...");
            TrainingSet set = createTrainingSet(TRAINING_FILE_PATH);
            trainData(network, set, TRAINING_EPOCHS_VALUE, TRAINING_LOOPS_VALUE, TRAINING_BATCH_SIZE);
            System.out.println("Testing neural network...");
            TrainingSet testSet = createTestingSet(TEST_FILE_PATH);
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
    public static TrainingSet createTrainingSet(String trainingFilePath) throws FileNotFoundException {
        TrainingSet set = new TrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
        Scanner scanner = new Scanner(new File(trainingFilePath));
        // Do while there is new line.
        while (scanner.hasNextLine()) {
            // Read in and split the line
            String line = scanner.nextLine();
            int lastCommaIndex = line.lastIndexOf(',');
            int label = Integer.parseInt(line.substring(lastCommaIndex + 1, lastCommaIndex + 2));
            String newLine = line.substring(0, lastCommaIndex);
            String[] splitLine = newLine.split(",");

            // Build an array to add to a set
            double[] splitLineNumber = new double[splitLine.length];
            for (int i = 0; i < splitLine.length; i++) {
                splitLineNumber[i] = Double.parseDouble(splitLine[i]);
            }
            double[] output = new double[10];
            output[label] = 1d;

            // print the output array
            System.out.println("\nOutput array: ");
            for (int i = 0; i < output.length; i++) {
                System.out.println(output[i]);
            }

            // print the splitLineNumber array
            System.out.println("\nSplitLineNumber array: ");
            for (int i = 0; i < splitLineNumber.length; i++) {
                System.out.println(splitLineNumber[i]);
            }

            set.addData(splitLineNumber, output);
        }
        scanner.close();
        return set;
    }

    /**
     * Method that builds a testing set and returns a
     * RecognizingHandwrittenDigits.TrainingSet object that
     * that is used for testing
     * 
     * @return RecognizingHandwrittenDigits.TrainingSet object
     * @throws FileNotFoundException
     */
    public static TrainingSet createTestingSet(String testFilePath) throws FileNotFoundException {
        TrainingSet set = new TrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
        Scanner scanner = new Scanner(new File(testFilePath));
        // Do while there is new line.
        while (scanner.hasNextLine()) {
            // Read in and split the line
            String line = scanner.nextLine();
            int lastCommaIndex = line.lastIndexOf(',');
            int label = Integer.parseInt(line.substring(lastCommaIndex + 1, lastCommaIndex + 2));
            String newLine = line.substring(0, lastCommaIndex);
            String[] splitLine = newLine.split(",");
            // Build an array to add to a set
            double[] splitLineNumber = new double[splitLine.length];
            for (int i = 0; i < splitLine.length; i++) {
                splitLineNumber[i] = Double.parseDouble(splitLine[i]);
            }
            double[] output = new double[10];
            output[label] = 1d;
            set.addData(splitLineNumber, output);
        }
        scanner.close();
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