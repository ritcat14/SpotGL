package SpotGL.core.graphics;

import SpotGL.core.math.Matrix4f;
import SpotGL.core.math.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int ID;
    private boolean enabled;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(int shaderID) {
        this.ID = shaderID;
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);
        int result = glGetUniformLocation(ID, name);
        if (result == 1) System.err.println("Could not find uniform variable '" + name + "'!");
        else
            locationCache.put(name, result);
        return result;
    }

    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector3f) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix4f) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix4f.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(ID);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

}
