package com.DavidUng.PokemonSigma.battleLogic;

import com.badlogic.gdx.Gdx;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import java.io.BufferedReader;



public class BattleMoveModel {
    private String name;
    private String type;
    private String dmgType;
    private int basePower;
    private int accuracy;
    private int pp;
    private int currentpp;
    private static Map<Integer, String[]> map;

    private BattleMoveModel(String name, String type, String dmgType, int pp, int basePower, int accuracy) {
        this.name = name;
        this.type = type;
        this.dmgType = dmgType;
        this.basePower = basePower;
        this.accuracy = accuracy;
        this.pp = pp;
        this.currentpp = pp;
    }

    //stores all possible moves in a map rather 500+ move objects.
    public static void init() {
        map = new HashMap<>();
        FileHandle fileHandle = Gdx.files.internal("data/move.csv");
        try (BufferedReader reader = new BufferedReader(fileHandle.reader())) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                map.put(Integer.parseInt(parts[0]), Arrays.copyOfRange(parts, 1, 7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDmgType() {
        return dmgType;
    }

    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }

    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    //gets Pokemon Move
    public static BattleMoveModel getMove(int id) {
        if (map == null) {
            init();
        }
        BattleMoveModel move;
        try {
            String[] moveArr = map.get(id);
            return new BattleMoveModel(moveArr[0], moveArr[1], moveArr[2], Integer.parseInt(moveArr[3]), Integer.parseInt(moveArr[4]), Integer.parseInt(moveArr[5]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("Type: " + type + "\n");
        sb.append("DmgType: " + dmgType + "\n");
        sb.append("BasePower: " + basePower + "\n");
        sb.append("Accuracy: " + accuracy + "\n");
        sb.append("Pp: " + pp + "\n");
        return sb.toString();
    }

    public int getCurrentPp() {
        return currentpp;
    }

    public void setCurrentPp(int currentpp) {
        this.currentpp = currentpp;
    }
}
