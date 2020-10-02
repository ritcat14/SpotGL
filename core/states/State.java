package SpotGL.core.states;

import SpotGL.core.graphics.ShaderUtils;
import SpotGL.core.input.InputHandler;

public abstract class State {

    private final StateName stateName;

    private StateName requestedState;
    private boolean requestedChange = false;

    protected ShaderUtils shaderUtils;

    public State(ShaderUtils shaderUtils, StateName stateName) {
        this.shaderUtils = shaderUtils;
        this.stateName = stateName;
    }

    public boolean verify(StateName stateName) {
        return this.stateName == stateName;
    }

    public abstract void init();

    public abstract void update();

    public abstract void render();

    public abstract void cleanUp();

    public abstract void onEvent(InputHandler inputHandler);

    public void requestChange(StateName state) {
        requestedState = state;
        requestedChange = true;
    }

    public boolean requestedChange() {
        return requestedChange;
    }

    public StateName getRequestedState() {
        return requestedState;
    }

    public void requestComplete() {
        requestedState = null;
        requestedChange = false;
    }

    public StateName getStateName() {
        return stateName;
    }

}
