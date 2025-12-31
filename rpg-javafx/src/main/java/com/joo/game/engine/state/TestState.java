package com.joo.game.engine.state;

import com.joo.game.engine.gfx.Camera;
import com.joo.game.engine.gfx.Renderer;
import com.joo.game.engine.input.Input;
import com.joo.game.input.Action;
import javafx.scene.paint.Color;

public final class TestState implements GameState {

    // Logical position
    private double x = 100;
    private double y = 100;

    // Previous logical position (for interpolation)
    private double prevX = x;
    private double prevY = y;

    private static final double SPEED = 200; // pixels per second
    private static final double SIZE  = 40;

    // Cached input reference
    private Input input;

    @Override
    public void onEnter() {
        System.out.println("Entered TestState");
    }

    @Override
    public void onExit() {
        System.out.println("Exited TestState");
    }

    @Override
    public void handleInput(Input input) {
        // Cache input for use during update()
        this.input = input;
    }

    @Override
    public void update(double dt) {
        // Save previous position BEFORE updating
        prevX = x;
        prevY = y;

        double dx = 0;
        double dy = 0;

        if (input != null) {
            if (input.isDown(Action.LEFT))  dx -= 1;
            if (input.isDown(Action.RIGHT)) dx += 1;
            if (input.isDown(Action.UP))    dy -= 1;
            if (input.isDown(Action.DOWN))  dy += 1;

            if (input.isPressed(Action.CONFIRM)) {
                System.out.println("CONFIRM pressed!");
            }
        }


        // Normalize diagonal movement
        double length = Math.hypot(dx, dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        x += dx * SPEED * dt;
        y += dy * SPEED * dt;   
    }

    @Override
    public void render(Renderer r, Camera camera, double alpha) {
        double renderX = prevX + (x - prevX) * alpha;
        double renderY = prevY + (y - prevY) * alpha;

        double screenX = renderX - camera.getX();
        double screenY = renderY - camera.getY();

        r.raw().setFill(Color.CORNFLOWERBLUE);
        r.raw().fillRect(screenX, screenY, SIZE, SIZE);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRenderX(double alpha) {
        return prevX + (x - prevX) * alpha;
    }

    public double getRenderY(double alpha) {
        return prevY + (y - prevY) * alpha;
    }

}
