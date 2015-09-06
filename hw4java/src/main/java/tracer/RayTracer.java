package tracer;

import scene.Model;

import java.awt.image.BufferedImage;

public class RayTracer {
    public static BufferedImage render(Model model) {
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_4BYTE_ABGR);
        return result;
    }
}
