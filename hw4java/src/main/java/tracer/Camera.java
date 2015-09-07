package tracer;

import org.apache.commons.math3.linear.RealVector;

public class Camera {
    private RealVector from;
    private RealVector to;
    private RealVector up;

    public void setFrom(RealVector from) {
        this.from = from;
    }

    public RealVector getFrom() {
        return from;
    }

    public void setTo(RealVector to) {
        this.to = to;
    }

    public RealVector getTo() {
        return to;
    }

    public void setUp(RealVector up) {
        this.up = up;
    }

    public RealVector getUp() {
        return up;
    }
}
