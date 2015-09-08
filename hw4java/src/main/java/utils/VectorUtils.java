package utils;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class VectorUtils {
    public static RealVector normalize(RealVector vec) {
        return vec.mapDivide(vec.getNorm());
    }

    public static Vector3D toVector3D(RealVector vec) {
        return new Vector3D(vec.getEntry(0), vec.getEntry(1), vec.getEntry(2));
    }

    public static RealVector toRealVector(Vector3D vec3d) {
        RealVector result = new ArrayRealVector(4);
        result.setEntry(0, vec3d.getX());
        result.setEntry(1, vec3d.getY());
        result.setEntry(2, vec3d.getZ());
        result.setEntry(3, 0.0);
        return result;
    }
}
