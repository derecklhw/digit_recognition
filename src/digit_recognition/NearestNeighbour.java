package digit_recognition;

public class NearestNeighbour {
    private static final int INPUT_FEATURES_SIZE = 64;

    public static void execute(int[][] firstDataset, int[][] secondDataset) {
        if (firstDataset == null || secondDataset == null) {
            throw new IllegalArgumentException("Datasets cannot be null");
        }

        Dataset firstDatasetExtracted = extractFeaturesAndLabels(firstDataset);
        Dataset secondDatasetExtracted = extractFeaturesAndLabels(secondDataset);

        evaluateAccuracyUsingNearestNeighbour(firstDatasetExtracted.features, firstDatasetExtracted.labels,
                secondDatasetExtracted.features, secondDatasetExtracted.labels);

    }

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

    private static class Dataset {
        public double[][] features;
        public int[] labels;

        public Dataset(double[][] features, int[] labels) {
            this.features = features;
            this.labels = labels;
        }
    }
}
