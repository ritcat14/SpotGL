package SpotGL.game.managers;

import SpotGL.core.graphics.GLFrame;
import SpotGL.core.graphics.Shader;
import SpotGL.core.input.InputListener;
import SpotGL.core.objects.Camera;

public abstract class Manager implements InputListener {

    protected Shader shader;

    public Manager(Shader shader) {
        this.shader = shader;
    }

    public abstract void update();

    public abstract void render(GLFrame frame, Camera camera);

    public abstract void cleanUp();

}
