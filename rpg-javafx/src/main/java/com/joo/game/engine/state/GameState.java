package com.joo.game.engine.state;

import com.joo.game.engine.gfx.Camera;
import com.joo.game.engine.input.Input;


import com.joo.game.engine.gfx.Renderer;

public interface GameState {

    void onEnter();
    void onExit();

    void update(double dt);
    void render(Renderer r, Camera camera, double alpha);

    void handleInput(Input input);
}
