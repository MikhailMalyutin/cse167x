package scene;

import org.apache.commons.math3.linear.RealVector;

public class SphereObject extends DrawedObject {
    private float size;
    private RealVector center;

    public SphereObject(float size, RealVector center) {
        this.size = size;
        this.center = center;
    }

    public float getSize() {
        return size;
    }

    public RealVector getCenter() {
        return center;
    }

}
