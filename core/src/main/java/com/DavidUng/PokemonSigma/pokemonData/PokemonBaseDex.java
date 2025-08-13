package com.DavidUng.PokemonSigma.pokemonData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Scanner;

public class PokemonBaseDex {
    private static PokemonBaseDex instance;
    private HashMap<String, PokemonBaseDexEntry> pokemonBaseDexEntries = new HashMap<>();

    private PokemonBaseDex() {

    }
    public static PokemonBaseDex getInstance() {
        if (instance == null) {
            instance = init();
        }
        return instance;
    }

    private static PokemonBaseDex init() {
        Json json = new Json();
        //needed to deserialize
        json.setElementType(PokemonBaseDex.class, "pokemonBaseDexEntries", PokemonBaseDexEntry.class);
        json.setElementType(PokemonBaseDexEntry.class, "evolution", Evolution.class);
        // load from file
        return json.fromJson(PokemonBaseDex.class, Gdx.files.internal("data/pokemonBaseDex.json"));
    }

    public PokemonBaseDexEntry getPokemonBaseDexEntry(String species) {

        return pokemonBaseDexEntries.get(species);
    }


}
