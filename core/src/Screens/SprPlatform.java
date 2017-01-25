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
        vPos = new Vector2(500,100 + (int)(Math.random() * 200 ));//randomize the y coordinate of the platform
        vPrevPos = new Vector2(500,50);
        vDir = new Vector2((float) -1.25,0);
    }


    void update() {
        vPrevPos.set(vPos);//know the previous position of the platform for hit detection
        vPos.add(vDir); //manipulate the position based on the direction vector
        sprPlat.setPosition(vPos.x, vPos.y);//set the position of the sprite
    }
    boolean isOffScreen(){
        if(vPos.x + sprPlat.getWidth() < 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean canSpawn(){
        if(vPos.x < 200){//keeps the platforms seperate
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
