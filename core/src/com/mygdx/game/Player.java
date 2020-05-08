package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {

    public float x, y;
    private Texture chara_up, chara_left, chara_down, chara_right;
    private TiledMapTileLayer collisionLayer;

    public Player(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
        x = (Gdx.graphics.getWidth() / 2) + (Gdx.graphics.getWidth() / 7);
        y = Gdx.graphics.getHeight()/25;

        loadPlayerTextures();
    }

    public void update (float delta){

    }

    public void render (SpriteBatch sb, int direction){
        if(direction ==1)
            sb.draw(chara_down,x,y);
        if(direction ==2)
            sb.draw(chara_up,x,y);
        if(direction ==3)
            sb.draw(chara_right,x,y);
        if(direction ==4)
            sb.draw(chara_left,x,y);
    }

    public void loadPlayerTextures(){
        chara_up = new Texture("chara_up.png");
        chara_down = new Texture("chara_down.png");
        chara_left = new Texture("chara_left.png");
        chara_right = new Texture("chara_right.png");
    }


    public boolean checkCollisionMap(float xcoord,float ycoord){
        float xWorld = xcoord;
        float yWorld = ycoord; //bottomleft coner

        boolean collisionWithMap = false;
        collisionWithMap = isCellBLocked(xWorld, yWorld);

        if (collisionWithMap) {
            return true;
        }
        return false;
    }

    public boolean isCellBLocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
                (int) (x / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));

        if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
            return true;
        return false;
    }
}