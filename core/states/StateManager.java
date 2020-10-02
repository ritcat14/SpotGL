package SpotGL.core.states;

import SpotGL.core.input.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    
    private List<State> states;
    private List<State> statesToAdd;

    private State currentState;
    private State requestedState;

    private boolean rendering, updating, input;

    public StateManager() {
        states = new ArrayList<>();
        statesToAdd = new ArrayList<>();
    }

    public void update() {
        if (!rendering && !input) {
            if (statesToAdd.size() > 0) {
                if (currentState == null) {
                    currentState = statesToAdd.get(0);
                    currentState.init();
                }
                states.addAll(statesToAdd);
                statesToAdd.clear();
            }
            if (requestedState != null) {
                if (currentState != null && currentState.requestedChange()) currentState.requestComplete();

                currentState = requestedState;
                currentState.init();
                requestedState = null;
            }
        }
        if (currentState != null) {
            updating = true;
            currentState.update();
            if (currentState.requestedChange()) setState(currentState.getRequestedState());
            updating = false;
        }
    }

    public void addState(State state) {
        statesToAdd.add(state);
    }

    public void render() {
        if (currentState != null) {
            rendering = true;
            currentState.render();
            rendering = false;
        }
    }

    public void setState(StateName stateName) {
        if (currentState != null) {
            if (currentState.verify(stateName)) {
                // State is already set
            } else {
                State newState = getState(stateName);
                if (newState != null) {
                    if (updating || rendering || input) requestedState = newState;
                } else {
                    // State does not exist/was not found
                }
            }
        }
    }

    private State getState(StateName stateName) {
        for (State state : states) {
            if (state.verify(stateName)) return state;
        }
        // State name was not found in list of states, therefore requested state does not exist
        return null;
    }

    public void onEvent(InputHandler inputHandler) {
        if (currentState != null) {
            input = true;
            currentState.onEvent(inputHandler);
            input = false;
        }
    }

    public void cleanUp() {
        for (State state : states) {
            state.cleanUp();
        }
        states.clear();
        statesToAdd.clear();
    }
}