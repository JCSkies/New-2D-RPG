package com.joo.game.engine;

import com.joo.game.engine.gfx.Camera;
import com.joo.game.engine.gfx.Renderer;
import com.joo.game.engine.gfx.TileRenderer;
import com.joo.game.engine.input.InputImpl;
import com.joo.game.engine.state.TestState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public final class GameApp extends Application {

    public static final int WIDTH  = 960;
    public static final int HEIGHT = 540;

    @Override
    public void start(Stage stage) {

        // --- Canvas ---
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // --- Input ---
        InputImpl input = new InputImpl();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(input::onKeyPressed);
        scene.setOnKeyReleased(input::onKeyReleased);

        // --- Engine ---
        GameEngine engine = new GameEngine(input);

        // --- Rendering ---
        Renderer renderer = new Renderer(gc);

        // --- Camera ---
        Camera camera = new Camera();




        Image tileset = new Image(
                getClass().getResourceAsStream("/tiles/tileset.png")
        );
        TileRenderer tileRenderer = new TileRenderer(tileset);

        // --- Window ---
        stage.setTitle("JRPG Engine");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        canvas.requestFocus();

        // --- Main Game Loop ---
        new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double frameDelta = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                double alpha = engine.tick(frameDelta);

// --- CAMERA LOCK ---
                if (engine.getCurrentState() instanceof TestState state) {
                    double targetX = state.getRenderX(alpha) - WIDTH / 2.0;
                    double targetY = state.getRenderY(alpha) - HEIGHT / 2.0;

                    camera.follow(targetX, targetY, 0.2);
                }
                // ----- RENDER PIPELINE -----
                renderer.clear();

                // World
                tileRenderer.render(gc, engine.getTileMap(), camera);

                // State
                if (engine.getCurrentState() != null) {
                    engine.getCurrentState().render(renderer, camera, alpha);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
