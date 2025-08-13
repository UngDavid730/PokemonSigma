package com.DavidUng.PokemonSigma.moveData;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PokemonLearnableMoves {
    private HashMap<String, ArrayList<LearnMoveModel>> moveMap = new HashMap<>();

    public PokemonLearnableMoves() {}
    public HashMap<String, ArrayList<LearnMoveModel>> getMoveMap() {
        return moveMap;
    }

    public ArrayList<LearnMoveModel> getLearnedMovesByLevel(int id) {
        return moveMap.get(Integer.toString(id));
    }


}
