package SpotGL.game.states;

import SpotGL.core.graphics.GLFrame;
import SpotGL.core.input.InputHandler;
import SpotGL.core.objects.Camera;
import SpotGL.core.states.State;
import SpotGL.game.entities.Player;
import SpotGL.game.managers.EntityManager;
import SpotGL.game.managers.TerrainManager;
import SpotGL.game.maps.Map1;

import static SpotGL.core.states.StateName.GAME;
import static SpotGL.core.states.StateName.START;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Game extends State {

    private EntityManager entityManager;
    private TerrainManager terrainManager;

    public Game() {
        super(GAME);
    }

    @Override
    public void init(GLFrame glFrame) {
        Camera camera = new Camera();
        Map1 map = new Map1(camera);
        entityManager = new EntityManager(glFrame, camera);
        entityManager.setPlayer(new Player());

        terrainManager = new TerrainManager(glFrame, map, camera);
    }

    @Override
    public void update() {
        entityManager.update();
        terrainManager.update();
    }

    @Override
    public void render() {
        entityManager.render();
        terrainManager.render();
    }

    @Override
    public void cleanUp() {
        entityManager.cleanUp();
        terrainManager.cleanUp();
    }

    @Override
    public void onInput(InputHandler inputHandler) {
        if (inputHandler.keyPressed(GLFW_KEY_ESCAPE)) requestChange(START);
        else entityManager.onInput(inputHandler);
    }
}
