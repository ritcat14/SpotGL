package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;

import java.util.List;

public class Layer {

    private final int id;
    private final String name;
    private final int width;
    private final int height;
    private final List<Chunk> layerData;

    public Layer(int id, String name, int width, int height, List<Chunk> layerData) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.layerData = layerData;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", layerData=" + layerData +
                '}';
    }

    public void render(Shader shader, TileSet tileSet, int x, int y) {
        for (Chunk chunk : layerData) {
            chunk.render(shader, tileSet, x, y);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
