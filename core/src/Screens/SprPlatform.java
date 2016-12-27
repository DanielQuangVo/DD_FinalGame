package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SprPlatform extends Sprite {
    String sFile;
    Texture txPlat;
    private Sprite sprPlat;
    Vector2 vPos, vDir,vPrevPos;

    SprPlatform(Texture _txPlat) {
        txPlat = _txPlat;
        sprPlat = new Sprite(txPlat);
        vPos = new Vector2(500,200);
        vPrevPos = new Vector2(500,50);
        vDir = new Vector2((float) -0.5,0);
    }


    void update() {
        vPrevPos.set(vPos);
        vPos.add(vDir);
        sprPlat.setPosition(vPos.x, vPos.y);
    }
    boolean isOffScreen(){
        if(vPos.x < 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean canSpawn(){
        if(vPos.x < 200){
            return true;
        }
        else{
            return false;
        }
    }

    public Sprite getSprite() {
        return sprPlat;
    }

    //@Override
    public float getX() {
        return vPos.x;
    }

    //@Override
    public float getY() {
        return vPos.y;
    }
}
