package com.codecool.rpg.util;

import com.codecool.rpg.model.actor.enemy.Enemy;
import com.codecool.rpg.model.item.Inventory;
import com.codecool.rpg.model.map.GameMap;
import com.codecool.rpg.model.map.Tiles;
import com.codecool.rpg.util.state.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class Draw {

    private GraphicsContext context;
    private Canvas canvas;
    private GameState state;
    private boolean isRefreshing = false;

    private final Tiles tiles;

    private static Draw instance;

    public static Draw getInstance() {
        if (instance == null) {
            instance = new Draw();
        }
        return instance;
    }

    private Draw() {
        this.tiles = new Tiles();
    }

    public void refresh() {
        if (isRefreshing) {
            return;
        }
        state = GameState.getInstance();
        GameMap map = state.getActiveMap();
        isRefreshing = true;
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getMaxWidth(); x++) {
            for (int y = 0; y < map.getMap().size(); y++) {
                tiles.drawTile(context, map.getMap().get(y).get(x), x, y);
            }
        }
        for (Enemy e : map.getEnemies()) {
            tiles.drawTile(context, e, e.getRow(), e.getCol());
        }
        for (Inventory i : map.getInventories()) {
            tiles.drawTile(context, i, i.getRow(), i.getCol());
        }
        tiles.drawTile(context, state.getPlayer(), state.getPlayer().getRow(), state.getPlayer().getCol());
        isRefreshing = false;
    }
}
