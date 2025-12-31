package com.joo.game.engine.gfx;

import com.joo.game.world.tile.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileRenderer {
    private final Image tileSet;
    private final int tilesPerRow;


    public TileRenderer (Image tileSet) {
        this.tileSet = tileSet;
        this.tilesPerRow = (int)(tileSet.getWidth() / TileMap.TILE_SIZE);
    }

    public void render(GraphicsContext gc, TileMap map, Camera camera) {
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {

                int tileId = map.getTile(row, col);
                if (tileId < 0) continue;

                int srcX = (tileId % tilesPerRow) * TileMap.TILE_SIZE;
                int srcY = (tileId / tilesPerRow) * TileMap.TILE_SIZE;

                double worldX = col * TileMap.TILE_SIZE;
                double worldY = row * TileMap.TILE_SIZE;

                double screenX = worldX - camera.getX();
                double screenY = worldY - camera.getY();

                gc.drawImage(
                        tileSet,
                        srcX, srcY,
                        TileMap.TILE_SIZE, TileMap.TILE_SIZE,
                        screenX, screenY,
                        TileMap.TILE_SIZE, TileMap.TILE_SIZE
                );
            }
        }
    }
}
