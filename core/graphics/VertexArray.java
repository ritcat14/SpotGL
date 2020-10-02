package SpotGL.core.graphics;

import SpotGL.core.math.BufferUtils;

import static SpotGL.core.graphics.ShaderUtils.TCOORD_ATTRIB;
import static SpotGL.core.graphics.ShaderUtils.VERTEX_ATTRIB;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    private int vao, vbo, ibo, tbo;
    private int count;

    public VertexArray(float vertices[], int[] indices, float[] textureCoords) {
        this.count = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(VERTEX_ATTRIB);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoords), GL_STATIC_DRAW);
        glVertexAttribPointer(TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(TCOORD_ATTRIB);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    public void bind() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(VERTEX_ATTRIB);
        glEnableVertexAttribArray(TCOORD_ATTRIB);
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(VERTEX_ATTRIB);
        glDisableVertexAttribArray(TCOORD_ATTRIB);
        glBindVertexArray(0);
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
    }

    public void render(Shader shader, Texture texture) {
        texture.bind(shader);
        bind();
        draw();
        texture.unbind();
        unbind();
    }

    public void cleanUp() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(tbo);
        glDeleteBuffers(ibo);
    }

}
