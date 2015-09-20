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
    private RealVector pv;
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
        if (object instanceof TriangleObject) {
            n =((TriangleObject)object).getN().scalarMultiply(-1.0);
        }
    }

    public DrawedObject getObject() {
        return object;
    }

    public void setP(Vector3D p) {
        this.p = p;
        this.pv = VectorUtils.toRealVector(p, 1.0);
    }

    public Vector3D getP() {
        return p;
    }

    public RealVector getPVector() {
        return pv;
    }

    public Vector3D getPPlusEpsilon() {
        if (object instanceof TriangleObject) {
            return p.add(getN().scalarMultiply(RayTracer.EPSILON));
        }
        return p.add(getN().scalarMultiply(RayTracer.EPSILON));
    }

    public Vector3D getN() {
        return n;
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
