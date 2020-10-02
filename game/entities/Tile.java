package SpotGL.game.entities;

import SpotGL.core.graphics.Texture;
import SpotGL.core.objects.Entity;
import SpotGL.game.shaders.TerrainShader;

import static SpotGL.core.math.MathUtils.pixelToGL;

public class Tile extends Entity {

    public Tile(int width, int height, Texture texture) {
        super(0, 0, width, height, texture);
    }

    @Override
    public void update() {

    }

    public void render(TerrainShader shader, int x, int y) {
        setPosition(x, y);
        transformationMatrix.translate(pixelToGL(position));
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix);
        vertexArray.render(shader, texture);
        texture.unbind();
    }

}
