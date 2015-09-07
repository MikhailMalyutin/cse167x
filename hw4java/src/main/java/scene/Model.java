package scene;

import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

public class Model {
//    int amount; // The amount of rotation for each arrow press
//    vec3 eye; // The (regularly updated) vector coordinates of the eye
//    vec3 up;  // The (regularly updated) vector coordinates of the up
//
//    vec3 eyeinit ;
//    vec3 upinit ;
//    vec3 center ;
//    int amountinit;
    private int w;
    private int h;
//    float fovy ;
//
//    static enum {view, translate, scale} transop ; // which operation to transform
//    enum shape {cube, sphere, teapot} ;
//    float sx, sy ; // the scale in x and y
//    float tx, ty ; // the translation in x and y
//
//// Lighting parameter array, similar to that in the fragment shader
//    final int numLights = 10 ;
//    GLfloat lightposn [4*numLights] ; // Light Positions
//    GLfloat lightcolor[4*numLights] ; // Light Colors
//    GLfloat lightransf[4*numLights] ; // Lights transformed by modelview
//    int numused ;                     // How many lights are used
//
//    // Materials (read from file)
//// With multiple objects, these are colors for each.
//    GLfloat ambient[4] ;
//    GLfloat diffuse[4] ;
//    GLfloat specular[4] ;
//    GLfloat emission[4] ;
//    GLfloat shininess ;
//
//// For multiple objects, read from a file.
    private int maxdepth = 5;
    private String output;
    private List<RealVector> vertices = new ArrayList<>();
    private List<DrawedObject> objects = new ArrayList<>();
    private RealVector from;
    private RealVector to;
    private RealVector up;
    private float fov;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public List<RealVector> getVertices() {
        return vertices;
    }

    public void setVertices(List<RealVector> vertices) {
        this.vertices = vertices;
    }

    public List<DrawedObject> getObjects() {
        return objects;
    }

    public void setObjects(List<DrawedObject> objects) {
        this.objects = objects;
    }

    public int getMaxdepth() {
        return maxdepth;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
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
}
