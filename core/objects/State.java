package SpotGL.core.objects;

import SpotGL.core.graphics.ShaderUtils;

public class State {

    private Entity background;
    private ShaderUtils shaderUtils;

    public State(ShaderUtils shaderUtils) {
        this.shaderUtils = shaderUtils;
        background = new Entity(50, 50, 1820, 980);
    }

    public void render() {
        shaderUtils.getShader("background").enable();
        background.render();
        shaderUtils.getShader("background").disable();
    }

}
