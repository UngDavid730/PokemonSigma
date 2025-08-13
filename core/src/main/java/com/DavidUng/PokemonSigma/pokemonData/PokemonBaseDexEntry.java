package com.DavidUng.PokemonSigma.pokemonData;

import com.badlogic.gdx.utils.Json;

import java.util.Arrays;

public class PokemonBaseDexEntry {

    private String[] abilityList;
    private String[] type;
    private String species;
    private int hp;
    private int attack;
    private int defense;
    private int sAttack;
    private int sDefense;
    private int speed;
    private Evolution[] evolution;

    public PokemonBaseDexEntry() {
            // required for Json serialization
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PokemonBaseDexEntry{\n");
        stringBuilder.append("\tabilityList=").append(Arrays.toString(abilityList)).append("\n");
        stringBuilder.append("\ttype=").append(Arrays.toString(type)).append("\n");
        stringBuilder.append("\tspecies=").append(species).append("\n");
        stringBuilder.append("\thp=").append(hp).append("\n");
        stringBuilder.append("\tattack=").append(attack).append("\n");
        stringBuilder.append("\tdefense=").append(defense).append("\n");
        stringBuilder.append("\tsattack=").append(sAttack).append("\n");
        stringBuilder.append("\tsdefense=").append(sDefense).append("\n");
        stringBuilder.append("\tspeed=").append(speed).append("\n");
        stringBuilder.append("\tevolution=").append(Arrays.toString(evolution)).append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
    public PokemonBaseDexEntry(String[] ab, String[] type, String species, int hp, int attack, int defense, int sAttack, int sDefense, int speed, Evolution[] evolution) {
        this.abilityList = ab;
        this.type = type;
        this.species = species;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.sAttack = sAttack;
        this.sDefense = sDefense;
        this.speed = speed;
        this.evolution = evolution;
    }

    public String[] getAbilityList() {
        return abilityList;
    }

    public String[] getType() {
        return type;
    }

    public String getSpecies() {
        return species;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getsAttack() {
        return sAttack;
    }

    public int getsDefense() {
        return sDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public Evolution[] getEvolution() {
        return evolution;
    }

    public void setEvolution(Evolution[] evolution) {
        this.evolution = evolution;
    }
}
