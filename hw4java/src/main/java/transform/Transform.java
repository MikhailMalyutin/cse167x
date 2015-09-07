package transform;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Transform {
    public static RealMatrix translate(RealVector vec) {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        return result;
    }

    public static RealMatrix scale(double sx, double sy, double sz) {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        return result;
    }

    public static RealMatrix rotate(float degrees, RealVector axis) {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        return result;
    }
}
