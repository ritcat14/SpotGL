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

public class Game extends State {

    private EntityManager entityManager;
    private TerrainManager terrainManager;
    private Camera camera;

    public Game(GLFrame glFrame) {
        super(glFrame, GAME);
    }

    @Override
    public void init() {
        camera = new Camera();
        Map1 map = new Map1(camera);
        entityManager = new EntityManager(map, camera);
        entityManager.setPlayer(new Player());

        terrainManager = new TerrainManager(map);
    }

    @Override
    public void update() {
        entityManager.update();
        terrainManager.update();
    }

    @Override
    public void render() {
        entityManager.render(glFrame, camera);
        terrainManager.render(glFrame, camera);
    }

    @Override
    public void cleanUp() {
        entityManager.cleanUp();
        terrainManager.cleanUp();
    }

    @Override
    public void onInput(InputHandler inputHandler) {
        entityManager.onInput(inputHandler);
    }
}
