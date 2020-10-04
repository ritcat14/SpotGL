package SpotGL.game.entities;

import SpotGL.core.graphics.Shader;
import SpotGL.core.graphics.Texture;
import SpotGL.core.objects.Entity;


public class Tile extends Entity {

    public Tile(int width, int height, Texture texture) {
        super(0, 0, width, height, texture);
    }

    @Override
    public void update() {

    }

    public void render(Shader shader, int x, int y) {
        setPosition(x, y, 1.0f);
        //shader.setUniformMatrix4f("transformationMatrix", createTransformationMatrix(position, rotation, size));
        vertexArray.render(shader, texture);
        texture.unbind();
    }

}
