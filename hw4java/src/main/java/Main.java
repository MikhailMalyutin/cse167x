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
//        String fileName = "j:\\Work\\Learn\\cse167x1\\testscenes\\scene3.test";
//        Model model = SceneParser.parse(new File(fileName));
//        BufferedImage image = render(model);
//        File outFileName = new File("j:\\Work\\Learn\\cse167x1\\testscenes\\out.png");
//        ImageIO.write(image, "png", outFileName);
          //renderFile("scene1", "out");
          //renderFile("scene4-ambient");
          renderFile("scene4-diffuse");
//        renderFile("scene4-emission");
        //  renderFile("scene4-specular");
     //   renderFile("scene5");
    //    renderFile("scene6");
//        renderFile("scene7");
    }

    private static void renderFile(String fileName, String outName) throws Exception {
        final String workDir = "j:\\Work\\Learn\\cse167x1\\testscenes\\";
        String filePath = workDir + fileName + ".test";
        Model model = SceneParser.parse(new File(filePath));
        BufferedImage image = render(model);
        File outFileName = new File(workDir + outName + ".png");
        ImageIO.write(image, "png", outFileName);
    }

    private static void renderFile(String fileName) throws Exception {
        renderFile(fileName, fileName);
    }
}
