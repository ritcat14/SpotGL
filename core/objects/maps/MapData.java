package SpotGL.core.objects.maps;

import java.util.List;

public class MapData {

    private List<Layer> layers;
    private TileSet tileSet;

    public MapData(List<Layer> layers, TileSet tileSet) {
        this.layers = layers;
        this.tileSet = tileSet;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
