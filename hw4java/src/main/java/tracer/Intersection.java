package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import scene.TriangleObject;

public class Intersection {
    private boolean match;
    private TriangleObject object;
    private Vector3D p;
    private double distance = Double.MAX_VALUE;

    public Intersection(boolean match) {
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public void setObject(TriangleObject object) {
        this.object = object;
    }

    public TriangleObject getObject() {
        return object;
    }

    public void setP(Vector3D p) {
        this.p = p;
    }

    public Vector3D getP() {
        return p;
    }

    public Vector3D getN() {
        return object.getN().scalarMultiply(-1.0);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
