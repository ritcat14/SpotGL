package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import SpotGL.game.entities.Tile;

import java.util.Arrays;

public class Chunk {

    private final int x, y, width, height, ID;
    private final int[][] data;

    public Chunk(int id, int x, int y, int width, int height, int[][] data) {
        this.ID = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", ID=" + ID +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public void render(Shader shader, TileSet tileSet, int mapX, int mapY) {
        for (int y1 = 0; y1 < height; y1++) {
            for (int x1 = 0; x1 < width; x1++) {
                int tileID = data[y1][x1];
                int tileX = mapX + ((x + x1) * tileSet.getWidth()) + (x1 * 40);
                int tileY = mapY + ((y + y1) * tileSet.getHeight() + y1 * 40);
                Tile tile = tileSet.getTile(tileID);
                if (tile != null) tile.render(shader, tileX, tileY);
            }
        }
    }

}
