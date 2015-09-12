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
    private double distance = Double.MAX_VALUE;

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
            SphereObject casted = (SphereObject) object;
            return p.subtract(VectorUtils.toVector3D(casted.getCenter())).scalarMultiply(-1.0);
        }
        return null;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
