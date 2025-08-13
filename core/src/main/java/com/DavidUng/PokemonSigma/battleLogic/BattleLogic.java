package com.DavidUng.PokemonSigma.battleLogic;

import com.DavidUng.PokemonSigma.PlayerData.Player;
import com.DavidUng.PokemonSigma.UI.StatusBox;
import com.DavidUng.PokemonSigma.screens.BattleScene;

public class BattleLogic {
    private static Pokemon currentPokemon;
    private static Pokemon enemyPokemon;
    private Action playerAction = null;
    private BattleMoveModel playerPokemonMove;
    private BattleScene battleScene;

    public void setPokemonMove(BattleMoveModel moveModel) {
        playerPokemonMove = moveModel;
    }

    public void setPlayerAction(Action action) {
        this.playerAction=action;
    }

    public enum Action{
        SWITCH_IN,
        ITEM,
        MOVE,
        RUN
    }
    public BattleLogic(Pokemon[] currentPokemons, BattleScene battleScene) {
        currentPokemon = currentPokemons[0];
        enemyPokemon = currentPokemons[1];
        this.battleScene = battleScene;
    }

    public static Pokemon getCurrentPokemon() {
        return currentPokemon;
    }
    public static Pokemon getEnemyPokemon(){
        return enemyPokemon;
    }
    public void battlePhase(){

        if(playerAction != null){

            switch(playerAction){
                case SWITCH_IN:
                    return;
                case ITEM:
                    break;
                case MOVE:
                    battleCalc();
                    break;
                case RUN:
//                    runAway();
                    break;


            }
        }
    }

    private void battleCalc() {
        if (playerPokemonMove.getDmgType().equals("Status")) {
            playerAction = null;
            damageCalc(enemyPokemon,currentPokemon,enemyPokemon.getMoveset()[0]);
            return;
        }
        if (currentPokemon.getSpeed() > enemyPokemon.getSpeed()) {
          damageCalc(currentPokemon,enemyPokemon,playerPokemonMove);
          if(pokemonFainted(enemyPokemon)){
              playerAction = null;
              return;
          }
          damageCalc(enemyPokemon,currentPokemon,enemyPokemon.getMoveset()[0]);
        }
        else{
            damageCalc(enemyPokemon,currentPokemon,enemyPokemon.getMoveset()[0]);
            if(pokemonFainted(currentPokemon)){
                playerAction = null;
                return;
            }
            damageCalc(currentPokemon,enemyPokemon,playerPokemonMove);
        }
        playerAction = null;
    }

    private boolean pokemonFainted(Pokemon pokemon) {
        if(pokemon.getStatus() == Pokemon.PokemonStatus.FAINTED){
            return true;
        }
        return false;
    }

    private void damageCalc(Pokemon self, Pokemon target, BattleMoveModel move) {
        int damage = 0;
        int resist = 0;
        damage = (((2 * self.getLevel()) / 5) + 2) * move.getBasePower() / 50;
        if (move.getDmgType().equals("Physical")) {
            resist = self.getAttack() / target.getDefense();
        } else if (move.getDmgType().equals("Special")) {
            resist = self.getsAttack() / target.getsDefense();
        }
        damageStep(target,damage * resist +1);
        target.getMaxHp();
    }
    private int damageStep(Pokemon pokemon, int dmg){
        pokemon.takeDamage(dmg);
        return dmg;
    }






}
