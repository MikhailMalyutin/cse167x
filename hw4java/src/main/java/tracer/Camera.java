package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import utils.VectorUtils;

public class Camera {
    private RealVector from;
    private RealVector to;
    private RealVector up;
    private float fov;
    private int width;
    private int height;

    private Vector3D from3;
    private Vector3D up3;
    private Vector3D to3;
    private Vector3D w;
    private Vector3D u;
    private Vector3D v;

    private int halfW;
    private int halfH;
    private float fovY;
    private float fovX;

    private double tanHalfFovY;
    private double tanHalfFovX;

    public void init() {
        from3 = VectorUtils.toVector3D(getFrom());
        up3 = VectorUtils.toVector3D(getUp());
        to3 = VectorUtils.toVector3D(getTo());
        w = from3.subtract(to3).normalize();
        u = up3.crossProduct(w).normalize();
        v = w.crossProduct(u);

        halfW = getWidth() / 2;
        halfH = getHeight() / 2;
        fovY = getFov();// / halfW * halfH;
        fovX = fovY * getWidth() / getHeight();
        tanHalfFovY = Math.tan(Math.toRadians(getFovY() / 2));
        tanHalfFovX = tanHalfFovY * getWidth() / getHeight();
    }

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

    public Vector3D getFrom3() {
        return from3;
    }

    public void setFrom3(Vector3D from3) {
        this.from3 = from3;
    }

    public Vector3D getUp3() {
        return up3;
    }

    public void setUp3(Vector3D up3) {
        this.up3 = up3;
    }

    public Vector3D getTo3() {
        return to3;
    }

    public void setTo3(Vector3D to3) {
        this.to3 = to3;
    }

    public Vector3D getW() {
        return w;
    }

    public void setW(Vector3D w) {
        this.w = w;
    }

    public Vector3D getU() {
        return u;
    }

    public void setU(Vector3D u) {
        this.u = u;
    }

    public Vector3D getV() {
        return v;
    }

    public void setV(Vector3D v) {
        this.v = v;
    }

    public int getHalfW() {
        return halfW;
    }

    public void setHalfW(int halfW) {
        this.halfW = halfW;
    }

    public int getHalfH() {
        return halfH;
    }

    public void setHalfH(int halfH) {
        this.halfH = halfH;
    }

    public float getFovY() {
        return fovY;
    }

    public void setFovY(float fovY) {
        this.fovY = fovY;
    }

    public float getFovX() {
        return fovX;
    }

    public void setFovX(float fovX) {
        this.fovX = fovX;
    }

    public double getTanHalfFovY() {
        return tanHalfFovY;
    }

    public void setTanHalfFovY(double tanHalfFovY) {
        this.tanHalfFovY = tanHalfFovY;
    }

    public double getTanHalfFovX() {
        return tanHalfFovX;
    }

    public void setTanHalfFovX(double tanHalfFovX) {
        this.tanHalfFovX = tanHalfFovX;
    }
}
