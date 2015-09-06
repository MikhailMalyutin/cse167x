import parser.SceneParser;
import scene.Model;
import tracer.RayTracer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static tracer.RayTracer.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = "j:\\Work\\Learn\\cse167x1\\testscenes\\scene1.test";
        Model model = SceneParser.parse(new File(fileName));
        BufferedImage image = render(model);
        File outFileName = new File("j:\\Work\\Learn\\cse167x1\\testscenes\\out.png");
        ImageIO.write(image, "png", outFileName);
    }
}
