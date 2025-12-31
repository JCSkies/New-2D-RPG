package com.joo.game.engine.state;

public final class StateManager {
    private GameState current;

    public void set(GameState next) {
        if (current != null) current.onExit();
        current = next;
        if (current != null) current.onEnter();
    }

    public GameState current() {
        return current;
    }
}
