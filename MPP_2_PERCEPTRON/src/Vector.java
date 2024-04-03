import java.util.ArrayList;

public class Vector {
    ArrayList<Double> vectorValues;
    String vectorName;

    public Vector(ArrayList<Double> vectorValues) {
        this(vectorValues, null);
    }

    public Vector(ArrayList<Double> vectorValues, String vectorName) {
        this.vectorValues = vectorValues;
        this.vectorName = vectorName;
    }

    public String toString() {
        if (vectorName == null) {
            return vectorValues.toString();
        } else {
            return vectorValues.toString() + ", " + vectorName;
        }
    }
}
