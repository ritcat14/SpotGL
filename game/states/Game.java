package SpotGL.game.states;

import SpotGL.core.input.InputHandler;
import SpotGL.core.math.Camera;
import SpotGL.core.math.Transformation;
import SpotGL.core.states.State;
import SpotGL.game.entities.Player;
import SpotGL.game.shaders.EntityShader;

import static SpotGL.core.states.StateName.GAME;

public class Game extends State {

    private EntityShader entityShader;
    private Player player;
    private Camera camera;
    private Transformation transformation;

    private boolean up, down, left, right;
    //private Map1 map;

    public Game() {
        super(GAME);
    }

    @Override
    public void init() {
        try {
            entityShader = new EntityShader();
        } catch (Exception e) {
            System.err.println("Failed in creating shaders for state: " + getStateName().toString());
            e.printStackTrace();
        }

        transformation = new Transformation();

        camera = new Camera();

        player = new Player();
        //map = new Map1();
    }

    @Override
    public void update() {
        //map.update();
        player.update();
    }

    @Override

    public void render() {
        entityShader.bind();
        entityShader.setUniformMatrix4f("viewMatrix", transformation.getViewMatrix(camera));
        entityShader.setUniformMatrix4f("projectionMatrix", transformation.getProjectionMatrix());
        player.render(entityShader, transformation);
        entityShader.unbind();
        //terrainShader.start();
        //map.render(terrainShader);
        //terrainShader.stop();
    }

    @Override
    public void cleanUp() {
        player.cleanUp();
        //map.cleanUp();
        entityShader.cleanUp();
    }

    @Override
    public void onEvent(InputHandler inputHandler) {
        camera.move(inputHandler);
    }
}
