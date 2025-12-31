package com.joo.game.engine.gfx;

public final class Camera {

    private double x;
    private double y;

    public Camera() {
        this(0, 0);
    }

    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // ✨ NEW: smooth follow
    public void follow(double targetX, double targetY, double smoothness) {
        x += (targetX - x) * smoothness;
        y += (targetY - y) * smoothness;
    }
}