package tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import scene.Model;
import utils.VectorUtils;

import java.awt.image.BufferedImage;

public class RayTracer {
    public static BufferedImage render(Model model) {
        Camera cam = getCamera(model);
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < model.getW(); ++x) {
            for (int y = 0; y < model.getH(); ++y) {
                Ray ray = rayThruPixel(cam, x, y);
                Intersection hit = intersect(ray, model);
                result.setRGB(x, y, findColor(hit));
            }
        }
        return result;
    }

    private static int findColor(Intersection hit) {
        return 255 * 16 * 16 + 128;
    }

    private static Intersection intersect(Ray ray, Model model) {
        Intersection result = new Intersection();

        return result;
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
        double alpha = Math.tan(Math.toRadians(cam.getFov() / 2)) * (x - halfW) / halfW;
        double beta = Math.tan(Math.toRadians(cam.getFov() / 2)) * (halfH - y) / halfH;
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
