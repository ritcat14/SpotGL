package SpotGL.game.shaders;

import SpotGL.core.graphics.Shader;

public class EntityShader extends Shader {

    public EntityShader() throws Exception {
        super("shaders/entityVertex.glsl", "shaders/entityFragment.glsl");
    }


}
