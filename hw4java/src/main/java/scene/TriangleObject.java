package scene;

public class TriangleObject extends DrawedObject {
    private int[] vertices = new int[3];

    public TriangleObject(int[] vertices) {
        this.vertices = vertices;
    }

    public int[] getVertices() {
        return vertices;
    }
}
