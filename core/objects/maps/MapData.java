package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import org.joml.Vector2f;

import java.util.List;

public class MapData {

    private final List<Layer> layers;
    private final TileSet tileSet;

    public MapData(List<Layer> layers, TileSet tileSet) {
        this.layers = layers;
        this.tileSet = tileSet;
    }

    public void render(Shader shader, Vector2f centerPosition) {
        for (Layer layer : layers) {
            layer.render(shader, tileSet, centerPosition);
        }
    }

    public void cleanUp() {
        tileSet.cleanUp();
    }
}
