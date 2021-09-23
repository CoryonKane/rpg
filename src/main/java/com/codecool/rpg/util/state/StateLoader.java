package com.codecool.rpg.util.state;

import com.codecool.rpg.model.actor.PlayerCharacter;
import com.codecool.rpg.model.actor.enemy.Enemy;
import com.codecool.rpg.model.actor.npc.NonPlayerCharacter;
import com.codecool.rpg.model.item.Item;
import com.codecool.rpg.model.map.GameMap;
import com.codecool.rpg.model.map.cell.Cell;
import com.codecool.rpg.model.map.cell.CellType;
import com.codecool.rpg.model.map.cell.Gate;

import java.io.*;
import java.util.*;

public class StateLoader {
    private final GameState state = GameState.getInstance();
    private GameMap map;
    private final String resources = "src/main/resources";
    private static StateLoader instance;

    public static StateLoader getInstance() {
        if (instance == null) {
            instance = new StateLoader();
        }
        return instance;
    }

    private StateLoader() {}

    public GameMap loadMap(String mapName) {
        if (state.getMapCache().containsKey(mapName)) {
            return state.getMapCache().get(mapName);
        }

        InputStream is = StateLoader.class.getResourceAsStream(mapName);

        Scanner scanner = new Scanner(is);

        map = GameMap.builder()
                .map(new ArrayList<>())
                .items(new ArrayList<>())
                .enemies(new ArrayList<>())
                .name(mapName)
                .player(state.getPlayer())
                .build();

        int rowCounter = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Cell> row = new ArrayList<>();
            int colCounter = 0;
            for (String mapCharacter : line.split("")) {
                Cell cell = Cell.builder()
                        .cellType(Arrays.stream(CellType.values()).filter(v -> mapCharacter.equals(v.getTileCharacter())).findFirst().orElse(null))
                        .gameMap(map)
                        .row(rowCounter)
                        .col(colCounter)
                        .build();
                row.add(cell);
                colCounter++;
            }
            map.addRow(row);
            if (row.size() > map.getMaxWidth()) {
                map.setMaxWidth(row.size());
            }
            rowCounter++;
        }

        state.getMapCache().put(mapName, map);
        String[] s = mapName.split("\\.");
        loadEnemies(resources + s[0] + "_enemies.ser");
        loadItems(resources + s[0] + "_items.ser");
        loadGates(resources + s[0] + "_gates.ser");
        loadNPCs(resources + s[0] + "_npc.ser");

        return map;
    }

    private void loadEnemies(String mapName) {
        List<Enemy> list;
        try {
            FileInputStream file = new FileInputStream(mapName);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (List<Enemy>) in.readObject();

            in.close();
            file.close();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("No enemy file found.");
            ex.printStackTrace();
            return;
        }
        list.forEach(map::addEnemy);
    }

    private void loadItems(String mapName) {
        List<Item> list;
        try {
            FileInputStream file = new FileInputStream(mapName);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (List<Item>) in.readObject();

            in.close();
            file.close();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("No item file found.");
            ex.printStackTrace();
            return;
        }
        list.forEach(map::addItem);
    }

    private void loadGates(String mapName) {
        List<Gate> list;
        try {
            FileInputStream file = new FileInputStream(mapName);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (List<Gate>) in.readObject();

            in.close();
            file.close();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("No gate file found.");
            ex.printStackTrace();
            return;
        }
        list.forEach(map::setGate);
    }

    private void loadNPCs(String mapName) {
        List<NonPlayerCharacter> list;
        try {
            FileInputStream file = new FileInputStream(mapName);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (List<NonPlayerCharacter>) in.readObject();

            in.close();
            file.close();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("No npc file found.");
            ex.printStackTrace();
            return;
        }
        list.forEach(map::addNPC);
    }
}