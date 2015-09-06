package scene;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public abstract class DrawedObject {
    //    shape type ;
    private float size;
    private RealVector ambient;
    private RealVector diffuse;
    private RealVector specular;
    private RealVector emission;
    private float shininess;
    private RealMatrix transform;

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public RealVector getAmbient() {
        return ambient;
    }

    public void setAmbient(RealVector ambient) {
        this.ambient = ambient;
    }

    public RealVector getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(RealVector diffuse) {
        this.diffuse = diffuse;
    }

    public RealVector getSpecular() {
        return specular;
    }

    public void setSpecular(RealVector specular) {
        this.specular = specular;
    }

    public RealVector getEmission() {
        return emission;
    }

    public void setEmission(RealVector emission) {
        this.emission = emission;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public RealMatrix getTransform() {
        return transform;
    }

    public void setTransform(RealMatrix transform) {
        this.transform = transform;
    }
}
