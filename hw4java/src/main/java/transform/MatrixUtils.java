package transform;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixUtils {
    public static RealMatrix getIdentity() {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        result.addToEntry(0,0,1.0);
        result.addToEntry(1,1,1.0);
        result.addToEntry(2,2,1.0);
        result.addToEntry(3,3,1.0);
        return result;
    }
}
