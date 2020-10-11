package SpotGL.game.states;

import SpotGL.core.graphics.GLFrame;
import SpotGL.core.graphics.gui.GuiButton;
import SpotGL.core.graphics.gui.GuiPanel;
import SpotGL.core.input.InputHandler;
import SpotGL.core.objects.Camera;
import SpotGL.core.states.State;
import SpotGL.core.states.StateName;
import SpotGL.game.managers.GuiManager;

import static SpotGL.core.VarStore.JAVA_HEIGHT;
import static SpotGL.core.VarStore.JAVA_WIDTH;
import static SpotGL.core.states.StateName.*;
import static SpotGL.core.utils.FileUtils.loadTexture;

public class Start extends State {

    private GuiManager guiManager;

    public Start() {
        super(START);
    }

    @Override
    public void init(GLFrame glFrame) {
        Camera camera = new Camera();
        guiManager = new GuiManager(glFrame, camera);
        guiManager.addComponent(new GuiPanel(0, 0, 1920, 1080,
                loadTexture("/images/gui/startBackground.png")));
        guiManager.addComponent(new GuiButton(JAVA_WIDTH/2, JAVA_HEIGHT/2, 288, 70,"start") {
            @Override
            public void onAction() {
                requestChange(GAME);
            }
        });
        guiManager.addComponent(new GuiButton(JAVA_WIDTH/2, (JAVA_HEIGHT/2) + 100, 288, 70,"settings") {
            @Override
            public void onAction() {
                requestChange(SETTINGS);
            }
        });
        guiManager.addComponent(new GuiButton(JAVA_WIDTH/2, (JAVA_HEIGHT/2)+200, 288, 70,"exit") {
            @Override
            public void onAction() {
                System.exit(0);
            }
        });
    }

    @Override
    public void update() {
        guiManager.update();
    }

    @Override
    public void render() {
        guiManager.render();
    }

    @Override
    public void cleanUp() {
        guiManager.cleanUp();
    }

    @Override
    public void onInput(InputHandler inputHandler) {
        guiManager.onInput(inputHandler);
    }
}
