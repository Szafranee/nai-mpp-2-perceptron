import java.util.*;

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

    public Perceptron trainOnTrainSet(List<Vector> trainSetVectors, Map<String, Integer> irisMap) {
        for (Vector vector : trainSetVectors) {
            int target = irisMap.get(vector.vectorName);
            int guess = this.guess(vector);
            if (guess != target) {
                this.train(vector, guess);
            }
        }
        return this;
    }

    public Map<Integer, Map.Entry<Integer, Integer>> testOnTestSetAndGetAmountOfCorrectGuesses(List<Vector> testSetVectors, Map<String, Integer> irisMap) {
        int classZeroTotal = testSetVectors.stream().filter(vector -> irisMap.get(vector.vectorName) == 0).toArray().length;
        int classOneTotal = testSetVectors.stream().filter(vector -> irisMap.get(vector.vectorName) == 1).toArray().length;

        Map.Entry<Integer, Integer> classZero = new AbstractMap.SimpleEntry<>(classZeroTotal, 0);
        Map.Entry<Integer, Integer> classOne = new AbstractMap.SimpleEntry<>(classOneTotal, 0);
        Map.Entry<Integer, Integer> classTotal = new AbstractMap.SimpleEntry<>(testSetVectors.size(), 0);

        Map<Integer, Map.Entry<Integer, Integer>> classSummary = new HashMap<>();

        classSummary.put(0, classZero);
        classSummary.put(1, classOne);
        classSummary.put(2, classTotal);

        for (Vector vector : testSetVectors) {
            int guess = this.guess(vector);
            System.out.println("Guess: " + Main.getClassFromMap(irisMap, guess) + ", Target: " + vector.vectorName);
            if (irisMap.get(vector.vectorName) == guess) {
                System.out.println("Guessed correctly! EZ 😎");
                classTotal.setValue(classTotal.getValue() + 1);
                if (guess == 0) {
                    classZero.setValue(classZero.getValue() + 1);
                } else {
                    classOne.setValue(classOne.getValue() + 1);
                }
            } else {
                System.out.println("Guessed incorrectly! Sadge 😔");
            }
            System.out.println();
        }
        return classSummary;
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