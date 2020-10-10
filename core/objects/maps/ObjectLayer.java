package SpotGL.core.objects.maps;

import SpotGL.core.graphics.Shader;
import SpotGL.core.objects.model.Entity;

import java.util.List;

public class ObjectLayer {

    private List<Entity> objectList;

    public ObjectLayer(List<Entity> objectList) {
        this.objectList = objectList;
    }

    public void update() {
        for (Entity entity : objectList) entity.update();
    }

    public void render(Shader shader) {
        for (Entity entity : objectList) entity.render(shader);
    }

    public void cleanUp() {
        for (Entity entity : objectList) entity.cleanUp();
        objectList.clear();
    }

}
