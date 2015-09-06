package tracer;

import scene.Model;

import java.awt.image.BufferedImage;

public class RayTracer {
    public static BufferedImage render(Model model) {
        BufferedImage result = new BufferedImage(model.getW(), model.getH(), BufferedImage.TYPE_INT_RGB);
        for (int x=0; x< model.getW(); ++x) {
            for (int y=0; y < model.getH(); ++y) {
                result.setRGB(x, y, 255 * 16*16 + 128);
            }
        }
        return result;
    }
}
