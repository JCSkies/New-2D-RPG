package com.joo.game.world.tile;

public class TileMap {
    public static final int TILE_SIZE = 32;


    private final int[][] map;

    public TileMap( int[][] map) {
        this.map = map;
    }

    public int getTile(int row, int col) {
        return map[row][col];
    }

    public int getRows() {
        return map.length;
    }
    public int getCols() {
        return map[0].length;
    }


}
