package SpotGL.game.states;

import SpotGL.core.graphics.Shader;
import SpotGL.core.graphics.ShaderUtils;
import SpotGL.core.input.InputHandler;
import SpotGL.core.states.State;
import SpotGL.game.entities.Player;
import SpotGL.game.maps.Map1;
import SpotGL.game.shaders.EntityShader;
import SpotGL.game.shaders.TerrainShader;

import static SpotGL.core.states.StateName.GAME;

public class Game extends State {

    private final EntityShader entityShader;
    private final TerrainShader terrainShader;
    private Player player;
    private Map1 map;

    public Game(ShaderUtils shaderUtils) {
        super(shaderUtils, GAME);
        entityShader = (EntityShader)shaderUtils.getShader("Entity");
        terrainShader = (TerrainShader)shaderUtils.getShader("Terrain");
    }

    @Override
    public void init() {
        player = new Player();
        map = new Map1();
    }

    @Override
    public void update() {
        map.update();
        player.update();
    }

    @Override

    public void render() {
        entityShader.start();
        player.render(entityShader);
        entityShader.stop();
        terrainShader.start();
        map.render(terrainShader);
        terrainShader.stop();
    }

    @Override
    public void cleanUp() {
        player.cleanUp();
        map.cleanUp();
        entityShader.cleanUp();
        terrainShader.cleanUp();
    }

    @Override
    public void onEvent(InputHandler inputHandler) {
        map.onEvent(inputHandler);
    }
}
