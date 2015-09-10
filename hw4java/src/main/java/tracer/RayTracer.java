package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import scene.DrawedObject;
import scene.Model;
import scene.TriangleObject;
import utils.VectorUtils;

import java.awt.image.BufferedImage;
import java.util.Date;

public class RayTracer {
    public static BufferedImage render(Model model) {
        Camera cam = getCamera(model);
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_INT_RGB);
        Date startTime = new Date();
        for (int x = 0; x < model.getW(); ++x) {
            for (int y = 0; y < model.getH(); ++y) {
                Ray ray = rayThruPixel(cam, x, y);
                Intersection hit = intersect(ray, model);
                result.setRGB(x, y, findColor(hit));
            }
        }
        Date endTime = new Date();
        final float time = endTime.getTime() - startTime.getTime();
        System.out.println("Executing time: " + time/1000);
        return result;
    }

    private static int findColor(Intersection hit) {
        if (hit.isMatch()) {
            return toColour(hit.getAmbient());
        }
        return 0;
    }

    private static int toColour(RealVector ambient) {
        final double r = 1.0;//ambient.getEntry(0);
        final double g = 1.0;//ambient.getEntry(1);
        final double b = 1.0;//ambient.getEntry(2);
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
        Vector3D a = VectorUtils.toVector3D(model.getVertices().get(obj.getVertices()[0]));
        Vector3D b = VectorUtils.toVector3D(model.getVertices().get(obj.getVertices()[1]));
        Vector3D c = VectorUtils.toVector3D(model.getVertices().get(obj.getVertices()[2]));
        Vector3D n = (c.subtract(a)).crossProduct(b.subtract(a)).normalize();
        double den = p1.dotProduct(n);
        if (den != 0.0) {
            double t = (a.dotProduct(n) - p0.dotProduct(n)) / den;
            if (t < 0) {
                return  new Intersection(false);
            }
            Vector3D p = p0.add(p1.scalarMultiply(t));
            Vector3D ac = a.subtract(c);
            Vector3D bc = b.subtract(c);
            Vector3D pc = p.subtract(c);
            double[][] mat = {{ac.getX(), bc.getX()}, {ac.getY(), bc.getY()}};
            double[] params = {pc.getX(), pc.getY()};
            RealVector constants = new ArrayRealVector(params, false);
            RealMatrix coefficients = new Array2DRowRealMatrix(mat);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
            RealVector solution = solver.solve(constants);
            double alpfa = solution.getEntry(0);
            double beta = solution.getEntry(1);
            boolean match = false;
            if (alpfa >=0 && beta >=0 && alpfa + beta < 1.0) {
                match = true;
            } else {
                match = false;
            }
            final Intersection intersection = new Intersection(match);
            intersection.setAmbient(obj.getAmbient());
            return intersection;
        }
        return new Intersection(false);
    }

    private static Ray rayThruPixel(Camera cam, int x, int y) {
        Ray result = new Ray();
        Vector3D from3 = VectorUtils.toVector3D(cam.getFrom());
        Vector3D up3 = VectorUtils.toVector3D(cam.getUp());
        Vector3D to3 = VectorUtils.toVector3D(cam.getTo());
        Vector3D w = from3.subtract(to3).normalize();
        Vector3D u = up3.crossProduct(w).normalize();
        Vector3D v = w.crossProduct(u);

        final int halfW = cam.getWidth() / 2;
        final int halfH = cam.getHeight() / 2;
        final float fovY = cam.getFov();// / halfW * halfH;
        final float fovX = fovY * halfW / halfH;;
        double alpha = Math.tan(Math.toRadians(fovX / 2)) * (x - halfW) / halfW;
        double beta = Math.tan(Math.toRadians(fovY / 2)) * (halfH - y) / halfH;
        Vector3D p13 = u.scalarMultiply(alpha).add(v.scalarMultiply(beta)).subtract(w);

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
        return result;
    }
}
