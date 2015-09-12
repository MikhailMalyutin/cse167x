package tracer;

import org.apache.commons.math3.linear.RealMatrix;
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

    public void setP1(RealVector p1) {
        this.p1 = p1;
    }

    public RealVector getP1() {
        return p1;
    }

    public Ray transform(RealMatrix transform) {
        Ray result = new Ray();
        result.setP0(transform.preMultiply(p0));
        result.setP1(transform.preMultiply(p1));
        return result;
    }
}
