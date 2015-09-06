package parser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import scene.Model;
import scene.TriangleObject;

import java.io.File;

public class SceneParser {
    public static Model parse(File file) throws Exception {
        Model result = new Model();
        String str = FileUtils.readFileToString(file);
        String[] lines = str.split("\n");

        RealVector ambient = null;
        RealVector diffuse = null;
        RealVector specular = null;
        RealVector emission = null;
        float shininess = 0.0f;

        for (String line : lines) {
            String[] commands = line.split(" ");
            final String operator = commands[0].trim();
            if (operator.equals("size")) {
                result.setW(Integer.parseInt(commands[1]));
                result.setH(Integer.parseInt(commands[2]));
            } else if (operator.equals("vertex")) {
                result.getVertices().add(readVec4(commands, 1));
            } else if (operator.equals("tri")) {
                int[] vertices = readInt(commands, 1, 3);
                final TriangleObject triangleObject = new TriangleObject(vertices);
                triangleObject.setAmbient(ambient);
                triangleObject.setDiffuse(diffuse);
                triangleObject.setSpecular(specular);
                triangleObject.setEmission(emission);
                triangleObject.setShininess(shininess);
                result.getObjects().add(triangleObject);
            } else if (operator.equals("camera")) {
                RealVector from = readVec4(commands, 1);
                RealVector to = readVec4(commands, 4);
                RealVector up = readVec4(commands, 7);
                float fov = Float.valueOf(commands[10]);
                result.setFrom(from);
                result.setTo(to);
                result.setUp(up);
                result.setFov(fov);
            } else if (operator.equals("ambient ")) {
                ambient = readVec3(commands, 1);
            } else if (operator.equals("diffuse ")) {
                diffuse = readVec3(commands, 1);
            } else if (operator.equals("specular ")) {
                specular = readVec3(commands, 1);
            } else if (operator.equals("emission ")) {
                emission = readVec3(commands, 1);
            } else if (operator.equals("shininess ")) {
                shininess = Float.valueOf(commands[1]);
            }
        }

        return result;
    }

    private static RealVector readVec3(String[] commands, int startIndex) {
        double[] vector = new double[3];
        vector[0] = Double.valueOf(commands[startIndex]);
        vector[1] = Double.valueOf(commands[startIndex + 1]);
        vector[2] = Double.valueOf(commands[startIndex + 2]);
        return new ArrayRealVector(vector);
    }

    private static RealVector readVec4(String[] commands, int startIndex) {
        double[] vector = readVector(commands, 1);
        return new ArrayRealVector(vector);
    }

    private static double[] readVector(String[] commands, int startIndex) {
        double[] vector = new double[4];
        vector[0] = Double.valueOf(commands[startIndex]);
        vector[1] = Double.valueOf(commands[startIndex + 1]);
        vector[2] = Double.valueOf(commands[startIndex + 2]);
        vector[3] = 1.0;
        return vector;
    }

    private static int[] readInt(String[] commands, int startIndex, int n) {
        int[] result = new int[n];
        for (int i=0; i<n; ++i) {
            result[i] = Integer.valueOf(commands[startIndex+i]);
        }
        return result;
    }
}
