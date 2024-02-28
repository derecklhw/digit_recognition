package digit_recognition;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class responsible for the k-nearest neighbours algorithm.
 */
public class KNearestNeighbours {
    // Constants
    private static final int INPUT_FEATURES_SIZE = 64;
    private static final int K = 3;

    /**
     * Executes the k-nearest neighbours algorithm.
     *
     * @param trainingDataset the training dataset
     * @param testingDataset  the testing dataset
     */
    public static void execute(int[][] trainingDataset, int[][] testingDataset) {
        // Check if the datasets are null
        if (trainingDataset == null || testingDataset == null) {
            throw new IllegalArgumentException("Datasets cannot be null");
        }

        // Extract features and labels from the datasets
        Dataset trainingDatasetExtracted = extractFeaturesAndLabels(trainingDataset);
        Dataset testingDatasetExtracted = extractFeaturesAndLabels(testingDataset);

        // Evaluate the accuracy using the k-nearest neighbours algorithm
        evaluateAccuracyUsingKNearestNeighbours(trainingDatasetExtracted.features, trainingDatasetExtracted.labels,
                testingDatasetExtracted.features, testingDatasetExtracted.labels, K);
    }

    /**
     * Extracts features and labels from the dataset.
     *
     * @param dataset the dataset
     * @return the extracted features and labels
     */
    private static Dataset extractFeaturesAndLabels(int[][] dataset) {
        // Create arrays to store the features and labels
        double[][] features = new double[dataset.length][INPUT_FEATURES_SIZE];
        int[] labels = new int[dataset.length];

        // Extract the features and labels
        for (int dataPointIndex = 0; dataPointIndex < dataset.length; dataPointIndex++) {
            // The first 64 values in the dataset are the features
            for (int featureIndex = 0; featureIndex < INPUT_FEATURES_SIZE; featureIndex++) {
                features[dataPointIndex][featureIndex] = dataset[dataPointIndex][featureIndex];
            }
            // The last value in the dataset is the label
            labels[dataPointIndex] = dataset[dataPointIndex][INPUT_FEATURES_SIZE];
        }

        return new Dataset(features, labels);
    }

    /**
     * Evaluates the accuracy using the k-nearest neighbours algorithm.
     *
     * @param trainingFeatures the features of the training dataset
     * @param trainingLabels   the labels of the training dataset
     * @param testingFeatures  the features of the testing dataset
     * @param testingLabels    the labels of the testing dataset
     * @param k                the number of neighbours to consider
     */
    public static void evaluateAccuracyUsingKNearestNeighbours(
            double[][] trainingFeatures,
            int[] trainingLabels,
            double[][] testingFeatures,
            int[] testingLabels,
            int k) {
        int correctPredictions = 0;
        // Classify each test feature and count the correct predictions
        for (int testIndex = 0; testIndex < testingFeatures.length; testIndex++) {
            // Classify the test feature using the k-nearest neighbours algorithm
            int predictedLabel = classifyUsingKNearestNeighbours(trainingFeatures, trainingLabels,
                    testingFeatures[testIndex], k);
            // Check if the predicted label is correct
            if (predictedLabel == testingLabels[testIndex]) {
                // Increment the number of correct predictions
                correctPredictions++;
            }
        }

        // Prints the evaluation results
        UserInterface.printFinalResults(correctPredictions, testingFeatures.length);
    }

    /**
     * Classifies a test feature using the k-nearest neighbours algorithm.
     *
     * @param trainingFeatures the features of the training dataset
     * @param trainingLabels   the labels of the training dataset
     * @param testFeature      the feature to classify
     * @param k                the number of neighbours to consider
     * @return the predicted label
     */
    private static int classifyUsingKNearestNeighbours(double[][] trainingFeatures, int[] trainingLabels,
            double[] testFeature, int k) {
        // Create a priority queue to store the neighbours
        PriorityQueue<Neighbour> neighbours = new PriorityQueue<>(k, (a, b) -> Double.compare(b.distance, a.distance));

        // Find the k nearest neighbours
        for (int trainingIndex = 0; trainingIndex < trainingFeatures.length; trainingIndex++) {
            // Calculate the distance between the training feature and the test feature
            double distance = Utility.calculateEuclideanDistance(trainingFeatures[trainingIndex], testFeature);
            // Add the neighbour to the priority queue
            if (neighbours.size() < k) {
                // Add the neighbour if the queue is not full
                neighbours.add(new Neighbour(trainingLabels[trainingIndex], distance));
            } else if (neighbours.peek().distance > distance) {
                // Replace the neighbour with the largest distance if the queue is full and the
                // new distance is smaller
                neighbours.poll();
                neighbours.add(new Neighbour(trainingLabels[trainingIndex], distance));
            }
        }

        return findMostCommonLabel(neighbours);
    }

    /**
     * Finds the most common label in the neighbours.
     *
     * @param neighbours the neighbours
     * @return the most common label
     */
    private static int findMostCommonLabel(PriorityQueue<Neighbour> neighbours) {
        // Create a map to store the counts of each label
        Map<Integer, Integer> counts = new HashMap<>();
        // Count the occurrences of each label
        while (!neighbours.isEmpty()) {
            // Get the next neighbour
            Neighbour neighbour = neighbours.poll();
            // Increment the count of the label
            counts.put(neighbour.label, counts.getOrDefault(neighbour.label, 0) + 1);
        }

        // Find the most common label
        return counts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Class representing the dataset.
     */
    private static class Dataset {
        public double[][] features;
        public int[] labels;

        // Constructor
        public Dataset(double[][] features, int[] labels) {
            this.features = features;
            this.labels = labels;
        }
    }

    /**
     * Class representing a neighbour.
     */
    private static class Neighbour {
        int label;
        double distance;

        // Constructor
        Neighbour(int label, double distance) {
            this.label = label;
            this.distance = distance;
        }
    }
}
