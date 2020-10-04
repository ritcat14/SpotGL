package SpotGL.game.entities;

import SpotGL.core.objects.Entity;

import static SpotGL.core.files.FileUtils.loadTexture;

public class Player extends Entity {

    public Player() {
        super(0, 0f, 0.5f, 0.5f, loadTexture("/images/player/walking/0a.png"));
    }

    @Override
    public void update() {

    }
}
