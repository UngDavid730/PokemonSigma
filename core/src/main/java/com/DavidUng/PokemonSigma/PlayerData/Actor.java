package com.DavidUng.PokemonSigma.PlayerData;

import com.DavidUng.PokemonSigma.PokemonSigma;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.net.SocketTimeoutException;
import java.util.Random;

public class Actor extends Sprite implements InputProcessor {
    private ACTOR_STATE state;
    private TiledMapTileLayer encounterLayer;
    private TiledMapTileLayer collisonLayer;
    private TiledMapTileLayer surfLayer;
    private TiledMapTileLayer portalLayer;
    private Boolean encounter = false;
    private int TILESIZE = PokemonSigma.getTileSize();
    private TextureAtlas playerAtlas;
    private Vector2 currentPosition;
    private Vector2 targetPosition;
    private Interpolation interpolation;
    private DIRECTION_STATE direction;
    private float elapsed;
    private float duration;

    private Animation<TextureRegion> walkNorth, walkSouth, walkEast, walkWest;
    private TextureRegion currentFrame;
    private float animationTime = 0f;





    public Actor(Sprite sprite, TiledMapTileLayer[] layers) {
        super(sprite);
        encounterLayer = layers[1];
        collisonLayer = layers[0];
        surfLayer = layers[2];
        portalLayer = layers[3];
        this.state = ACTOR_STATE.STANDING;
        this.direction =  DIRECTION_STATE.WEST;
        // Set the starting position of the actor
        float startX = 928.0f;
        float startY = 320.0f;
        setPosition(startX, startY);
        // Now initialize based on that
        currentPosition = new Vector2(startX, startY);
        targetPosition = new Vector2(startX, startY);
        // Use the class field
        this.interpolation = Interpolation.linear;
        playerAtlas = new TextureAtlas(Gdx.files.internal("playerSprites/playerAtlas.atlas"));

        //animation construct
        walkNorth = new Animation<>(0.15f, playerAtlas.findRegions("dawnNORTH"), Animation.PlayMode.LOOP);
        walkSouth = new Animation<>(0.15f, playerAtlas.findRegions("dawnSOUTH"), Animation.PlayMode.LOOP);
        walkEast = new Animation<>(0.15f, playerAtlas.findRegions("dawnEAST"), Animation.PlayMode.LOOP);
        walkWest = new Animation<>(0.15f, playerAtlas.findRegions("dawnWEST"), Animation.PlayMode.LOOP);
;
    }

    public enum ACTOR_STATE{
        WALKING,
        STANDING,
    }
    public enum DIRECTION_STATE{
        NORTH,
        SOUTH,
        EAST,
        WEST,
    }
    private void startMove(int oldX, int oldY, int newX, int newY){
        currentPosition.set(oldX, oldY); // set base from current position
        targetPosition.set(newX, newY);
        duration = 0.3f; // smoother and snappier feel
        elapsed = 0f;
        state = ACTOR_STATE.WALKING;
    }

    public void update(float delta) {
        elapsed += delta;
        // Only start a move if currently standing
        if (state == ACTOR_STATE.STANDING) {
            int x = 0;
            int y = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x = -1;
                direction = DIRECTION_STATE.WEST;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x = 1;
                direction = DIRECTION_STATE.EAST;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y = 1;
                direction = DIRECTION_STATE.NORTH;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y = -1;
                direction = DIRECTION_STATE.SOUTH;
            }
            if ((x != 0 || y != 0) && !collisionCheck(x, y) && !surfCheck(x, y)) {
                startMove((int) getX(), (int) getY(), (int) (getX() + TILESIZE * x), (int) (getY() + TILESIZE * y));
                genEncounter();
            }
        }
        // interpolation
        float progress = Math.min(1f, elapsed / duration);
        float alpha = interpolation.apply(progress);
        Vector2 interpolated = new Vector2(currentPosition).lerp(targetPosition, alpha);
        setPosition(interpolated.x, interpolated.y);
        // animation = over;
        if (progress >= 1f) {
            state = ACTOR_STATE.STANDING;
            currentPosition.set(targetPosition);
        } else {
            state = ACTOR_STATE.WALKING;
        }
        // animation
        animationTime += delta;
        if (state == ACTOR_STATE.WALKING) {
            switch (direction) {
                case NORTH:
                    currentFrame = walkNorth.getKeyFrame(animationTime);
                    break;
                case SOUTH:
                    currentFrame = walkSouth.getKeyFrame(animationTime);
                    break;
                case EAST:
                    currentFrame = walkEast.getKeyFrame(animationTime);
                    break;
                case WEST:
                    currentFrame = walkWest.getKeyFrame(animationTime);
                    break;
            }
            setRegion(currentFrame);
        } else {
            animationTime = 0f; // reset to first frame when standing
        }
    }

    public void actorDraw(Batch batch){
        super.draw(batch);
    }
    public void genEncounter(){
        if(encounterLayer.getCell((int) (getX()/TILESIZE), (int) getY()/TILESIZE) != null) {
            int random = new Random().nextInt(2);
            System.out.println(random);
            if(random == 1) {
                this.encounter = true;
            }
        }
    }
    public void resetEncounter(){
        encounter = false;
    }
    public Boolean getEncounter() {
        return encounter;
    }
    public boolean collisionCheck(int x, int y){return (collisonLayer.getCell((int) (getX()/TILESIZE)+x, (int) (getY()/TILESIZE)+y) != null);}
    public boolean surfCheck(int x, int y){return (surfLayer.getCell((int) (getX()/TILESIZE)+x, (int) (getY()/TILESIZE)+y) != null);}

    @Override
    public boolean keyDown(int keycode) {
        int x = 0;
        int y = 0;
        if(state != ACTOR_STATE.STANDING){
            return false;
        }
        switch (keycode) {
            case Input.Keys.LEFT:
                x -= 1;
                direction = DIRECTION_STATE.WEST;
                break;
            case Input.Keys.RIGHT:
                x += 1;
                direction = DIRECTION_STATE.EAST;
                break;
            case Input.Keys.UP:
                y += 1;
                direction = DIRECTION_STATE.NORTH;
                break;
            case Input.Keys.DOWN:
                y -= 1;
                direction = DIRECTION_STATE.SOUTH;
                break;
        }
        if(collisionCheck(x,y) || surfCheck(x,y)) {return false;}
        portalCheck();
        startMove((int) getX(), (int) getY(), (int) (getX()+(TILESIZE)*x), (int) (getY()+(TILESIZE)*y));
        genEncounter();
        return false;
    }

    public String portalCheck() {
        if(portalLayer.getCell((int) (getX()/TILESIZE), (int) getY()/TILESIZE) != null){
            return portalLayer.getProperties().get("portal").toString();
        }
        return null;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
