package scene;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public abstract class DrawedObject {
    //    shape type ;
    float size;
    RealVector ambient;
    RealVector diffuse;
    RealVector specular;
    RealVector emission;
    float shininess;
    RealMatrix transform;
}
