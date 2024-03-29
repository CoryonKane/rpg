package com.codecool.rpg.model.actor;

import com.codecool.rpg.model.event.Event;
import com.codecool.rpg.model.map.Direction;
import com.codecool.rpg.model.map.Drawable;
import com.codecool.rpg.model.map.cell.Cell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Actor implements Drawable, Serializable {
    private int maxHealth;
    private int currHealth;
    private int damage;
    private int armor;
    private int row;
    private int col;
    private Direction facing;
    private String name;
    private Cell cell;
    private Map<Direction, String> tileNames;

    @Override
    public String getTileName() {
        return this.tileNames.get(this.facing);
    }

    public abstract void move(Direction direction);

    public abstract Event die();

}
