package com.DavidUng.PokemonSigma.moveData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;

public class PokemonLearnMoveDex {
    private static PokemonLearnMoveDex instance;
    private HashMap<String, PokemonLearnableMoves> pokemonLearnMoveDexMap = new HashMap<>();
    private PokemonLearnMoveDex() {
    }
    public static PokemonLearnMoveDex getInstance() {
        if (instance == null) {
            instance = init();
        }
        return instance;
    }

    private static PokemonLearnMoveDex init() {
        Json json = new Json();
        json.setElementType(PokemonLearnMoveDex.class, "pokemonLearnMoveDexMap", PokemonLearnableMoves.class);
        json.setElementType(PokemonLearnableMoves.class, "moveMap", ArrayList.class);
        return json.fromJson(PokemonLearnMoveDex.class, Gdx.files.internal("data/pokemonLearnMoveDex.json"));
    }

    public HashMap<String, PokemonLearnableMoves> getPokemonLearnMoveDexMap() {
        return pokemonLearnMoveDexMap;
    }
    public PokemonLearnableMoves getAllLearnable(String pokemonSpecies) {
        return instance.pokemonLearnMoveDexMap.get(pokemonSpecies);
    }
    public ArrayList<LearnMoveModel> getLearnableAtLevel(String pokemonSpecies, int level) {
        instance.pokemonLearnMoveDexMap.get(pokemonSpecies);
        return instance.pokemonLearnMoveDexMap.get(pokemonSpecies).getLearnedMovesByLevel(level);
    }

}
