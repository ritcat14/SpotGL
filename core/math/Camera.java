package SpotGL.core.math;

import SpotGL.core.input.InputHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private boolean up, down, left, right;
    private Vector3f position = new Vector3f();
    private float pitch;
    private float yaw;
    private float roll;

    public void update() {
    }

    public void move(InputHandler inputHandler) {
        if (up) {
            if (inputHandler.keyReleased(org.lwjgl.glfw.GLFW.GLFW_KEY_W)) up = false;
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

        if (up) {
            position.z -= 0.02f;
        }
        if (right) {
            position.x += 0.02f;
        }
        if (down) {
            position.z += 0.02f;
        }
        if (left) {
            position.x -= 0.02f;
        }
    }

    public Vector3f getInversePosition() {
        return new Vector3f(-position.x, -position.y, -position.z);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
