import java.util.ArrayList;
import java.util.List;

public class Vector {
    List<Double> vectorValues;
    String vectorName;

    public Vector(List<Double> vectorValues) {
        this(vectorValues, null);
    }

    public Vector(List<Double> vectorValues, String vectorName) {
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
