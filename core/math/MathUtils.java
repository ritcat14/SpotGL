package core.math;

import core.graphics.VertexArray;

import java.awt.*;

import static core.graphics.GLFrame.*;

public class MathUtils {

    private MathUtils() {}

    private static byte[] indices = {
            0, 1, 2,
            0, 2, 3
    };

    private static float[] textureCoords = {
            0, 1,
            0, 0,
            1, 0,
            1, 1
    };

    public static float roundFloat(float f) {
        return (float)Math.round(f * 12.0f) / 12.0f;
    }

    public static float roundFloatToHalf(float f) {
        return (float)Math.round(f * 2.0f) / 2.0f;
    }

    public static float[] pixelToGL(float x, float y, float width, float height) {
        float glWidth = (width / 1920.0f)*glW;
        float glHeight = (height / 1080.0f)*glH;

        float glXPos = glX + ((x / 1920.0f)*glW);
        float glYPos = -(glY + ((y / 1080.0f) * glH));

        return new float[] {
                glXPos, glYPos, 0.0f, // tl
                glXPos, glYPos - glHeight, 0.0f, // bl
                glXPos + glWidth, glYPos - glHeight, 0.0f, // br
                glXPos + glWidth, glYPos, 0.0f // tr
        };
    }

    public static VertexArray createVertexFromRectangle(Rectangle rectangle) {
        return new VertexArray(pixelToGL(rectangle.x, rectangle.y, rectangle.width, rectangle.height), indices, textureCoords);
    }

}
