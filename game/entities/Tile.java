package SpotGL.game.entities;

import SpotGL.core.graphics.Shader;
import SpotGL.core.graphics.Texture;
import SpotGL.core.objects.model.Entity;

import static SpotGL.core.VarStore.JAVA_HEIGHT;
import static SpotGL.core.VarStore.JAVA_WIDTH;
import static SpotGL.core.utils.MatrixUtils.updateTransformationMatrix;
import static org.lwjgl.opengl.GL11.*;


public class Tile extends Entity {

    public Tile(int width, int height, Texture texture) {
        super(0, 0, width, height, texture);
    }

    @Override
    public void update() {

    }

    public void render(Shader shader, float x, float y) {
        if (x < 0 || y < 0 || x >= JAVA_WIDTH || y >= JAVA_HEIGHT) return;
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        setPosition(x, y);
        updateTransformationMatrix(this);
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix);
        vertexArray.render(shader, texture);
        glDisable(GL_BLEND);
    }

}
