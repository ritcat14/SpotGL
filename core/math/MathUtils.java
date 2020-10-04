package SpotGL.core.math;

import SpotGL.core.graphics.VertexArray;
import SpotGL.core.objects.Entity;
import org.joml.Vector3f;

import static SpotGL.core.graphics.GLFrame.*;

public class MathUtils {

    private MathUtils() {}

    private static float[] vertices = new float[] {
            -1f,  1f, -1.05f,
            -1f, -1f, -1.05f,
            1f, -1f, -1.05f,
            1f,  1f, -1.05f,
    };

    private static int[] indices = new int[] {
            0, 1, 3, 3, 1, 2,
    };

    private static float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    public static float roundFloat(float f) {
        return (float)Math.round(f * 12.0f) / 12.0f;
    }

    public static float roundFloatToHalf(float f) {
        return (float)Math.round(f * 2.0f) / 2.0f;
    }

    public static Vector3f pixelToGL(Vector3f pixelVector) {
        Vector3f glVector = new Vector3f();
        glVector.x = glX + ((pixelVector.x / JAVA_WIDTH) * glW);
        glVector.y = -(glY + ((pixelVector.y / JAVA_HEIGHT) * glH));
        return glVector;
    }

    public static Vector3f pixelSizeToGL(Vector3f pixelVector) {
        Vector3f glVector = new Vector3f();
        glVector.x = (pixelVector.x / JAVA_WIDTH) * glW;
        glVector.y = (pixelVector.y / JAVA_HEIGHT) * glH;
        return glVector;
    }

    public static float[] pixelToGL(float x, float y, float width, float height) {
        Vector3f glSize = pixelSizeToGL(new Vector3f(width, height, 0.0f));
        Vector3f glPos = pixelToGL(new Vector3f(x, y, 0.0f));
        /*float glWidth = (width / 1920.0f)*glW;
        float glHeight = (height / 1080.0f)*glH;

        float glXPos = glX + ((x / 1920.0f)*glW);
        float glYPos = -(glY + ((y / 1080.0f) * glH));*/

        return new float[] {
                glPos.x, glPos.y, 0.0f, // tl
                glPos.x, glPos.y - glSize.y, 0.0f, // bl
                glPos.x + glSize.x, glPos.y - glSize.y, 0.0f, // br
                glPos.x + glSize.x, glPos.y, 0.0f // tr
        };
    }

    public static VertexArray createEntityVertexArray(Entity entity) {
        Vector3f position = entity.getPosition();
        Vector3f size = entity.getSize();
        /*float[] vertices = {
                position.x, position.y, position.z,
                position.x, position.y - size.y, position.z,
                position.x + size.x, position.y - size.y, position.z,
                position.x + size.x, position.y, position.z
        };*/
        return new VertexArray(vertices, indices, textureCoords);
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static double getTOA(double opposite, double adjacent) {
        return Math.toDegrees(Math.atan2(opposite, adjacent));
    }

    public static double getSOH(double opposite, double hypotenuse) {
        return Math.toDegrees(Math.asin(opposite/hypotenuse));
    }

    public static double getCAH(double adjacent, double hypotenuse) {
        return Math.toDegrees(Math.acos(adjacent/hypotenuse));
    }

    public static double missingAngleTriangle(double width, double height) {
        double angle1 = 90;
        double angle2 = getTOA(width, height);
        return 180 - angle1 - angle2;
    }

}
