package digit_recognition;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class responsible for the k-nearest neighbours algorithm.
 */
public class KNearestNeighbours {
    private static final int INPUT_FEATURES_SIZE = 64;
    private static final int K = 3;

    /**
     * Executes the k-nearest neighbours algorithm.
     *
     * @param trainingDataset the training dataset
     * @param testingDataset  the testing dataset
     */
    public static void execute(int[][] trainingDataset, int[][] testingDataset) {
        if (trainingDataset == null || testingDataset == null) {
            throw new IllegalArgumentException("Datasets cannot be null");
        }

        Dataset trainingDatasetExtracted = extractFeaturesAndLabels(trainingDataset);
        Dataset testingDatasetExtracted = extractFeaturesAndLabels(testingDataset);

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
        double[][] features = new double[dataset.length][INPUT_FEATURES_SIZE];
        int[] labels = new int[dataset.length];

        for (int i = 0; i < dataset.length; i++) {
            for (int j = 0; j < INPUT_FEATURES_SIZE; j++) {
                features[i][j] = dataset[i][j];
            }
            labels[i] = dataset[i][INPUT_FEATURES_SIZE];
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
            double[][] trainingFeatures, int[] trainingLabels,
            double[][] testingFeatures, int[] testingLabels, int k) {
        int correctPredictions = 0;
        for (int i = 0; i < testingFeatures.length; i++) {
            int predictedLabel = classifyUsingKNearestNeighbours(trainingFeatures, trainingLabels,
                    testingFeatures[i], k);
            if (predictedLabel == testingLabels[i]) {
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
        PriorityQueue<Neighbour> neighbours = new PriorityQueue<>(k, (a, b) -> Double.compare(b.distance, a.distance));
        for (int i = 0; i < trainingFeatures.length; i++) {
            double distance = Utility.calculateEuclideanDistance(trainingFeatures[i], testFeature);
            if (neighbours.size() < k) {
                neighbours.add(new Neighbour(trainingLabels[i], distance));
            } else if (neighbours.peek().distance > distance) {
                neighbours.poll();
                neighbours.add(new Neighbour(trainingLabels[i], distance));
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
        Map<Integer, Integer> counts = new HashMap<>();
        while (!neighbours.isEmpty()) {
            Neighbour neighbour = neighbours.poll();
            counts.put(neighbour.label, counts.getOrDefault(neighbour.label, 0) + 1);
        }

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
