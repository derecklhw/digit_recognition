package digit_recognition;

public class NetworkBase {

    public final int[] NETWORK_LAYER_SIZE;
    public final int INPUT_LAYER_SIZE;
    public final int OUTPUT_LAYER_SIZE;
    public final int NETWORK_SIZE;

    private double[][] output;
    private double[][][] weights;
    private double[][] bias;

    private double[][] error_signal;
    private double[][] output_derivative;

    public NetworkBase(int[] NETWORK_LAYER_SIZE) {
        this.NETWORK_LAYER_SIZE = NETWORK_LAYER_SIZE;
        this.INPUT_LAYER_SIZE = NETWORK_LAYER_SIZE[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZE.length;
        this.OUTPUT_LAYER_SIZE = NETWORK_LAYER_SIZE[NETWORK_SIZE - 1];
        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];
        this.error_signal = new double[NETWORK_SIZE][];
        this.output_derivative = new double[NETWORK_SIZE][];

        for (int index = 0; index < NETWORK_SIZE; index++) {
            this.output[index] = new double[NETWORK_LAYER_SIZE[index]];
            this.error_signal[index] = new double[NETWORK_LAYER_SIZE[index]];
            this.output_derivative[index] = new double[NETWORK_LAYER_SIZE[index]];

            if (index > 0) {
                this.weights[index] = Utility.buildRandomArray(NETWORK_LAYER_SIZE[index], NETWORK_LAYER_SIZE[index - 1],
                        MultiLayerPerceptron.WEIGHTS_RANGE_SMALLEST,
                        MultiLayerPerceptron.WEIGHTS_RANGE_BIGGEST);
                this.bias[index] = Utility.buildRandomArray(NETWORK_LAYER_SIZE[index],
                        MultiLayerPerceptron.BIAS_RANGE_SMALLEST, MultiLayerPerceptron.BIAS_RANGE_BIGGEST);
            }
        }
    }

    public void train(TrainingSet set, int epochs, int loops, int batch_size) {
        if (set.INPUT_SIZE != INPUT_LAYER_SIZE || set.OUTPUT_SIZE != OUTPUT_LAYER_SIZE) {
            return;
        }
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int index = 0; index < loops; index++) {
                TrainingSet batch = set.extractBatch(batch_size);
                for (int b = 0; b < batch_size; b++) {
                    forward(batch.getInput(b));
                    backward(batch.getOutput(b));
                    updateWeights(MultiLayerPerceptron.LEARNING_RATE);
                }
            }
        }
    }

    public void evaluate(TrainingSet trainingSet) {
        int correct = 0;
        for (int i = 0; i < trainingSet.size(); i++) {
            double[] output = forward(trainingSet.getInput(i));
            double highest = Utility.returnIndexOfHighestValue(output);
            double actualHighest = Utility.returnIndexOfHighestValue(trainingSet.getOutput(i));
            if (highest == actualHighest) {
                correct++;
            }
        }
        UserInterface.printFinalResults(correct, trainingSet.size());
    }

    public double[] forward(double[] input) {
        if (input.length != this.INPUT_LAYER_SIZE) {
            return null;
        }
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                double sum = 0.0;
                for (int previousNeuron = 0; previousNeuron < NETWORK_LAYER_SIZE[layer - 1]; previousNeuron++) {
                    sum += output[layer - 1][previousNeuron] *
                            weights[layer][neuron][previousNeuron];
                }
                sum += bias[layer][neuron];

                output[layer][neuron] = sigmoidFunction(sum);
                output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);

            }
        }
        return output[NETWORK_SIZE - 1];
    }

    private void backward(double[] target) {
        for (int layer = NETWORK_SIZE - 1; layer > 0; layer--) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                if (layer == NETWORK_SIZE - 1) { // Output layer
                    double derivative = output_derivative[layer][neuron];
                    double difference = output[layer][neuron] - target[neuron];
                    error_signal[layer][neuron] = difference * derivative;
                } else { // Hidden layers
                    double derivative = output_derivative[layer][neuron];
                    double sum = 0.0;
                    for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZE[layer + 1]; nextNeuron++) {
                        sum += weights[layer + 1][nextNeuron][neuron] * error_signal[layer + 1][nextNeuron];
                    }
                    error_signal[layer][neuron] = sum * derivative;
                }
            }
        }
    }

    public void updateWeights(double learningRate) {
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
                double delta = -learningRate * error_signal[layer][neuron];
                bias[layer][neuron] += delta;
                for (int previousNeuron = 0; previousNeuron < NETWORK_LAYER_SIZE[layer - 1]; previousNeuron++) {
                    weights[layer][neuron][previousNeuron] += delta * output[layer - 1][previousNeuron];
                }
            }
        }
    }

    private double sigmoidFunction(double inputValue) {
        return 1D / (1 + Math.exp(-inputValue));
    }
}
