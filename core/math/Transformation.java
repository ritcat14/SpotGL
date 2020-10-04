package SpotGL.core.math;

import SpotGL.core.graphics.GLFrame;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static SpotGL.core.graphics.GLFrame.*;

public class Transformation {

    private final Matrix4f projectionMatrix;
    private final Matrix4f transformationMatrix;
    private final Matrix4f viewMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        transformationMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix() {
        float aspectRatio = GLFrame.JAVA_WIDTH/GLFrame.JAVA_HEIGHT;
        projectionMatrix.identity();
        projectionMatrix.perspective(FOV, aspectRatio, NEAR_PLANE, FAR_PLANE);
        return projectionMatrix;
    }

    public final Matrix4f getTransformationMatrix(Vector3f offset, Vector3f rotation, Vector3f scale) {
        projectionMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return projectionMatrix;
    }

    public final Matrix4f getViewMatrix(Camera camera) {
        viewMatrix.identity().rotateX(camera.getPitch()).
                rotateY(camera.getYaw()).
                rotateZ(camera.getRoll()).
                translate(camera.getInversePosition());
        return viewMatrix;
    }

}
