package core.objects;

import core.graphics.VertexArray;

import java.awt.*;

import static core.math.MathUtils.createVertexFromRectangle;

public class Entity extends Rectangle {

    protected VertexArray vertexArray;

    public Entity(int x, int y, int width, int height) {
        super(x, y, width, height);
        vertexArray = createVertexFromRectangle(this);
    }

    public void update() {

    }

    public void render() {
        vertexArray.render();
    }
}
