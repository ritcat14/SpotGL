package core;

import core.graphics.GLFrame;
import core.graphics.ShaderUtils;
import core.objects.State;

import static core.graphics.GLFrame.projectionMatrix;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLEngine implements Runnable {

    private Thread thread;
    private GLFrame glFrame;

    private State state;
    private ShaderUtils shaderUtils;

    private void init() {
        glFrame = new GLFrame(1920, 1080);

        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        shaderUtils = new ShaderUtils();

        shaderUtils.registerShader("entity");
        shaderUtils.registerShader("background");

        state = new State(shaderUtils);
    }

    public synchronized void start() {
        thread = new Thread(this, "Main");
        thread.start();
    }

    public synchronized void stop() {
        glfwFreeCallbacks(glFrame.getWindowID());
        glfwDestroyWindow(glFrame.getWindowID());

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public void run() {
        init();

        glClearColor(0.25f, 0.25f, 0.25f, 0.0f);

        while ( !glfwWindowShouldClose(glFrame.getWindowID()) ) {
            update();
            render();
        }

        stop();

    }

    private void update() {
        glfwPollEvents();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        state.render();
        glfwSwapBuffers(glFrame.getWindowID());
    }

}
