package scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import tracer.Camera;
import tracer.Intersection;
import tracer.Ray;
import tracer.RayTracer;
import utils.VectorUtils;

public class LightCalculations {

    private static final int MAX_RECURSION = 5;

    public static RealVector computeLight(Vector3D eyePos,
                                          Model model,
                                          Camera camera,
                                          Intersection intersection,
                                          int recurseCount) {
        RealVector finalcolor;

        final DrawedObject object = intersection.getObject();
        finalcolor = object.getAmbient();
        final RealVector emission = object.getEmission();
        finalcolor = finalcolor.add(emission);

        for (Light light : model.getLights()) {
            boolean isVisible = isVisibleFn(light, intersection, model);
            if (isVisible) {
                finalcolor = finalcolor.add(calculatePosLight(light,
                        camera, intersection, eyePos, model, recurseCount));
            }
            if (recurseCount < MAX_RECURSION) {
                Ray reflectedRay = new Ray();
                Vector3D intersectionPos = intersection.getP();
                Vector3D eyedirn = intersectionPos.subtract(eyePos).negate().normalize();
                reflectedRay.setP0(VectorUtils.toRealVector(intersection.getP(), 1.0));
                Vector3D normal = intersection.getN();
                Vector3D reflected = getReflection(eyedirn, normal);
                reflectedRay.setP1(VectorUtils.toRealVector(reflected));
                Intersection secondaryIntersection = RayTracer.intersect(reflectedRay, model);
                if (secondaryIntersection.isMatch()) {
                    RealVector secondaryLight
                            = computeLight(intersectionPos, model, camera, secondaryIntersection, ++recurseCount);
                    finalcolor = finalcolor.add(secondaryLight.ebeMultiply(object.getSpecular()));
//                col1 = new ArrayRealVector(3);
//                col1.setEntry(0, 1.0);
//                col1.setEntry(1, 1.0);
//                col1.setEntry(2, 1.0);
                }
            }
        }

        return finalcolor;
    }

    private static boolean isVisibleFn(Light light, Intersection intersection, Model model) {
        if (light.isPoint()) {
            Ray lightRay = new Ray();
            lightRay.setP0(light.getLightpos());
            final RealVector p1 = VectorUtils.toRealVector(intersection.getP()).subtract(light.getLightpos());
            p1.setEntry(3, 0.0);
            lightRay.setP1(p1);
            Intersection intersection1 = RayTracer.intersect(lightRay, model);
            return intersection1.isMatch() && intersection1.getP().distance(intersection.getP()) < RayTracer.EPSILON;

        }
        Ray lightRay = new Ray();

        RealVector lightDirection = light.isDirectional()
                ? light.getLightpos()
                : VectorUtils.toRealVector(intersection.getP()).subtract(light.getLightpos());
        lightRay.setP1(lightDirection.mapMultiply(-1.0));
        final RealVector intersectionP = VectorUtils.toRealVector(intersection.getP(), 1.0);
        lightRay.setP0(intersectionP);
        Intersection intersection1 = RayTracer.intersect(lightRay, model);
        return !intersection1.isMatch()
                || (intersection1.getIntersectionCount() == 1 && intersection1.getP().distance(intersection.getP()) < RayTracer.EPSILON);
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

    private static RealVector calculatePosLight(Light light,
                                                Camera camera,
                                                Intersection intersection,
                                                Vector3D eyePos,
                                                Model model,
                                                int recursiveStep) {
        Vector3D intersectionPos = intersection.getP(); // Dehomogenize current location
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
            lightDistance = lightpos3d.distance(intersectionPos);
            lightDirection = lightpos3d.subtract(intersectionPos).normalize(); // no attenuation
        }
        Vector3D eyedirn = intersectionPos.subtract(eyePos).negate().normalize();
        Vector3D half1 = eyedirn.add(lightDirection).normalize();
        DrawedObject obj = intersection.getObject();
        RealVector col1 = computeLight(lightDirection, light.getLightcolor(), normal,
                half1, obj.getDiffuse(), obj.getSpecular(), obj.getShininess()) ;

        return col1.mapDivide(light.getAttenuation(lightDistance));
    }

    private static Vector3D getEyeDir(Vector3D pos, Vector3D eyePos) {
        final Vector3D eyeDir = pos.subtract(eyePos);
        return eyeDir.normalize();
    }

    private static Vector3D getReflection(Vector3D l, Vector3D n) {
        return n.scalarMultiply(2.0 * l.dotProduct(n)).subtract(l);
    }
}
