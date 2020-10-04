package SpotGL.core.objects;

import SpotGL.core.graphics.Shader;
import SpotGL.core.graphics.Texture;
import SpotGL.core.graphics.VertexArray;
import SpotGL.core.math.Transformation;
import org.joml.Vector3f;

import static SpotGL.core.math.MathUtils.*;

public abstract class Entity {

    protected VertexArray vertexArray;
    protected Texture texture;

    protected Vector3f position;
    protected Vector3f size;
    protected Vector3f rotation;

    public Entity(float x, float y, float width, float height, Texture texture) {
        this.vertexArray = createEntityVertexArray(this);
        this.texture = texture;

        this.position = new Vector3f(0f, 0f, 0.0f);
        this.size = new Vector3f(1f, 1f, 1f);
        this.rotation = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getSize() {
        return size;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setSize(float width, float height) {
        this.size.x = width;
        this.size.y = height;
    }

    public void setRotation(float xRot, float yRot, float rotZ) {
        this.rotation.x = xRot;
        this.rotation.y = yRot;
        this.rotation.z = rotZ;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public abstract void update();

    public void render(Shader shader, Transformation transformation) {
        shader.setUniformMatrix4f("transformationMatrix", transformation.getTransformationMatrix(position, rotation, size));
        vertexArray.render(shader, texture);
    }

    public void cleanUp() {
        vertexArray.cleanUp();
        texture.cleanUp();
    }


}
