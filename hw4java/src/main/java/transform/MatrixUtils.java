package transform;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixUtils {
    public static RealMatrix getIdentity() {
        return getIdentity(4);
    }

    public static RealMatrix getIdentity(int n) {
        RealMatrix result = new Array2DRowRealMatrix(n,n);
        for (int i=0; i<n; ++i) {
            result.addToEntry(i, i, 1.0);
        }
        return result;
    }
}
