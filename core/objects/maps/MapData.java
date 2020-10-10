package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import org.joml.Vector2f;

import java.util.List;

public class MapData {

    private final List<TileLayer> tileLayers;
    private final List<ObjectLayer> objectLayers;
    private final TileSet tileSet;

    public MapData(List<TileLayer> tileLayers, List<ObjectLayer> objectLayers, TileSet tileSet) {
        this.tileLayers = tileLayers;
        this.objectLayers = objectLayers;
        this.tileSet = tileSet;
    }

    public void update() {
        for (ObjectLayer objectLayer : objectLayers) {
            objectLayer.update();
        }
    }

    public void renderObjectLayers(Shader shader) {
        for (ObjectLayer objectLayer : objectLayers) {
            objectLayer.render(shader);
        }
    }

    public void renderTileLayers(Shader shader, Vector2f centerPosition) {
        for (TileLayer tileLayer : tileLayers) {
            tileLayer.render(shader, tileSet, centerPosition);
        }
    }

    public void cleanUp() {
        tileSet.cleanUp();
    }
}
