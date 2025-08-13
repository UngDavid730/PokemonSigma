package com.DavidUng.PokemonSigma.battleLogic;

import com.DavidUng.PokemonSigma.moveData.LearnMoveModel;
import com.DavidUng.PokemonSigma.moveData.PokemonLearnMoveDex;
import com.DavidUng.PokemonSigma.moveData.PokemonLearnableMoves;
import com.DavidUng.PokemonSigma.pokemonData.PokemonBaseDex;
import com.DavidUng.PokemonSigma.pokemonData.PokemonBaseDexEntry;

import javax.xml.transform.Source;
import java.sql.SQLOutput;
import java.util.*;

import static java.lang.Math.floor;


public class Pokemon {
    private static PokemonBaseDex pokemonBaseDex;
    private static PokemonLearnMoveDex pokemonLearnMoveDex;
    private String ability;
    private String[] type;
    private String name;
    private String species;
    private int level;
    private int hp;
    private int attack;
    private int defense;
    private int sAttack;
    private int sDefense;
    private int speed;
    private PokemonStatus status;
    private float currentHp;
    private float maxHp;



    //move list is for possible moves
    private BattleMoveModel[] moveset;
    private Integer exp;
    private Integer item;

    public PokemonStatus getStatus() {
        return this.status;
    }

    public float getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    public enum PokemonStatus{
        FAINTED,
        BURN,
        POISON,
        FROZEN,
        PARALYZED,
        SLEEP,
    }


//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Ability: " + ability + "\n");
//        sb.append("Name: " + name + "\n");
//        sb.append("Species: " + species + "\n");
//        sb.append("Level: " + level + "\n");
//        sb.append("HP: " + hp + "\n");
//        sb.append("Attack: " + attack + "\n");
//        sb.append("Defense: " + defense + "\n");
//        sb.append("Special Attack: " + sAttack + "\n");
//        sb.append("Special Defense: " + defense + "\n");
//        sb.append("Speed: " + speed + "\n");
//        sb.append("Status: " + status + "\n");
//        sb.append("Exp: " + exp + "\n");
//        sb.append("Item: " + item + "\n");
//        sb.append("Moveset: " + "\n");
//        for (BattleMoveModel move : moveset) {
//            sb.append(move.toString() + "\n");
//        }
//        return sb.toString();
//    }

    public Pokemon() {
    }

    // init pokemon
    public Pokemon(String ability, String[] type, String species, int level,int hp, int attack, int defense, int sAttack, int sDefense, int speed, BattleMoveModel[] moveset) {
        this.ability = ability;
        this.type = type;
        this.species = species;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.sAttack = sAttack;
        this.sDefense = sDefense;
        this.speed = speed;
        this.moveset = moveset;
        this.name = species;
        this.level = level;
        this.maxHp = (((2*hp)*level)/100)+level+10;
        this.currentHp = maxHp;

    }

    public static Pokemon encounter(String species, int level) {
        pokemonBaseDex = PokemonBaseDex.getInstance();
        pokemonLearnMoveDex = PokemonLearnMoveDex.getInstance();
        PokemonBaseDexEntry baseDexEntry = pokemonBaseDex.getPokemonBaseDexEntry(species);
//        System.out.println("baseDexEntry = " + baseDexEntry);
        //gen ability
        String[] abilityList = baseDexEntry.getAbilityList();
        String ability;
        Random random = new Random();
        if (abilityList.length < 1) {
            ability = abilityList[random.nextInt(abilityList.length) + 1];
        } else {
            ability = abilityList[0];
        }
        //calc stats
        int[] base = new int[6];
        base[0] = (int) floor(((2 * baseDexEntry.getHp()) * level) / 100 + level + 10);
        base[1] = (int) floor(((2 * baseDexEntry.getAttack()) * level) / 100 + 5);
        base[2] = (int) floor(((2 * baseDexEntry.getDefense()) * level) / 100 + 5);
        base[3] = (int) floor(((2 * baseDexEntry.getsAttack()) * level) / 100 + 5);
        base[4] = (int) floor(((2 * baseDexEntry.getsDefense()) * level) / 100 + 5);
        base[5] = (int) floor(((2 * baseDexEntry.getSpeed()) * level) / 100 + 5);
        //gen moveset
        BattleMoveModel[] moves = generateMoveset(species, level);
        System.out.println("Moveset: " + Arrays.toString(moves));
        return new Pokemon(ability, baseDexEntry.getType(), species,level, base[0], base[1], base[2], base[3], base[4], base[5], moves);

    }

    private static BattleMoveModel[] generateMoveset(String species, int level) {
        int counter = 0;
        BattleMoveModel[] moveset = new BattleMoveModel[4];
        PokemonLearnableMoves allLearnable = pokemonLearnMoveDex.getAllLearnable(species);
        HashMap<String, ArrayList<LearnMoveModel>> moveMap = allLearnable.getMoveMap();
        //reverses the order of keys
        ArrayList<Integer> keys = new ArrayList<>();
        for (String key : moveMap.keySet()) {
            keys.add(Integer.parseInt(key));
        }
        Collections.sort(keys);
        Collections.reverse(keys);
        for (Integer key : keys) {
            System.out.println(counter);
            if (key == 0) {
                continue;
            }
            if (key < level) {
                System.out.println("move set< level");
                ArrayList<LearnMoveModel> moves = moveMap.get(Integer.toString(key));
                for (LearnMoveModel learnMoveModel : moves) {
                    moveset[counter] = BattleMoveModel.getMove(learnMoveModel.getId());
                    System.out.println(learnMoveModel);
                    counter++;
                    System.out.println(counter);
                    if (counter > 3) {
                        return moveset;
                    }
                }
            }


        }
        return moveset;
    }

    public BattleMoveModel[] getMoveset() {
        return moveset;
    }

    public void setMoveset(BattleMoveModel move, int index) {
        moveset[index] = move;
    }


    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getsAttack() {
        return sAttack;
    }

    public int getsDefense() {
        return sDefense;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public void takeDamage(int dmg) {
        this.currentHp -= dmg;
        if (currentHp <= 0) {
            this.status = PokemonStatus.FAINTED;
        }
    }
    public float getMaxHp(){
        return maxHp;
    }
    public void restore(){
        this.currentHp = maxHp;
        this.status = null;
    }
}
