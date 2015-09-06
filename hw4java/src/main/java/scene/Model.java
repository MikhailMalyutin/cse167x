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
//    int w, h ;
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
    private static List<RealVector> vertices = new ArrayList<>();
    final static int maxobjects = 10 ;
    private static int numobjects ;
    private static List<DrawedObject> objects = new ArrayList<>();
}
