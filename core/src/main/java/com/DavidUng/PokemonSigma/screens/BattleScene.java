package com.DavidUng.PokemonSigma.screens;

import com.DavidUng.PokemonSigma.PlayerData.Player;
import com.DavidUng.PokemonSigma.PokemonSigma;
import com.DavidUng.PokemonSigma.UI.BattleUI;
import com.DavidUng.PokemonSigma.UI.DetailedStatusBox;
import com.DavidUng.PokemonSigma.UI.StatusBox;
import com.DavidUng.PokemonSigma.battleLogic.BattleLogic;
import com.DavidUng.PokemonSigma.battleLogic.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BattleScene extends ScreenAdapter implements Screen {
    private final Pokemon[] enemyParty;
    private Music music;
    //game logics
    private Player player = Player.getInstance();
    private Screen returnScreen;
    private PokemonSigma game;
    private static BattleLogic battleLogic;
    private Pokemon currentEnemyMon;
    private Pokemon currentYourMon;
    //visuals
    private Stage stage;
    private Texture background;
    private TextureAtlas spriteAtlas;
    private Image playerPokemon;
    private Image enemyPokemon;
    private Skin skin;
    private DetailedStatusBox playerStatus;
    private StatusBox opponentStatus;

    private static Boolean battleOver;
    private boolean encounter;

    public Pokemon getCurrentYourMon(){
        return currentYourMon;
    }


    public BattleScene(Pokemon[] pokemonParty, Boolean encounter, Screen returnScreen, PokemonSigma game) {
        currentEnemyMon = pokemonParty[0];
        this.enemyParty = pokemonParty;
        this.returnScreen = returnScreen;
        this.game = game;
        currentYourMon = player.getPokemon(0);
        this.encounter = encounter;
        battleOver = false;
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/encounter.wav"));

        // Set to loop so it keeps playing
        music.setLooping(true);

        // Optional: Set volume (0.0 - 1.0)
        music.setVolume(0.8f);

        // Play the music
        music.play();
    }

    @Override
    public void show() {
        spriteAtlas = new TextureAtlas(Gdx.files.internal("battleSprites/battleSprites.atlas"));
        stage = new Stage(new FitViewport(640, 360));
        Gdx.input.setInputProcessor(stage);
        background = new Texture(Gdx.files.internal("battleGuiAssets/battleField.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        String currentEnemyMonSpecies = currentEnemyMon.getSpecies() + "Front";
        String currentYourMonSpecies = currentYourMon.getSpecies() + "Back";
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        TextureRegion playerRegion = spriteAtlas.findRegion(currentYourMonSpecies);
        TextureRegion enemyRegion = spriteAtlas.findRegion(currentEnemyMonSpecies);
        playerPokemon = new Image(new TextureRegionDrawable(playerRegion));
        playerPokemon.scaleBy(1f);
        enemyPokemon = new Image(new TextureRegionDrawable(enemyRegion));
        enemyPokemon.scaleBy(1f);
        // Set positions and add to stage
        playerPokemon.setPosition((640/3)-playerPokemon.getWidth(), (640/3)-playerPokemon.getHeight());
        enemyPokemon.setPosition((2f*(640/3)-enemyPokemon.getWidth()), (1.25f*(640/3)-enemyPokemon.getHeight()));
        stage.addActor(playerPokemon);
        stage.addActor(enemyPokemon);
        BattleUI battleUI = new BattleUI(skin);
        stage.addActor(battleUI);
        battleLogic = new BattleLogic(new Pokemon[]{currentYourMon,currentEnemyMon},this);
        //status boxes
        Table statusBoxRoot = new Table();
        statusBoxRoot.setFillParent(true);
        stage.addActor(statusBoxRoot);
        playerStatus = new DetailedStatusBox(skin);
        playerStatus.setName(currentYourMon.getName());
        playerStatus.setLevel(currentYourMon.getLevel());
        opponentStatus = new StatusBox(skin);
        opponentStatus.setName(currentEnemyMon.getName());
        opponentStatus.setLevel(currentEnemyMon.getLevel());
        statusBoxRoot.add(opponentStatus).expand().align(Align.topLeft);
        statusBoxRoot.add(playerStatus).align(Align.right);
        playerStatus.setHPText(currentYourMon.getHp(),currentYourMon.getHp());
        StringBuilder entryDialogText = new StringBuilder();
        if(encounter) {
            entryDialogText.append("A wild ").append(currentEnemyMon.getSpecies()).append(" has appeared.");
        }
        battleUI.setDialogText(entryDialogText.toString());

    }

    public static BattleLogic getBattleLogic() {
        return battleLogic;
    }


    private void pokemonHpUpdate() {
        playerStatus.displayHPLeft(currentYourMon.getCurrentHp()/currentYourMon.getMaxHp());
        opponentStatus.displayHPLeft(currentEnemyMon.getCurrentHp()/currentEnemyMon.getMaxHp());
        playerStatus.setHPText((int) currentYourMon.getCurrentHp(), (int) currentYourMon.getMaxHp());
    }
    @Override
    public void render(float delta) {
        isBattleOver();
        if(!checkAllFainted(enemyParty)){
            checkAllFainted(player.getParty());
        }
        battleLogic.battlePhase();
        pokemonHpUpdate();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
        music.dispose();
    }
    private void isBattleOver(){
        if(battleOver) {
            music.stop();
            game.setScreen(returnScreen);
            this.dispose();
        }
        battleOver = false;
    }
    public boolean checkAllFainted(Pokemon[] party){
        boolean allFainted = true;
        for(Pokemon pokemon : party){
            if(pokemon == null) {
                continue;
            }
            if(pokemon.getStatus() != Pokemon.PokemonStatus.FAINTED){
                allFainted = false;
            }
        }
        battleOver = (allFainted);
        return allFainted;
    }

    public static void setbattleOver(Boolean value){
       battleOver = value;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

}
