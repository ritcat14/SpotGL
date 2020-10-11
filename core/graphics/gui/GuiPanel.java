package SpotGL.core.graphics.gui;

import SpotGL.core.graphics.Texture;
import SpotGL.core.input.InputHandler;

import static SpotGL.core.VarStore.JAVA_HEIGHT;
import static SpotGL.core.VarStore.JAVA_WIDTH;

public class GuiPanel extends GuiComponent {

    public GuiPanel(float x, float y, float width, float height, Texture texture) {
        super(x + JAVA_WIDTH/2, y - (JAVA_HEIGHT/2), width, height, texture);
    }

    @Override
    public void update() {

    }

    @Override
    public void onInput(InputHandler inputHandler) {

    }
}
