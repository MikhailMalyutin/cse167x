package scene;

import org.apache.commons.math3.linear.RealVector;


public class Lignt {
    private RealVector lightpos; // Light Positions
    private RealVector lightcolor; // Light Colors

    public RealVector getLightpos() {
        return lightpos;
    }

    public void setLightpos(RealVector lightpos) {
        this.lightpos = lightpos;
    }

    public RealVector getLightcolor() {
        return lightcolor;
    }

    public void setLightcolor(RealVector lightcolor) {
        this.lightcolor = lightcolor;
    }
}
