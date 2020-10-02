package SpotGL.core.graphics;

import SpotGL.core.math.Matrix4f;
import SpotGL.core.math.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    private final int ID, vertexID, fragmentID;
    private boolean enabled;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(int shaderID, int vertexID, int fragmentID) {
        this.ID = shaderID;
        this.vertexID = vertexID;
        this.fragmentID = fragmentID;
        bindAttributes();
    }

    public abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(ID, attribute, variableName);
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);
        int result = glGetUniformLocation(ID, name);
        if (result == 1) System.err.println("Could not find uniform variable '" + name + "'!");
        else locationCache.put(name, result);
        return result;
    }

    public void setUniformBoolean(String name, boolean value) {
        float toLoad = (value) ? 1 : 0;
        setUniform1f(name, toLoad);
    }

    public void setUniform1i(String name, int value) {
        if (!enabled) start();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) start();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!enabled) start();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector3f) {
        if (!enabled) start();
        glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix4f) {
        if (!enabled) start();
        glUniformMatrix4fv(getUniform(name), false, matrix4f.toFloatBuffer());
    }

    public void start() {
        glUseProgram(ID);
        enabled = true;
    }

    public void stop() {
        glUseProgram(0);
        enabled = false;
    }

    public void cleanUp() {
        stop();
        glDetachShader(ID, vertexID);
        glDetachShader(ID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        glDeleteProgram(ID);
    }

}
