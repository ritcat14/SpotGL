package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import SpotGL.core.objects.Camera;
import org.joml.Vector2f;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static SpotGL.core.utils.FileUtils.readMap;

public abstract class Map {

    protected MapData mapData;
    protected Camera camera;
    protected Vector2f centerPosition = new Vector2f();

    public Map(Camera camera, String fileName) {
        this.camera = camera;
        try {
            mapData = readMap(fileName);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        centerPosition.x = camera.getPosition().x;
        centerPosition.y = camera.getPosition().y;
    }

    public void render(Shader shader) {
        mapData.render(shader, centerPosition);
    }

    public void cleanUp() {
        mapData.cleanUp();
    }

}
