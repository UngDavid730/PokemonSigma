package com.DavidUng.PokemonSigma.screens;
import com.DavidUng.PokemonSigma.PlayerData.Actor;
import com.DavidUng.PokemonSigma.PlayerData.Player;
import com.DavidUng.PokemonSigma.PokemonSigma;
import com.DavidUng.PokemonSigma.battleLogic.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.Random;

public class RouteScene implements Screen {
    private Player player = Player.getInstance();
    PokemonSigma game;
    private String[] pokemonEncounters = {
        "Turtwig", "Grotle", "Torterra", "Torchic", "Combusken", "Blaziken",
        "Oshawott", "Dewott", "Samurott", "Sentret", "Furret", "Zigzagoon",
        "Linoone", "Pidgey", "Pidgeotto", "Pidgeot", "Starly", "Staravia",
        "Staraptor", "Caterpie", "Metapod", "Butterfree", "Sewaddle", "Swadloon",
        "Leavanny", "Zubat", "Golbat", "Crobat", "Roggenrola", "Boldore",
        "Gigalith", "Ekans", "Arbok", "Dwebble", "Crustle", "Mienfoo", "Mienshao",
        "Drifloon", "Drifblim", "Shuckle", "Abra", "Kadabra", "Gothita",
        "Gothorita", "Gothitelle", "Magnemite", "Magneton", "Magnezone",
        "Electrike", "Manectric", "Sandile", "Krokorok", "Krookodile", "Cacnea",
        "Cacturne", "Kabuto", "Kabutops", "Shieldon", "Bastiodon", "Bagon",
        "Shelgon", "Salamence", "Snover", "Abomasnow", "Spheal", "Sealeo",
        "Walrein", "Shuppet", "Banette", "Litwick", "Lampent", "Chandelure",
        "Forretress", "Probopass", "Tangela", "Tangrowth", "Lopunny", "Chimecho",
        "Tropius", "Mawile", "Claydol", "Eevee", "Vaporeon", "Jolteon", "Flareon",
        "Espeon", "Umbreon", "Leafeon", "Glaceon", "Diglett", "Dugtrio", "Numel",
        "Camerupt", "Houndour", "Houndoom", "Meditite", "Medicham", "Snorunt",
        "Glalie", "Voltorb", "Electrode", "Trapinch", "Vibrava", "Flygon",
        "Murkrow", "Honchkrow", "Sableye", "Lunatone", "Solrock", "Heracross",
        "Hippopotas", "Hippowdon", "Baltoy", "Claydol", "Yanma", "Yanmega",
        "Breloom", "Shroomish", "Nosepass", "Lickitung", "Aipom", "Torkoal",
        "Glameow", "Purugly", "Carnivine", "Chatot", "Whismur", "Loudred",
        "Exploud", "Finneon", "Lumineon", "Remoraid", "Octillery", "Corsola",
        "Relicanth", "Phanpy", "Donphan", "Slakoth", "Vigoroth", "Slaking",
        "Minccino", "Cinccino", "Audino", "Dratini", "Dragonair", "Dragonite",
        "Uxie", "Mesprit", "Azelf", "Cresselia"};
    private int[] encounterLevels = {2,3,4,5,35,20,22,16,15};

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Actor actor;


    public RouteScene(PokemonSigma pokemonSigma,String mapname) {
        init(pokemonSigma, mapname);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(actor);
    }
    public void cameraUpdate(){
        float spriteCenterX = actor.getX() + actor.getWidth() / 2f;
        float spriteCenterY = actor.getY() + actor.getHeight() / 2f;
        camera.position.x = spriteCenterX;
        camera.position.y = spriteCenterY;
        camera.update();
    }
    @Override
    public void render(float delta) {
        cameraUpdate();
        renderer.render();
        renderer.setView(camera);
        renderer.getBatch().begin();
        actor.actorDraw(renderer.getBatch());
        renderer.getBatch().end();
        encounterCheck();
        actor.update(Gdx.graphics.getDeltaTime());
//        healTeam();
    }

//    private void healTeam() {
//        for(Pokemon pokemon : player.getParty()){
//            pokemon.restore();
//        }
//    }

    private void encounterCheck() {
        if(actor.getEncounter()){
            int level = encounterLevels[new Random().nextInt(encounterLevels.length)];
            String species = pokemonEncounters[new Random().nextInt(pokemonEncounters.length)];
            Pokemon encounter = Pokemon.encounter(species,level);
            System.out.println(species + level);
            actor.resetEncounter();
            game.setScreen(new BattleScene(new Pokemon[]{encounter},true, this,game));
        }
    }
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        actor.getTexture().dispose();
    }
    public void init(PokemonSigma pokemonSigma, String mapname) {
        this.game = pokemonSigma;
        map = new TmxMapLoader().load("maps/" + mapname + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        int mapHeightInTiles = map.getProperties().get("height", Integer.class);
        int mapWidthInTiles = map.getProperties().get("width", Integer.class);
        int tileHeightInPixels = map.getProperties().get("tileheight", Integer.class);
        int tileWidthInPixels = map.getProperties().get("tilewidth", Integer.class);
        TiledMapTileLayer encounterLayer = (TiledMapTileLayer) map.getLayers().get("encounter");
        TiledMapTileLayer collisonLayer = (TiledMapTileLayer) map.getLayers().get("barrier");
        TiledMapTileLayer surfLayer = (TiledMapTileLayer) map.getLayers().get("surf");
        TiledMapTileLayer portalLayer = (TiledMapTileLayer) map.getLayers().get("portal");
        TiledMapTileLayer[] layers = {collisonLayer,encounterLayer,surfLayer,portalLayer};
        actor = new Actor(new Sprite(new Texture(Gdx.files.internal("dawn.png"))),layers);
        actor.setX((mapWidthInTiles * tileWidthInPixels)-tileWidthInPixels);
        actor.setY(((float) mapHeightInTiles /2) * tileHeightInPixels);

    }

}
