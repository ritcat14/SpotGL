package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import SpotGL.core.input.InputHandler;
import SpotGL.game.shaders.TerrainShader;
import org.lwjgl.glfw.GLFW;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static SpotGL.core.files.FileUtils.readMap;

public abstract class Map {

    private List<Layer> layers;
    private TileSet tileSet;
    private int x, y;
    private MapData mapData;

    private boolean up, down, left, right;

    public Map(String fileName) {
        try {
            mapData = readMap(fileName);
            tileSet = mapData.getTileSet();
            layers = mapData.getLayers();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (up) {
            y +=5;
        }
        if (down) {
            y -=5;
        }
        if (left) {
            x +=5;
        }
        if (right) {
            x -=5;
        }
    }

    public void render(TerrainShader shader) {
        for (Layer layer : layers) {
            layer.render(shader, tileSet, x, y);
        }
    }

    public void onEvent(InputHandler inputHandler) {
        if (up) {
            if (inputHandler.keyReleased(GLFW.GLFW_KEY_W)) up = false;
        } else up = inputHandler.keyPressed(GLFW.GLFW_KEY_W);
        if (down) {
            if (inputHandler.keyReleased(GLFW.GLFW_KEY_S)) down = false;
        } else down = inputHandler.keyPressed(GLFW.GLFW_KEY_S);
        if (left) {
            if (inputHandler.keyReleased(GLFW.GLFW_KEY_A)) left = false;
        } else left = inputHandler.keyPressed(GLFW.GLFW_KEY_A);
        if (right) {
            if (inputHandler.keyReleased(GLFW.GLFW_KEY_D)) right = false;
        } else right = inputHandler.keyPressed(GLFW.GLFW_KEY_D);
    }

    public void cleanUp() {
        tileSet.cleanUp();
    }

}
