package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import tracer.Camera;
import tracer.Intersection;
import utils.VectorUtils;

public class LightCalculations {
    public static RealVector computeLight(Model model, Camera camera, Intersection intersection) {
        RealVector finalcolor;

        finalcolor = intersection.getObject().getAmbient();

        for (Light light : model.getLights()) {
            finalcolor = finalcolor.add(calculatePosLight(light, camera, intersection));
        }

        return finalcolor;
    }

    private static RealVector computeLight(Vector3D direction,
                                          RealVector lightcolor,
                                          Vector3D normal,
                                          Vector3D halfvec,
                                          RealVector mydiffuse,
                                          RealVector myspecular,
                                          float myshininess) {

        float nDotL = (float) normal.dotProduct(direction);
        RealVector lambert = mydiffuse.ebeMultiply(lightcolor.mapMultiply(Math.max(nDotL, 0.0)));

        float nDotH = (float) normal.dotProduct(halfvec);
        RealVector phong = myspecular.ebeMultiply(lightcolor).mapMultiply(Math.pow(Math.max(nDotH, 0.0), myshininess));

        RealVector retval = lambert.add(phong);
        return retval;
    }

    private static RealVector calculatePosLight(Light light, Camera camera, Intersection intersection) {
        Vector3D mypos = camera.getFrom3(); // Dehomogenize current location
        Vector3D eyedirn = camera.getW();

        // Compute normal, needed for shading.
        // Simpler is vec3 normal = normalize(gl_NormalMatrix * mynormal) ;
        Vector3D normal = intersection.getN();

        Vector3D direction1;
        // Light 1, point
        final RealVector lightpos = light.getLightpos();
        final double lightposW = lightpos.getEntry(3);
        if (lightposW == 0) {
            direction1 = VectorUtils.toVector3D(lightpos).normalize();
        } else {
            Vector3D position = VectorUtils.toVector3D(lightpos).scalarMultiply(1.0/lightposW);
            direction1 = position.subtract(mypos).normalize(); // no attenuation
        }
        Vector3D half1 = direction1.add(eyedirn).normalize();
        DrawedObject obj = intersection.getObject();
        RealVector col1 = computeLight(direction1, light.getLightcolor(), normal,
                half1, obj.getDiffuse(), obj.getSpecular(), obj.getShininess()) ;

        return col1 ;
    }
}
