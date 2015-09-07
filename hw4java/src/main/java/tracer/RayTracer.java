package tracer;

import scene.Model;

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
        result.setP0(cam.getFrom());

        return result;
    }

    private static Camera getCamera(Model model) {
        Camera result = new Camera();
        result.setFrom(model.getFrom());
        result.setTo(model.getFrom());
        result.setUp(model.getUp());
        return result;
    }
}
