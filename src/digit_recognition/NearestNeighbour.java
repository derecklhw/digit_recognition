package digit_recognition;

/**
 * Class responsible for the nearest neighbour algorithm.
 */
public class NearestNeighbour {
    // Constants for the dataset
    private static final int INPUT_FEATURES_SIZE = 64;

    /**
     * Executes the nearest neighbour algorithm.
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

        evaluateAccuracyUsingNearestNeighbour(trainingDatasetExtracted.features, trainingDatasetExtracted.labels,
                testingDatasetExtracted.features, testingDatasetExtracted.labels);
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
     * Evaluates the accuracy using the nearest neighbour algorithm.
     *
     * @param trainingDatasetFeatures the features of the training dataset
     * @param trainingDatasetLabels   the labels of the training dataset
     * @param testingDatasetFeatures  the features of the testing dataset
     * @param testingDatasetLabels    the labels of the testing dataset
     */
    public static void evaluateAccuracyUsingNearestNeighbour(
            double[][] trainingDatasetFeatures,
            int[] trainingDatasetLabels,
            double[][] testingDatasetFeatures,
            int[] testingDatasetLabels) {
        int correctPredictions = 0;
        for (int i = 0; i < testingDatasetFeatures.length; i++) {
            int predictedLabel = classifyUsingNearestNeighbour(trainingDatasetFeatures, trainingDatasetLabels,
                    testingDatasetFeatures[i]);
            if (predictedLabel == testingDatasetLabels[i]) {
                correctPredictions++;
            }
        }

        // Prints the evaluation results
        UserInterface.printFinalResults(correctPredictions, testingDatasetFeatures.length);
    }

    /**
     * Classifies the test feature using the nearest neighbour algorithm.
     *
     * @param trainingFeatures the features of the training dataset
     * @param trainingLabels   the labels of the training dataset
     * @param testFeature      the test feature
     * @return the predicted label
     */
    private static int classifyUsingNearestNeighbour(double[][] trainingFeatures, int[] trainingLabels,
            double[] testFeature) {
        int nearestLabel = -1;
        double nearestDistance = Double.MAX_VALUE;
        for (int i = 0; i < trainingFeatures.length; i++) {
            double distance = Utility.calculateEuclideanDistance(trainingFeatures[i], testFeature);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestLabel = trainingLabels[i];
            }
        }
        return nearestLabel;
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
}
