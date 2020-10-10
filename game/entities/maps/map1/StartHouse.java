package SpotGL.game.entities.maps.map1;

import SpotGL.core.objects.model.Entity;

import static SpotGL.core.utils.FileUtils.loadTexture;

public class StartHouse extends Entity {

    public StartHouse(float x, float y, float width, float height) {
        super(x, y, width, height, loadTexture("/maps/objects/Map1/startHouse.png"));
    }

    @Override
    public void update() {

    }
}
