package SpotGL.game.managers;

import SpotGL.core.graphics.GLFrame;
import SpotGL.core.graphics.Shader;
import SpotGL.core.input.InputHandler;
import SpotGL.core.objects.Camera;
import SpotGL.core.objects.maps.Map;

public class TerrainManager extends Manager {

    private Map map;

    public TerrainManager(Map map) {
        super(new Shader("shaders/terrainVertex.glsl", "shaders/terrainFragment.glsl"));
        this.map = map;
    }

    @Override
    public void update() {
        map.update();
    }

    @Override
    public void render(GLFrame frame, Camera camera) {
        shader.bind();
        shader.setUniformMatrix4f("viewMatrix", camera.getViewMatrix());
        shader.setUniformMatrix4f("projectionMatrix", frame.getProjectionMatrix());

        map.render(shader);

        shader.unbind();
    }

    @Override
    public void cleanUp() {
        map.cleanUp();
        shader.cleanUp();
    }

    @Override
    public void onInput(InputHandler inputHandler) {

    }
}
