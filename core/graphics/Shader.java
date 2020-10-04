package SpotGL.core.graphics;
import SpotGL.core.files.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    protected final int ID;
    protected final String name;

    private int vertexID, fragmentID;

    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(String vertex, String fragment) throws Exception {
        this.name = vertex.substring(vertex.length() - 17, vertex.length() - 11);
        System.out.println(name);
        StringBuilder vertexString = FileUtils.loadAsString(vertex);
        StringBuilder fragmentString = FileUtils.loadAsString(fragment);

        this.ID = createShader(vertexString, fragmentString);
    }

    private int createShader(StringBuilder vertex, StringBuilder fragment) throws Exception {
        int ID = glCreateProgram();
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);


        glShaderSource(vertexID, vertex);
        glShaderSource(fragmentID, fragment);

        glCompileShader(vertexID);
        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertexID));
            return 0;
        }

        glCompileShader(fragmentID);
        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragmentID));
            return 0;
        }

        glAttachShader(ID, vertexID);
        glAttachShader(ID, fragmentID);

        glLinkProgram(ID);
        glValidateProgram(ID);

        return ID;
    }

    public void bind() {
        glUseProgram(ID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanUp() {
        unbind();
        glDetachShader(ID, vertexID);
        glDetachShader(ID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        glDeleteProgram(ID);
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);
        int result = glGetUniformLocation(ID, name);
        if (result < 0) System.err.println("Could not find uniform variable '" + name + "'!");
        else locationCache.put(name, result);
        return result;
    }

    public void setUniformBoolean(String name, boolean value) {
        float toLoad = (value) ? 1 : 0;
        setUniform1f(name, toLoad);
    }

    public void setUniform1i(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector3f) {
        glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix4f) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(16);
            matrix4f.get(floatBuffer);
            glUniformMatrix4fv(getUniform(name), false, floatBuffer);
        }
    }
}
