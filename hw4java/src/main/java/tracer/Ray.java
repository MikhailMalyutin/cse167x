package tracer;

import org.apache.commons.math3.linear.RealVector;

public class Ray {
    RealVector p0;
    RealVector p1;

    public RealVector getP0() {
        return p0;
    }

    public void setP0(RealVector p0) {
        this.p0 = p0;
    }
}
