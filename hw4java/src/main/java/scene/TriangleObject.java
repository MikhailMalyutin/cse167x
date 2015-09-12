package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import utils.VectorUtils;

public class TriangleObject extends DrawedObject {
    private int[] vertices = new int[3];
    private DecompositionSolver solverXY;
    private DecompositionSolver solverXZ;
    private DecompositionSolver solverYZ;
    private Vector3D a;
    private Vector3D b;
    private Vector3D c;
    private Vector3D n;

    public TriangleObject(int[] vertices, Model model, RealMatrix transform) {
        this.vertices = vertices;
        setTransform(transform);
        init(model);
    }

    public int[] getVertices() {
        return vertices;
    }

    private void init(Model model) {
        final RealMatrix transform = getTransform();
        final RealVector av = transform.preMultiply(model.getVertices().get(vertices[0]));
        a = VectorUtils.toVector3D(av);
        final RealVector bv = transform.preMultiply(model.getVertices().get(vertices[1]));
        b = VectorUtils.toVector3D(bv);
        final RealVector cv = transform.preMultiply(model.getVertices().get(vertices[2]));
        c = VectorUtils.toVector3D(cv);
        n = (c.subtract(a)).crossProduct(b.subtract(a)).normalize();
        Vector3D ac = a.subtract(c);
        Vector3D bc = b.subtract(c);
        double[][] matXY = {{ac.getX(), bc.getX()}, {ac.getY(), bc.getY()}};
        double[][] matXZ = {{ac.getX(), bc.getX()}, {ac.getZ(), bc.getZ()}};
        double[][] matYZ = {{ac.getY(), bc.getY()}, {ac.getZ(), bc.getZ()}};
        RealMatrix coefficientsXY = new Array2DRowRealMatrix(matXY);
        RealMatrix coefficientsXZ = new Array2DRowRealMatrix(matXZ);
        RealMatrix coefficientsYZ = new Array2DRowRealMatrix(matYZ);
        solverXY = new LUDecomposition(coefficientsXY).getSolver();
        solverXZ = new LUDecomposition(coefficientsXZ).getSolver();
        solverYZ = new LUDecomposition(coefficientsYZ).getSolver();
    }

    public DecompositionSolver getSolverXY() {
        return solverXY;
    }

    public DecompositionSolver getSolverXZ() {
        return solverXZ;
    }

    public DecompositionSolver getSolverYZ() {
        return solverYZ;
    }

    public Vector3D getA() {
        return a;
    }

    public Vector3D getB() {
        return b;
    }

    public Vector3D getC() {
        return c;
    }

    public Vector3D getN() {
        return n;
    }
}
