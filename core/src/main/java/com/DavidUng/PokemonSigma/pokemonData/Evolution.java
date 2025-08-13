package com.DavidUng.PokemonSigma.pokemonData;

import com.DavidUng.PokemonSigma.battleLogic.Pokemon;

public class Evolution {
    private String species;
    private String trigger;
    private Integer level;
    private String item;
    public Evolution() {

    }
    public Evolution(String species, String trigger, int level, String item) {
        this.species = species;
        this.trigger = trigger;
        this.level = level;
        this.item = item;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Pokemon evolve(){
        return new Pokemon();
    }

    public String getItem() {
        return item;
    }
}
