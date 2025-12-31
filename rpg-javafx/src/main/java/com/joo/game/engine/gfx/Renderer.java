package com.joo.game.engine.gfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Renderer {

    private final GraphicsContext gc;

    public Renderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawRect(double x, double y, double w, double h, Color color) {
        gc.setFill(color);
        gc.fillRect(x, y, w, h);
    }

    public void drawText(String text, double x, double y, Color color) {
        gc.setFill(color);
        gc.fillText(text, x, y);
    }

    // Escape hatch if needed
    public GraphicsContext raw() {
        return gc;
    }
}