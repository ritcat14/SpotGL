package SpotGL.game.entities;

import SpotGL.core.graphics.Shader;
import SpotGL.core.input.InputHandler;
import SpotGL.core.input.InputListener;
import SpotGL.core.objects.Camera;
import SpotGL.core.objects.model.Entity;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import static SpotGL.core.VarStore.JAVA_HEIGHT;
import static SpotGL.core.VarStore.JAVA_WIDTH;
import static SpotGL.core.utils.FileUtils.loadTexture;
import static SpotGL.core.utils.MatrixUtils.updateTransformationMatrix;

public class Player extends Entity implements InputListener {

    private boolean up, down, left, right;

    public Player() {
        super((JAVA_WIDTH/2) - 30, (JAVA_HEIGHT/2) - 30, 60, 60,
                loadTexture("/images/player/playerTest.png"));
    }

    public void update(Camera camera) {
        if (up) {
            camera.getPosition().y -=5f;
        }
        if (down) {
            camera.getPosition().y +=5f;
        }
        if (left) {
            camera.getPosition().x -=5f;
        }
        if (right) {
            camera.getPosition().x +=5f;
        }
        camera.update();
        update();
    }

    @Override
    public void update() {
        updateTransformationMatrix(this);
    }

    @Override
    public void render(Shader shader) {
        shader.setUniformMatrix4f("viewMatrix", new Matrix4f().identity());
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix);
        vertexArray.render(shader, texture);
    }

    @Override
    public void onInput(InputHandler inputHandler) {
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
}
