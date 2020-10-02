package SpotGL.core.graphics;

import SpotGL.core.files.FileUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static SpotGL.core.graphics.GLFrame.projectionMatrix;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;

    private Map<String, Shader> shaders = new HashMap<>();

    public void registerShader(String name) {
        Shader shader = load(name);
        shader.setUniformMatrix4f("projectionMatrix", projectionMatrix);
        shader.stop();
        shaders.put(name, shader);
    }

    public Shader load(String name) {
        String vertexPath = "shaders/" + name + "Vertex.glsl";
        String fragmentPath = "shaders/" + name + "Fragment.glsl";

        StringBuilder vertex = FileUtils.loadAsString(vertexPath);
        StringBuilder fragment = FileUtils.loadAsString(fragmentPath);

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
        Shader shader = null;
        try {
            Class<?> cls = Class.forName("SpotGL.game.shaders." + name + "Shader");
            Object shaderObject = cls.getDeclaredConstructor(int.class, int.class, int.class).newInstance(program, vertexID, fragmentID);
            shader = (Shader)shaderObject;
        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        glLinkProgram(program);
        glValidateProgram(program);

        return shader;
    }

    public Shader getShader(String name) {
        return shaders.get(name);
    }

}
