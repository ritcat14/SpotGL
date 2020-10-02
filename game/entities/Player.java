package SpotGL.game.entities;

import SpotGL.core.graphics.Texture;
import SpotGL.core.objects.Entity;

import static SpotGL.core.files.FileUtils.loadTexture;
import static SpotGL.core.graphics.GLFrame.JAVA_HEIGHT;
import static SpotGL.core.graphics.GLFrame.JAVA_WIDTH;

public class Player extends Entity {

    public Player() {
        super((JAVA_WIDTH/2) - 30, (JAVA_HEIGHT/2) - 30, 60, 60, loadTexture("/images/player/playerTest.png"));
    }

    @Override
    public void update() {

    }
}
