package digit_recognition;

/**
 * Base class for the network.
 */
public class NetworkBase {

    // Constants for the network
    public final int[] NETWORK_LAYER_SIZE;
    public final int INPUT_LAYER_SIZE;
    public final int OUTPUT_LAYER_SIZE;
    public final int NETWORK_SIZE;

    private double[][] output;
    private double[][][] weights;
    private double[][] bias;

    private double[][] error_signal;
    private double[][] output_derivative;

    /**
     * Constructor for the network.
     *
     * @param NETWORK_LAYER_SIZE the network layer size
     */
    public NetworkBase(int[] NETWORK_LAYER_SIZE) {

        // Sets the network's layer sizes
        this.NETWORK_LAYER_SIZE = NETWORK_LAYER_SIZE;
        this.INPUT_LAYER_SIZE = NETWORK_LAYER_SIZE[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZE.length;
        this.OUTPUT_LAYER_SIZE = NETWORK_LAYER_SIZE[NETWORK_SIZE - 1];

        // Initializes the network's parameters based on the layer sizes
        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];
        this.error_signal = new double[NETWORK_SIZE][];
        this.output_derivative = new double[NETWORK_SIZE][];

        // Loop through each layer in the network to initialize layer-specific
        // parameters.
        for (int index = 0; index < NETWORK_SIZE; index++) {
            // Initialize the layer's output, error signal, and output derivative arrays.
            this.output[index] = new double[NETWORK_LAYER_SIZE[index]];
            this.error_signal[index] = new double[NETWORK_LAYER_SIZE[index]];
            this.output_derivative[index] = new double[NETWORK_LAYER_SIZE[index]];

            // Initialize the weights and biases for each neuron in the layer.
            if (index > 0) {
                this.weights[index] = Utility.buildRandomArray(NETWORK_LAYER_SIZE[index], NETWORK_LAYER_SIZE[index - 1],
                        MultiLayerPerceptron.WEIGHTS_RANGE_SMALLEST,
                        MultiLayerPerceptron.WEIGHTS_RANGE_BIGGEST);
                this.bias[index] = Utility.buildRandomArray(NETWORK_LAYER_SIZE[index],
                        MultiLayerPerceptron.BIAS_RANGE_SMALLEST, MultiLayerPerceptron.BIAS_RANGE_BIGGEST);
            }
        }
    }

    /**
     * Trains the network network using the training set.
     *
     * @param set       the training set
     * @param epochs    the epochs
     * @param loops     the loops
     * @param batchSize the batch size
     */
    public void train(DataSet set, int epochs, int loops, int batchSize) {
        // Iterates over each epoch
        for (int epoch = 0; epoch < epochs; epoch++) {
            System.out.println("\rEpochs : " + epoch + "/" + epochs);
            for (int loop = 0; loop < loops; loop++) {
                // Extracts a mini-batch from the training set
                DataSet batch = set.extractBatch(batchSize);
                // Iterates over each data point in the batch
                for (int b = 0; b < batchSize; b++) {
                    // Forward pass to compute the network's prediction
                    forward(batch.getInput(b));
                    // Backward pass to compute gradients based on the error
                    backward(batch.getOutput(b));
                    // Update weights and biases based on gradients
                    updateWeights(MultiLayerPerceptron.LEARNING_RATE);
                }
            }
        }
        System.out.println("Training neural network complete.\n");
    }

    /**
     * Evaluates the neural network's performance on the given dataset.
     *
     * @param trainingSet the training set
     */
    public void evaluate(DataSet trainingSet) {
        int correct = 0;
        // Iterates over each data point in the dataset
        for (int sampleIndex = 0; sampleIndex < trainingSet.size(); sampleIndex++) {
            // Forward pass to compute the network's prediction
            double[] output = forward(trainingSet.getInput(sampleIndex));
            // Determines the index of the highest output value
            double predictedIndex = Utility.returnIndexOfHighestValue(output);
            double actualIndex = Utility.returnIndexOfHighestValue(trainingSet.getOutput(sampleIndex));
            // Increment correct count if prediction matches the actual value
            if (predictedIndex == actualIndex) {
                correct++;
            }
        }
        // Prints the evaluation results
        UserInterface.printFinalResults(correct, trainingSet.size());
    }

    /**
     * Performs a forward pass through the network.
     *
     * @param input the input
     * @return the double[]
     */
    public double[] forward(double[] input) {
        // Sets the input layer's outputs to the input features
        this.output[0] = input;
        // Propagates the data forward through each layer
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                double sum = 0.0;
                // Computes the weighted sum of inputs for the current neuron
                for (int previousNeuron = 0; previousNeuron < NETWORK_LAYER_SIZE[layer - 1]; previousNeuron++) {
                    sum += output[layer - 1][previousNeuron] *
                            weights[layer][neuron][previousNeuron];
                }
                // Adds the bias and applies the activation function
                sum += bias[layer][neuron];
                output[layer][neuron] = sigmoidFunction(sum);
                output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);

            }
        }
        // Returns the output of the last layer as the network's prediction
        return output[NETWORK_SIZE - 1];
    }

    /**
     * Performs a backward pass through the network, computing gradients
     *
     * @param target the target
     */
    private void backward(double[] target) {
        // Iterates over each layer and neuron to compute the error signal
        for (int layer = NETWORK_SIZE - 1; layer > 0; layer--) {
            // Iterates over each neuron in the layer
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                // Computes the error signal for the current neuron
                if (layer == NETWORK_SIZE - 1) {
                    // Applies the derivative of the activation function
                    double derivative = output_derivative[layer][neuron];
                    // Computes the difference between the output and the target
                    double difference = output[layer][neuron] - target[neuron];
                    error_signal[layer][neuron] = difference * derivative;
                } else {
                    // Applies the derivative of the activation function
                    double derivative = output_derivative[layer][neuron];
                    // Initializes the sum of errors for the current neuron
                    double sum = 0.0;
                    // Computes the weighted sum of errors from the next layer
                    for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZE[layer + 1]; nextNeuron++) {
                        sum += weights[layer + 1][nextNeuron][neuron] * error_signal[layer + 1][nextNeuron];
                    }
                    // Applies the derivative of the activation function
                    error_signal[layer][neuron] = sum * derivative;
                }
            }
        }
    }

    /**
     * Updates the weights and biases of the network based on the computed
     * gradients.
     *
     * @param learningRate the learning rate
     */
    public void updateWeights(double learningRate) {
        // Iterates over each layer and neuron to adjust weights and biases
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                // Computes the delta for the current neuron
                double delta = -learningRate * error_signal[layer][neuron];
                // Adjusts the bias for the current neuron
                bias[layer][neuron] += delta;
                // Iterates over each neuron in the previous layer to adjust the weights
                for (int previousNeuron = 0; previousNeuron < NETWORK_LAYER_SIZE[layer - 1]; previousNeuron++) {
                    // Adjusts the weights based on the error signal and the output of the previous
                    // layer
                    weights[layer][neuron][previousNeuron] += delta * output[layer - 1][previousNeuron];
                }
            }
        }
    }

    /**
     * Sigmoid function.
     *
     * @param inputValue the input value
     * @return the double
     */
    private double sigmoidFunction(double inputValue) {
        return 1D / (1 + Math.exp(-inputValue));
    }
}
