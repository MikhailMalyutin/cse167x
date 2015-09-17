package scene;

import org.apache.commons.math3.linear.RealVector;


public class Light {
    private RealVector lightpos; // Light Positions
    private RealVector lightcolor; // Light Colors
    private boolean point;

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
        return 1.;//distance * distance;
    }

    public boolean isPoint() {
        return point;
    }

    public  boolean isDirectional() {
        return !point;
    }
}
