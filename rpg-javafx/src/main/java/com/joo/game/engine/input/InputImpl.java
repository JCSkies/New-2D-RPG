package com.joo.game.engine.input;

import com.joo.game.input.Action;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public final class InputImpl implements Input {

    // Current key state    
    private final Set<Action> down = EnumSet.noneOf(Action.class);

    // Edge states (cleared every tick)
    private final Set<Action> pressed = EnumSet.noneOf(Action.class);
    private final Set<Action> released = EnumSet.noneOf(Action.class);

    // Key bindings
    private final Map<KeyCode, Action> bindings = new EnumMap<>(KeyCode.class);

    public InputImpl() {
        // Default bindings
        bindings.put(KeyCode.W, Action.UP);
        bindings.put(KeyCode.UP, Action.UP);

        bindings.put(KeyCode.S, Action.DOWN);
        bindings.put(KeyCode.DOWN, Action.DOWN);

        bindings.put(KeyCode.A, Action.LEFT);
        bindings.put(KeyCode.LEFT, Action.LEFT);

        bindings.put(KeyCode.D, Action.RIGHT);
        bindings.put(KeyCode.RIGHT, Action.RIGHT);

        bindings.put(KeyCode.ENTER, Action.CONFIRM);
        bindings.put(KeyCode.ESCAPE, Action.MENU);
        bindings.put(KeyCode.BACK_SPACE, Action.CANCEL);
    }

    // --- JavaFX hooks ---
    public void onKeyPressed(KeyEvent e) {
        Action action = bindings.get(e.getCode());
        if (action == null) return;

        if (!down.contains(action)) {
            down.add(action);
            pressed.add(action);
        }
    }

    public void onKeyReleased(KeyEvent e) {
        Action action = bindings.get(e.getCode());
        if (action == null) return;

        down.remove(action);
        released.add(action);
    }

    // --- Input interface ---
    @Override
    public boolean isDown(Action action) {
        return down.contains(action);
    }

    @Override
    public boolean isPressed(Action action) {
        return pressed.contains(action);
    }

    @Override
    public boolean isReleased(Action action) {
        return released.contains(action);
    }

    @Override
    public void endTick() {
        pressed.clear();
        released.clear();
    }
}
