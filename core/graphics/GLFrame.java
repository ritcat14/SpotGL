package SpotGL.core.graphics;

import SpotGL.core.GLEngine;
import SpotGL.core.input.InputHandler;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLFrame {

    public static final float JAVA_WIDTH = 1920.f;
    public static final float JAVA_HEIGHT = 1080.f;

    public static final float glX = -1f;
    public static final float glY = -1f;
    public static final float glW = 2f;
    public static final float glH = 2f;

    public static final float FOV = (float)Math.toRadians(60.0f);
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000.f;

    private long windowID;
    private InputHandler inputHandler;

    public GLFrame(GLEngine glEngine, int width, int height) {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

        windowID = glfwCreateWindow(width, height, "Spot", NULL, NULL);

        if (windowID == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowID, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    windowID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        inputHandler = new InputHandler(glEngine);
        glfwSetKeyCallback(windowID, inputHandler.keyboard);
        glfwSetMouseButtonCallback(windowID, inputHandler.mouse);

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        glfwShowWindow(windowID);

        GL.createCapabilities();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void close() {
        glfwDestroyWindow(windowID);
    }

    public long getWindowID() {
        return windowID;
    }
}
