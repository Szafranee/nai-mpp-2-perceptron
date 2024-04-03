import java.util.List;

public class Perceptron {
    private double[] weights;
    private double learningRate;
    private double threshold;

    public Perceptron(int n, double a) {
        weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = Math.random();
        }
        learningRate = a;
        threshold = Math.random() * 10 - 5;
    }

    public int guess(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sum > threshold ? 1 : 0;
    }

    public void train(Vector vector) {
        double[] inputs = vector.vectorValues.stream().mapToDouble(Double::doubleValue).toArray();
        int guess = guess(inputs);
        // Target is 0 if guess is 1, and 1 if guess is 0
        int target = guess == 1 ? 0 : 1;
        double error = target - guess;
        for (int i = 0; i < weights.length; i++) {
            weights[i] += error * inputs[i] * learningRate;
        }
        threshold += error * learningRate;
    }
}
