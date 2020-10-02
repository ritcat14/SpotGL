package SpotGL.core.math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

    public static ByteBuffer createByteBuffer(byte[] array) {
        ByteBuffer result = org.lwjgl.BufferUtils.createByteBuffer(array.length);
        result.put(array).flip();
        return result;
    }

    public static FloatBuffer createFloatBuffer(float[] array) {
        FloatBuffer result = org.lwjgl.BufferUtils.createFloatBuffer(array.length);
        result.put(array).flip();
        return result;
    }

    public static IntBuffer createIntBuffer(int[] array) {
        IntBuffer result = org.lwjgl.BufferUtils.createIntBuffer(array.length);
        result.put(array).flip();
        return result;
    }

}
