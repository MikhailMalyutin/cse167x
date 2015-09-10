package tracer;

import org.apache.commons.math3.linear.RealVector;

public class Intersection {
    private boolean match;
    private RealVector ambient;

    public Intersection(boolean match) {
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public void setAmbient(RealVector ambient) {
        this.ambient = ambient;
    }

    public RealVector getAmbient() {
        return ambient;
    }
}
