package SpotGL.game.shaders;

import SpotGL.core.graphics.Shader;

import static SpotGL.core.graphics.ShaderUtils.TCOORD_ATTRIB;
import static SpotGL.core.graphics.ShaderUtils.VERTEX_ATTRIB;

public class TerrainShader extends Shader {

    public TerrainShader(int shaderID, int vertexID, int fragmentID) {
        super(shaderID, vertexID, fragmentID);
    }

    @Override
    public void bindAttributes() {
        bindAttribute(VERTEX_ATTRIB, "position");
        bindAttribute(TCOORD_ATTRIB, "textureCoords");
    }
}
