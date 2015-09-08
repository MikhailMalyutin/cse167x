package tracer;

import org.apache.commons.math3.linear.RealVector;

public class Camera {
    private RealVector from;
    private RealVector to;
    private RealVector up;
    private float fov;
    private int width;
    private int height;

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

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getFov() {
        return fov;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
