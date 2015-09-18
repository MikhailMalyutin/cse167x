package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import scene.*;
import utils.VectorUtils;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.stream.IntStream;

public class RayTracer {

    private static final double EPSILON = 0.001;

    public static BufferedImage render(Model model) {
        Camera cam = getCamera(model);
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_INT_RGB);
        IntStream xs = IntStream.range(0, model.getW()).parallel();
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
        System.out.println("Executing time: " + time / 1000);
        return result;
    }

    private static int findColor(Intersection hit, Camera cam, Model model) {
        if (hit.isMatch()) {
            Vector3D eyeDir = cam.getW();
            RealVector light = LightCalculations.computeLight(eyeDir, model, cam, hit, 0);
            return toColour(light);
        }
        return 0;
    }

    private static int toColour(RealVector ambient) {
        final double r = ambient.getEntry(0);
        final double g = ambient.getEntry(1);
        final double b = ambient.getEntry(2);
        int rc = 256 * 256 * (int) (255. * r);
        int rg = 256 * (int) (255. * g);
        int rb = (int) (255. * b);
        return rc + rg + rb;
    }

    public static Intersection intersect(Ray ray, Model model) {
        Intersection result = new Intersection(false);
        for (DrawedObject obj : model.getObjects()) {
            if (obj instanceof TriangleObject) {
                Intersection intersection = getIntersection(ray, (TriangleObject) obj, model);
                if (intersection.isMatch() && intersection.getDistance() < result.getDistance()) {
                    result = intersection;
                }
            } else if (obj instanceof SphereObject) {
                Intersection intersection = getIntersection(ray, (SphereObject) obj, model);
                if (intersection.isMatch() && intersection.getDistance() < result.getDistance()) {
                    result = intersection;
                }
            }

        }

        return result;
    }

    private static Intersection getIntersection(Ray ray, SphereObject obj, Model model) {
        RealMatrix transform = obj.getTransform();
        final RealMatrix transformInverse = obj.getTransformInverse();
        ray = ray.transform(transformInverse);
        Vector3D c = VectorUtils.toVector3D(obj.getCenter());
        Vector3D p0 = VectorUtils.toVector3D(ray.getP0());
        Vector3D p1 = VectorUtils.toVector3D(ray.getP1());
        float a = (float) p1.dotProduct(p1);
        Vector3D p0c = p0.subtract(c);
        float b = (float) p1.dotProduct(p0c) * 2.0f;

        float cc = (float) (p0c.dotProduct(p0c)) - obj.getSize() * obj.getSize();
        Double t = quadraticEquationRoot1(a, b, cc);
        if (t == null || t < EPSILON) {
            return new Intersection(false);
        }
        Intersection result = new Intersection(true);
        result.setObject(obj);
        final Vector3D p = p0.add(p1.scalarMultiply(t));
        RealVector pv = VectorUtils.toRealVector(p);
        pv.setEntry(3, 1.0);
        RealVector pt = transform.preMultiply(pv);
        result.setP(VectorUtils.toVector3D(pt));
        RealVector nv = pv.subtract(obj.getCenter());
        final RealVector nvt = transformInverse.transpose().preMultiply(nv);
        result.setN(VectorUtils.toVector3D(nvt).normalize());
        result.setDistance(t);
        return result;
    }

    public static Double quadraticEquationRoot1(double a, double b, double c){
        double root1, root2; //This is now a double, too.
        double d = Math.pow(b, 2) - 4 * a * c;
        if (d < 0) {
            return null;
        }
        root1 = (-b + Math.sqrt(d)) / (2*a);
        root2 = (-b - Math.sqrt(d)) / (2*a);
        if (root2 > 0) {
            return root2;
        }
        if (root1 > 0) {
            return root1;
        }
        return null;
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
            if (t < EPSILON) {
                return new Intersection(false);
            }
            Vector3D p = p0.add(p1.scalarMultiply(t));
            Vector3D pc = p.subtract(c);
            final Intersection intersection = getIntersection(obj, p, pc);
            if (intersection != null) {
                intersection.setDistance(t);
                return intersection;
            }
        }
        return new Intersection(false);
    }

    private static Intersection getIntersection(TriangleObject obj, Vector3D p, Vector3D pc) {
        DecompositionSolver solver = obj.getSolverXY();
        try {
            double[] params = {pc.getX(), pc.getY()};
            return getIntersection(obj, solver, p, pc, params);
        } catch (Exception ex) {
            solver = obj.getSolverYZ();
            try {
                double[] params = {pc.getY(), pc.getZ()};
                return getIntersection(obj, solver, p, pc, params);
            } catch (Exception e) {
                solver = obj.getSolverXZ();
                double[] params = {pc.getX(), pc.getZ()};
                return getIntersection(obj, solver, p, pc, params);
            }
        }
    }

    private static Intersection getIntersection(TriangleObject obj,
                                                DecompositionSolver solver,
                                                Vector3D p,
                                                Vector3D pc,
                                                double[] params) {
        RealVector constants = new ArrayRealVector(params, false);
        RealVector solution = solver.solve(constants);
        double alpfa = solution.getEntry(0);
        double beta = solution.getEntry(1);
        boolean match = false;
        if (alpfa >= 0 && beta >= 0 && alpfa + beta <= 1.0) {
            match = true;
        } else {
            match = false;
        }
        final Intersection intersection = new Intersection(match);
        intersection.setObject(obj);
        intersection.setP(p);
        return intersection;
    }

    private static Ray rayThruPixel(Camera cam, int xi, int yi) {
        Ray result = new Ray();
        float x = xi + .5f;
        float y = yi + .5f;
        final int halfW = cam.getHalfW();
        float alpha = (float) (cam.getTanHalfFovX()) * ((x - halfW) / halfW);
        final int halfH = cam.getHalfH();
        float beta = (float) (cam.getTanHalfFovY() * (halfH - y) / halfH);
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
