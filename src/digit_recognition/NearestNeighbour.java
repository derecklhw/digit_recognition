package digit_recognition;

public class NearestNeighbour {
    private static final int INPUT_FEATURES_SIZE = 64;

    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        if (firstDataset == null || secondDataset == null) {
            throw new IllegalArgumentException("Datasets cannot be null");
        }

        double[][] firstDatasetFeatures = extractFeatures(firstDataset);
        int[] firstDatasetLabels = extractLabels(firstDataset);

        double[][] secondDatasetFeatures = extractFeatures(secondDataset);
        int[] secondDatasetLabels = extractLabels(secondDataset);

        testWithEuclideanDistance(firstDatasetFeatures, firstDatasetLabels, secondDatasetFeatures, secondDatasetLabels);

    }

    private static double[][] extractFeatures(int[][] dataset) {
        double[][] features = new double[dataset.length][INPUT_FEATURES_SIZE];
        for (int i = 0; i < dataset.length; i++) {
            for (int j = 0; j < INPUT_FEATURES_SIZE; j++) {
                features[i][j] = dataset[i][j];
            }
        }
        return features;
    }

    private static int[] extractLabels(int[][] dataset) {
        int[] labels = new int[dataset.length];
        for (int i = 0; i < dataset.length; i++) {
            labels[i] = dataset[i][INPUT_FEATURES_SIZE];
        }
        return labels;
    }

    public static void testWithEuclideanDistance(double[][] firstDatasetFeatures, int[] firstDatasetLabels,
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

    private static int classifyUsingNearestNeighbour(double[][] trainingFeatures, int[] trainingLabels,
            double[] testFeature) {
        int nearestLabel = -1;
        double nearestDistance = Double.MAX_VALUE;
        for (int i = 0; i < trainingFeatures.length; i++) {
            double distance = calculateEuclideanDistance(trainingFeatures[i], testFeature);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestLabel = trainingLabels[i];
            }
        }
        return nearestLabel;
    }

    private static double calculateEuclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }
}
