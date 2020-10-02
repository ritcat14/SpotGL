package SpotGL.core.objects;

import SpotGL.core.graphics.Shader;
import SpotGL.core.graphics.Texture;
import SpotGL.core.graphics.VertexArray;
import SpotGL.core.math.Matrix4f;
import SpotGL.core.math.Vector3f;
import SpotGL.game.shaders.EntityShader;

import static SpotGL.core.math.MathUtils.createVertexFromRectangle;
import static SpotGL.core.math.MathUtils.pixelToGL;

public abstract class Entity {

    protected VertexArray vertexArray;
    protected Texture texture;
    protected Vector3f position;
    protected Vector3f size;
    protected Vector3f rotation;
    protected Matrix4f transformationMatrix;

    public Entity(int x, int y, int width, int height, Texture texture) {
        this.texture = texture;
        this.position = new Vector3f(x, y, 1f);
        this.size = new Vector3f(width, height, 1.0f);
        this.rotation = new Vector3f();
        vertexArray = createVertexFromRectangle(this);
        transformationMatrix = Matrix4f.identity();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void move(int xMove, int yMove) {
        position.x += xMove;
        position.y += yMove;
    }

    public void rotate(int xRot, int yRot) {

    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public abstract void update();

    public void render(EntityShader shader) {
        transformationMatrix.translate(pixelToGL(position));
        transformationMatrix.rotate((float)Math.toRadians(rotation.getXYAngle()));
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix.translate(pixelToGL(position)));
        vertexArray.render(shader, texture);
    }

    public void cleanUp() {
        vertexArray.cleanUp();
        texture.cleanUp();
    }

}
