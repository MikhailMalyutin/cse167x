package parser;

import org.apache.commons.io.FileUtils;
import scene.Model;

import java.io.File;

public class SceneParser {
    public static Model parse(File file) throws Exception {
        Model result = new Model();
        String str = FileUtils.readFileToString(file);
        String[] lines = str.split("\n");
        for (String line : lines) {
            String[] commands = line.split(" ");
            final String operator = commands[0].trim();
            if (operator.equals("size")) {
                result.setW(Integer.parseInt(commands[1]));
                result.setH(Integer.parseInt(commands[2]));
            }
        }

        return result;
    }
}
