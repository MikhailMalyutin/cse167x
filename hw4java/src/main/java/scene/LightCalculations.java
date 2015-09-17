package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import tracer.Camera;
import tracer.Intersection;
import tracer.Ray;
import tracer.RayTracer;
import utils.VectorUtils;

public class LightCalculations {
    public static RealVector computeLight(Model model, Camera camera, Intersection intersection) {
        RealVector finalcolor;

        final DrawedObject object = intersection.getObject();
        finalcolor = object.getAmbient();
        final RealVector emission = object.getEmission();
        finalcolor = finalcolor.add(emission);

        for (Light light : model.getLights()) {
            boolean isVisible = isVisibleFn(light, intersection, model);
            if (isVisible) {
                finalcolor = finalcolor.add(calculatePosLight(light, camera, intersection));
            }
        }

        return finalcolor;
    }

    private static boolean isVisibleFn(Light light, Intersection intersection, Model model) {
        Ray lightRay = new Ray();
        lightRay.setP0(light.getLightpos());
        final RealVector p1 = VectorUtils.toRealVector(intersection.getP()).subtract(light.getLightpos());
        p1.setEntry(3, 0.0);
        lightRay.setP1(p1);
        Intersection intersection1 = RayTracer.intersect(lightRay, model);
        return intersection1.isMatch() && intersection1.getP().distance(intersection.getP()) < 0.001;
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
        Vector3D mypos = intersection.getP(); // Dehomogenize current location
        Vector3D eyedirn = camera.getW();
        double lightDistance = 0;

        // Compute normal, needed for shading.
        // Simpler is vec3 normal = normalize(gl_NormalMatrix * mynormal) ;
        Vector3D normal = intersection.getN();

        Vector3D lightDirection;
        // Light 1, point
        final RealVector lightpos = light.getLightpos();
        final double lightposW = lightpos.getEntry(3);
        if (light.isDirectional()) {
            lightDirection = VectorUtils.toVector3D(lightpos).normalize();
        } else {
            Vector3D lightpos3d = VectorUtils.toVector3D(lightpos).scalarMultiply(1.0 / lightposW);
            lightDistance = lightpos3d.distance(intersection.getP());
            lightDirection = lightpos3d.subtract(intersection.getP()).normalize(); // no attenuation
        }
        Vector3D half1 = eyedirn.add(lightDirection).normalize();
        DrawedObject obj = intersection.getObject();
        RealVector col1 = computeLight(lightDirection, light.getLightcolor(), normal,
                half1, obj.getDiffuse(), obj.getSpecular(), obj.getShininess()) ;

        return col1.mapDivide(light.getAttenuation(lightDistance));
    }

    private Vector3D getReflection(Vector3D l, Vector3D n) {
        return n.scalarMultiply(2.0 * l.dotProduct(n)).subtract(l);
    }
}
