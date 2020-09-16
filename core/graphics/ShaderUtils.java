package SpotGL.core.graphics;

import SpotGL.core.files.FileUtils;

import java.util.HashMap;
import java.util.Map;

import static SpotGL.core.graphics.GLFrame.projectionMatrix;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;

    private Map<String, Shader> shaders = new HashMap<>();

    public void registerShader(String name) {
        Shader shader = load("shaders/" + name + "Vertex.glsl", "shaders/" + name + "Fragment.glsl");
        shader.enable();
        shader.setUniformMatrix4f("projectionMatrix", projectionMatrix);
        shader.disable();
        shaders.put(name, shader);
    }

    public Shader load(String vertexPath, String fragmentPath) {
        String vertex = FileUtils.loadAsString(vertexPath);
        String fragment = FileUtils.loadAsString(fragmentPath);

        int program = glCreateProgram();
        int vertexID = glCreateShader(GL_VERTEX_SHADER);
        int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertexID, vertex);
        glShaderSource(fragmentID, fragment);

        glCompileShader(vertexID);
        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertexID));
            return null;
        }

        glCompileShader(fragmentID);
        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragmentID));
            return null;
        }

        glAttachShader(program, vertexID);
        glAttachShader(program, fragmentID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);

        return new Shader(program);
    }

    public Shader getShader(String name) {
        return shaders.get(name);
    }

}
