package com.joo.game.engine.input;

import com.joo.game.input.Action;

public interface Input {

    boolean isDown(Action action);       // held
    boolean isPressed(Action action);    // went down THIS tick
    boolean isReleased(Action action);   // went up THIS tick

    void endTick(); // clears pressed / released
}