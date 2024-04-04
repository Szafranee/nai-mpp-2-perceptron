import java.util.Arrays;

public class Perceptron {
    private double[] weights;
    private final double learningRate;
    private double threshold;

    public Perceptron(int n, double a) {
        weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = Math.random();
        }
        learningRate = a;
        threshold = Math.random() * 10 - 5;
    }

    public int guess(Vector vector) {
        double sum = calculateSum(vector);
        return sum > threshold ? 1 : 0;
    }

    public void train(Vector vector, int guess) {
        // If the guess is 1, the target is 0, otherwise the target is 1
        int target = guess == 1 ? 0 : 1;
        // either 1 or -1
        double error = target - guess;
        adjustWeightsAndThreshold(vector, error);
    }

    private double calculateSum(Vector vector) {
        double[] inputVectorValues = vector.vectorValues.stream().mapToDouble(Double::doubleValue).toArray();
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputVectorValues[i] * weights[i];
        }
        return sum;
    }

    private void adjustWeightsAndThreshold(Vector vector, double error) {
        double[] inputs = vector.vectorValues.stream().mapToDouble(Double::doubleValue).toArray();
        for (int i = 0; i < weights.length; i++) {
            weights[i] += error * inputs[i] * learningRate;
        }
        threshold += error * learningRate * -1;
    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "weights=" + Arrays.toString(weights) +
                ", learningRate=" + learningRate +
                ", threshold=" + threshold +
                '}';
    }
}