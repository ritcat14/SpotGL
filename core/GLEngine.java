package SpotGL.core;

import SpotGL.core.graphics.GLFrame;
import SpotGL.core.input.InputHandler;
import SpotGL.core.states.StateManager;
import SpotGL.game.states.Game;

import static java.lang.System.currentTimeMillis;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLEngine implements Runnable {

    private Thread thread;
    private GLFrame glFrame;

    private InputHandler inputHandler;
    private StateManager stateManager;

    private void init() {
        glFrame = new GLFrame(this, 1920, 1080);

        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        inputHandler = glFrame.getInputHandler();
        inputHandler.init(glFrame.getWindowID());

        stateManager = new StateManager();
        stateManager.addState(new Game());
    }

    public synchronized void start() {
        thread = new Thread(this, "Main");
        thread.start();
    }

    public synchronized void stop() {
        stateManager.cleanUp();
        glfwFreeCallbacks(glFrame.getWindowID());
        glfwDestroyWindow(glFrame.getWindowID());

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void onEvent() {
        stateManager.onEvent(inputHandler);
    }

    @Override
    public void run() {
        init();

        glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

        double secsPerUpdate = 1.0d / 30.0d;
        double previous = currentTimeMillis();
        double steps = 0.0;

        while (!glfwWindowShouldClose(glFrame.getWindowID())) {
            double loopStartTime = currentTimeMillis();
            double elapsed = loopStartTime - previous;
            previous = loopStartTime;
            steps += elapsed;

            while (steps >= secsPerUpdate) {
                update();
                steps -= secsPerUpdate;
            }

            render();
            sync(loopStartTime);
        }

        stop();

    }

    private void sync(double loopStartTime) {
        float loopSlot = 1f / 50;
        double endTime = loopStartTime + loopSlot;
        while(currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {}
        }
    }

    private void update() {
        inputHandler.update();
        stateManager.update();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        stateManager.render();
        glfwSwapBuffers(glFrame.getWindowID());
    }

}
