package com.joo.game.world.tile;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class TileMapLoader {

    private TileMapLoader() {} // utility class

    public static TileMap load(String path) {

        if (path == null) {
            throw new IllegalArgumentException("Tile map path cannot be null");
        }

        InputStream stream = TileMapLoader.class.getResourceAsStream(path);
        if (stream == null) {
            throw new IllegalArgumentException("Tile map resource not found: " + path);
        }

        List<int[]> rows = new ArrayList<>();
        int expectedCols = -1;
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;

                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] tokens = line.split("\\s+");

                if (expectedCols == -1) {
                    expectedCols = tokens.length;
                } else if (tokens.length != expectedCols) {
                    throw new IllegalArgumentException(
                            "Non-rectangular tile map at line " + lineNumber +
                                    ": expected " + expectedCols + " columns, found " + tokens.length
                    );
                }

                int[] row = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    try {
                        row[i] = Integer.parseInt(tokens[i]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "Invalid tile id '" + tokens[i] +
                                        "' at line " + lineNumber, e
                        );
                    }
                }

                rows.add(row);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load tile map: " + path, e);
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Tile map is empty: " + path);
        }

        int[][] map = rows.toArray(new int[0][]);
        return new TileMap(map);
    }
}
