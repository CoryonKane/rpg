package com.codecool.rpg.util;

import com.codecool.rpg.model.actor.enemy.Enemy;
import com.codecool.rpg.model.item.Item;
import com.codecool.rpg.model.map.GameMap;
import com.codecool.rpg.model.map.Tiles;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Draw {

    private GraphicsContext context;
    private Canvas canvas;
    private GameMap map;

    private final Tiles tiles;

    @Autowired
    public Draw(Tiles tiles) {
        this.tiles = tiles;
    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getMaxWidth(); x++) {
            for (int y = 0; y < map.getMap().size(); y++) {
                tiles.drawTile(context, map.getMap().get(y).get(x), x, y);
            }
        }
        for (Enemy e : map.getEnemies()) {
            tiles.drawTile(context, e, e.getCol(), e.getRow());
        }
        for (Item i : map.getItems()) {
            tiles.drawTile(context, i, i.getCol(), i.getCol());
        }
        tiles.drawTile(context, map.getPlayer(), map.getPlayer().getCol(), map.getPlayer().getRow());
    }
}