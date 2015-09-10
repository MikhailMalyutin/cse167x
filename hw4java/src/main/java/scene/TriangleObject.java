package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import tracer.Intersection;
import utils.VectorUtils;

public class TriangleObject extends DrawedObject {
    private int[] vertices = new int[3];
    private DecompositionSolver solver;
    private Vector3D a;
    private Vector3D b;
    private Vector3D c;
    private Vector3D n;

    public TriangleObject(int[] vertices, Model model) {
        this.vertices = vertices;
        init(model);
    }

    public int[] getVertices() {
        return vertices;
    }

    private void init(Model model) {
        a = VectorUtils.toVector3D(model.getVertices().get(vertices[0]));
        b = VectorUtils.toVector3D(model.getVertices().get(vertices[1]));
        c = VectorUtils.toVector3D(model.getVertices().get(vertices[2]));
        n = (c.subtract(a)).crossProduct(b.subtract(a)).normalize();
        Vector3D ac = a.subtract(c);
        Vector3D bc = b.subtract(c);
        double[][] mat = {{ac.getX(), bc.getX()}, {ac.getY(), bc.getY()}};
        RealMatrix coefficients = new Array2DRowRealMatrix(mat);
        solver = new LUDecomposition(coefficients).getSolver();
    }

    public DecompositionSolver getSolver() {
        return solver;
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
