package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import scene.DrawedObject;
import scene.LightCalculations;
import scene.Model;
import scene.TriangleObject;
import utils.VectorUtils;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RayTracer {
    public static BufferedImage render(Model model) {
        Camera cam = getCamera(model);
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_INT_RGB);
        IntStream xs = IntStream.range(0, model.getW());
        Date startTime = new Date();
        xs.forEach(x ->
        {
            IntStream ys = IntStream.range(0, model.getH());
            ys.forEach(y -> {
                Ray ray = rayThruPixel(cam, x, y);
                Intersection hit = intersect(ray, model);
                result.setRGB(x, y, findColor(hit, cam, model));
            });
        });

        Date endTime = new Date();
        final float time = endTime.getTime() - startTime.getTime();
        System.out.println("Executing time: " + time/1000);
        return result;
    }

    private static int findColor(Intersection hit, Camera cam, Model model) {
        if (hit.isMatch()) {
            RealVector light = LightCalculations.computeLight(model, cam, hit);
            return toColour(light);
        }
        return 0;
    }

    private static int toColour(RealVector ambient) {
        final double r = ambient.getEntry(0);
        final double g = ambient.getEntry(1);
        final double b = ambient.getEntry(2);
        int rc = 256 * 256 * (int)(255. * r);
        int rg = 256 * (int)(255. * g);
        int rb = (int)(255. * b);
        return rc + rg + rb;
    }

    private static Intersection intersect(Ray ray, Model model) {
        Intersection result = new Intersection(false);
        for (DrawedObject obj : model.getObjects()) {
            if (obj instanceof TriangleObject) {
                Intersection intersection = getIntersection(ray, (TriangleObject) obj, model);
                if (intersection.isMatch()) {
                    return intersection;
                }
            }

        }

        return result;
    }

    private static Intersection getIntersection(Ray ray, TriangleObject obj, Model model) {
        Vector3D p0 = VectorUtils.toVector3D(ray.getP0());
        Vector3D p1 = VectorUtils.toVector3D(ray.getP1());
        Vector3D a = obj.getA();
        Vector3D b = obj.getB();
        Vector3D c = obj.getC();
        Vector3D n = obj.getN();
        double den = p1.dotProduct(n);
        if (den != 0.0) {
            double t = (a.dotProduct(n) - p0.dotProduct(n)) / den;
            if (t < 0) {
                return  new Intersection(false);
            }
            Vector3D p = p0.add(p1.scalarMultiply(t));
            Vector3D pc = p.subtract(c);
            double[] params = {pc.getX(), pc.getY()};
            RealVector constants = new ArrayRealVector(params, false);
            DecompositionSolver solver = obj.getSolver();
            RealVector solution = solver.solve(constants);
            double alpfa = solution.getEntry(0);
            double beta = solution.getEntry(1);
            boolean match = false;
            if (alpfa >=0 && beta >=0 && alpfa + beta <= 1.0) {
                match = true;
            } else {
                match = false;
            }
            final Intersection intersection = new Intersection(match);
            intersection.setObject(obj);
            intersection.setP(p);
            return intersection;
        }
        return new Intersection(false);
    }

    private static Ray rayThruPixel(Camera cam, int x, int y) {
        Ray result = new Ray();
        final int halfW = cam.getHalfW();
        double alpha = Math.tan(Math.toRadians(cam.getFovX() / 2)) * (x - halfW) / halfW;
        final int halfH = cam.getHalfH();
        double beta = cam.getTanHalfFovY() * (halfH - y) / halfH;
        Vector3D p13 = cam.getU().scalarMultiply(alpha).add(cam.getV().scalarMultiply(beta)).subtract(cam.getW());

        result.setP0(cam.getFrom());
        result.setP1(VectorUtils.toRealVector(p13));

        return result;
    }

    private static Camera getCamera(Model model) {
        Camera result = new Camera();
        result.setFrom(model.getFrom());
        result.setTo(model.getTo());
        result.setUp(model.getUp());
        result.setFov(model.getFov());
        result.setWidth(model.getW());
        result.setHeight(model.getH());
        result.init();
        return result;
    }
}
