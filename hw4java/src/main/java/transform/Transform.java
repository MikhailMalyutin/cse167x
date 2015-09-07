package transform;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Transform {
    public static RealMatrix translate(RealVector vec) {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        result.setEntry(0,0,1.0);
        result.setEntry(1,1,1.0);
        result.setEntry(2,2,1.0);
        result.setEntry(3,3,1.0);
        result.setEntry(3,0,vec.getEntry(0));
        result.setEntry(3, 1, vec.getEntry(1));
        result.setEntry(3, 2, vec.getEntry(2));
        return result;
    }


    public static RealMatrix scale(double sx, double sy, double sz) {
        RealMatrix result = new Array2DRowRealMatrix(4,4);
        result.setEntry(0,0,sx);
        result.setEntry(1,1,sy);
        result.setEntry(2,2,sz);
        result.setEntry(3,3,1.0);
        return result;
    }

    public static RealMatrix rotate(float degrees, RealVector axis) {
        float x = (float) axis.getEntry(0);
        float y = (float) axis.getEntry(1);
        float z = (float) axis.getEntry(2);
        float d = (float) Math.sqrt(x * x + y * y + z * z);
        float theta = (float) Math.toRadians(degrees);
        double[][] dualV = {{0., z, -y},
                {-z, 0., x},
                {y, -x, 0.0}};
        RealMatrix dual = new Array2DRowRealMatrix(dualV);
        double[][] amV = {
                {x, y, z},
                {0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0}};
        RealMatrix am = new Array2DRowRealMatrix(amV);
        RealMatrix I = MatrixUtils.getIdentity(3);
        RealMatrix res3 = I.scalarMultiply(Math.cos(theta))
                .add(
                        am.multiply(am.transpose()).scalarMultiply(1.0 - Math.cos(theta)))
                .add(dual.scalarMultiply(Math.sin(theta)));
        double[][] res3V = res3.getData();
        double[][] resV = {
                {res3V[0][0], res3V[0][1], res3V[0][2], 0.0},
                {res3V[1][0], res3V[1][1], res3V[1][2], 0.0},
                {res3V[2][0], res3V[2][1], res3V[2][2], 0.0},
                {0.0, 0.0, 0.0, 1.0}};

        return new Array2DRowRealMatrix(resV);
    }
}
