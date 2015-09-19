package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import scene.DrawedObject;
import scene.SphereObject;
import scene.TriangleObject;
import utils.VectorUtils;

public class Intersection {
    private boolean match;
    private DrawedObject object;
    private Vector3D p;
    private Vector3D n;
    private double distance = Double.MAX_VALUE;
    private int intersectionCount = 0;

    public Intersection(boolean match) {
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public void setObject(DrawedObject object) {
        this.object = object;
    }

    public DrawedObject getObject() {
        return object;
    }

    public void setP(Vector3D p) {
        this.p = p;
    }

    public Vector3D getP() {
        return p;
    }

    public Vector3D getN() {
        if (object instanceof TriangleObject) {
            return ((TriangleObject)object).getN().scalarMultiply(-1.0);
        }
        if (object instanceof SphereObject) {
            return n;
        }
        return null;
    }

    public void setN(Vector3D n) {
        this.n = n;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public int getIntersectionCount() {
        return intersectionCount;
    }

    public void setIntersectionCount(int intersectionCount) {
        this.intersectionCount = intersectionCount;
    }
}
