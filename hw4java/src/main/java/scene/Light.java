package scene;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;


public class Light {
    private RealVector lightpos; // Light Positions
    private RealVector lightcolor; // Light Colors
    private boolean point;
    private RealVector attenuation = getDefault();

    private RealVector getDefault() {
        RealVector result = new ArrayRealVector(3);
        result.setEntry(0, 1.0);
        return result;
    }

    public RealVector getLightpos() {
        return lightpos;
    }

    public void setLightpos(RealVector lightpos) {
        this.lightpos = lightpos;
        this.point = !(lightpos.getEntry(3) == 0);
    }

    public RealVector getLightcolor() {
        return lightcolor;
    }

    public void setLightcolor(RealVector lightcolor) {
        this.lightcolor = lightcolor;
    }

    public double getAttenuation(double distance) {
        if (isDirectional()) {
            return 1.f;
        }
        return attenuation.getEntry(0)
                + attenuation.getEntry(1) * distance
                + attenuation.getEntry(2) * distance * distance;
    }

    public boolean isPoint() {
        return point;
    }

    public  boolean isDirectional() {
        return !point;
    }

    public void setAttenuation(RealVector attenuation) {
        if (attenuation == null) {
            this.attenuation = getDefault();
            return;
        }
        this.attenuation = attenuation;
    }
}
