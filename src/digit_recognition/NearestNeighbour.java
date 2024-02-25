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
     * @param firstDataset  the first dataset
     * @param secondDataset the second dataset
     */
    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        if (firstDataset == null || secondDataset == null) {
            throw new IllegalArgumentException("Datasets cannot be null");
        }

        Dataset firstDatasetExtracted = extractFeaturesAndLabels(firstDataset);
        Dataset secondDatasetExtracted = extractFeaturesAndLabels(secondDataset);

        evaluateAccuracyUsingNearestNeighbour(firstDatasetExtracted.features, firstDatasetExtracted.labels,
                secondDatasetExtracted.features, secondDatasetExtracted.labels);
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
     * @param firstDatasetFeatures  the features of the first dataset
     * @param firstDatasetLabels    the labels of the first dataset
     * @param secondDatasetFeatures the features of the second dataset
     * @param secondDatasetLabels   the labels of the second dataset
     */
    public static void evaluateAccuracyUsingNearestNeighbour(double[][] firstDatasetFeatures, int[] firstDatasetLabels,
            double[][] secondDatasetFeatures, int[] secondDatasetLabels) {
        int correctPredictions = 0;
        for (int i = 0; i < secondDatasetFeatures.length; i++) {
            int predictedLabel = classifyUsingNearestNeighbour(firstDatasetFeatures, firstDatasetLabels,
                    secondDatasetFeatures[i]);
            if (predictedLabel == secondDatasetLabels[i]) {
                correctPredictions++;
            }
        }

        UserInterface.printFinalResults(correctPredictions, secondDatasetFeatures.length);
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
