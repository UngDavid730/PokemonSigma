package com.DavidUng.PokemonSigma;

import com.DavidUng.PokemonSigma.PlayerData.Player;
import com.DavidUng.PokemonSigma.battleLogic.Pokemon;
import com.DavidUng.PokemonSigma.moveData.PokemonLearnMoveDex;
import com.DavidUng.PokemonSigma.pokemonData.PokemonBaseDex;
import com.DavidUng.PokemonSigma.screens.BattleScene;
import com.DavidUng.PokemonSigma.screens.RouteScene;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PokemonSigma extends Game{
    private Player player;
    private PokemonLearnMoveDex pokemonLearnMoveDex;
    private PokemonBaseDex pokemonBaseDex;
    private static PokemonSigma game;
    private RouteScene route1;
    private static final int TILESIZE = 32;
    private static float scale =1f;
    private static float scaledtilesize = TILESIZE * scale;
    public static int getTileSize() {
        return TILESIZE;
    }

    public static float getScaleTileSize(){
        return scaledtilesize;
    }

    @Override
    public void create() {
        initFiles();
        setScreen(new RouteScene(this,"route1"));
    }

    public void render() {
        ScreenUtils.clear(0,0,0,1);
        super.render(); // important!
    }
    public void dispose() {
        super.dispose();
    }
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    public void initFiles(){
        player = Player.getInstance();
        pokemonLearnMoveDex = PokemonLearnMoveDex.getInstance();
        pokemonBaseDex = PokemonBaseDex.getInstance();

    }

}


