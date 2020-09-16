package SpotGL.core.math;

import java.nio.FloatBuffer;

public class Matrix4f {

    public static int SIZE = 4 * 4;
    public static int WIDTH = 4;

    public float[] elements = new float[4 * 4];

    public Matrix4f() {
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < WIDTH; i++) {
            result.elements[i + i * WIDTH] = 1.0f;
        }
        return result;
    }

    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();
        result.elements[0] = 2.0f / (right - left);
        result.elements[1 + WIDTH] = 2.0f / (top - bottom);
        result.elements[2 + 2 * WIDTH] = 2.0f / (near - far);

        result.elements[3 * WIDTH] = (left + right) / (left - right);
        result.elements[1 + 3 * WIDTH] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * WIDTH] = (far + near) / (far - near);

        return result;
    }

    public static Matrix4f translate(Vector3f vector3f) {
        Matrix4f result = identity();

        result.elements[3 * WIDTH] = vector3f.x;
        result.elements[1 + 3 * WIDTH] = vector3f.y;
        result.elements[2 + 3 * WIDTH] = vector3f.z;
        return result;
    }

    public static Matrix4f rotate(float angle) {
        Matrix4f result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0] = cos;
        result.elements[1] = sin;

        result.elements[WIDTH] = -sin;
        result.elements[1 + WIDTH] = cos;

        return result;
    }

    public Matrix4f multiply(Matrix4f matrix4f) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < WIDTH; x++) {
                float sum = 0.0f;
                for (int e = 0; e < WIDTH; e++) {
                    sum += this.elements[x + e * WIDTH] * matrix4f.elements[e + y * WIDTH];
                }
                result.elements[x + y * WIDTH] = sum;
            }
        }
        return result;
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }

}
