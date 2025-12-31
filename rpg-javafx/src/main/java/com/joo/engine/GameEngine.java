package com.joo.game.engine;

import com.joo.game.engine.input.Input;
import com.joo.game.engine.state.GameState;
import com.joo.game.engine.state.StateManager;
import com.joo.game.engine.state.TestState;
import com.joo.game.world.tile.TileMap;
import com.joo.game.world.tile.TileMapLoader;

public final class GameEngine {

    private static final double FIXED_DT = 1.0 / 60.0;

    private final Input input;
    private final StateManager stateManager = new StateManager();
    private final TileMap tileMap;

    private double accumulator = 0.0;

    public GameEngine(Input input) {
        this.input = input;
        this.tileMap = TileMapLoader.load("/maps/demo.map");

        // Boot state
        stateManager.set(new TestState());
    }

    /** Called once per frame from GameApp */
    public double tick(double frameDelta) {
        accumulator += frameDelta;

        while (accumulator >= FIXED_DT) {
            updateFixed(FIXED_DT);
            accumulator -= FIXED_DT;
        }

        return accumulator / FIXED_DT; // alpha
    }

    private void updateFixed(double dt) {
        GameState current = stateManager.current();
        if (current != null) {
            current.handleInput(input);
            current.update(dt);
        }
        input.endTick();
    }

    // --- Getters for rendering ---
    public TileMap getTileMap() {
        return tileMap;
    }

    public GameState getCurrentState() {
        return stateManager.current();
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}