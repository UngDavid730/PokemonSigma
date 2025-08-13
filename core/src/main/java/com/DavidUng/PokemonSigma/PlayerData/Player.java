package com.DavidUng.PokemonSigma.PlayerData;

import com.DavidUng.PokemonSigma.battleLogic.Pokemon;

public class Player {
    private static Player player;
    private Pokemon[] party;
    private Integer money = 0;

    private Player(){
        party = new Pokemon[6];
        party[0] = Pokemon.encounter("Blaziken",36);
    }
    public static Player getInstance(){
        if(player == null){
            player = new Player();
        }
        else {
            return player;
        }
        return player;
    }
    public Pokemon[] getParty(){
        return party;
    }
    public Pokemon getPokemon(int index){
        return party[index];
    }
    public void setParty(Pokemon[] party){}
    public void addParty(Pokemon pokemon){
        for(int i = 0; i < party.length; i++){
            if(party[i] == null){
                party[i] = pokemon;
                return;
            }
        }
        System.out.println("Party Full");
    }
    public Integer getMoney(){
        return money;
    }
    public void setMoney(int money){}

}
