package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import utils.VectorUtils;

public class SphereObject extends DrawedObject {
    private float size;
    private RealVector center;

    public SphereObject(float size, RealVector center, RealMatrix transform) {
        this.size = (float) (size*transform.getEntry(2, 2));
        Vector3D c3d = VectorUtils.toVector3D(center);
        RealVector homegenous = VectorUtils.toRealVector(c3d);
        homegenous.setEntry(3, 1.0);
        //final RealVector transformed = transform.preMultiply(homegenous);
        this.center = center;
    }

    public float getSize() {
        return size;
    }

    public RealVector getCenter() {
        return center;
    }

}
