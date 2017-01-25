package Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SprHitPoint extends Sprite {

    Texture txHitPoint;
    private Sprite sprHitPoint;
    Vector2 vPos, vPrevPos, vOrigPart, vPart;

    SprHitPoint(Texture _txHitPoint, Vector2 _vPart) {
        
        vPos = new Vector2(0, 0);
        vOrigPart = new Vector2(_vPart.x, _vPart.y);//the vOrigPart vector is used to hold the original positon of the hit point within the sprite in the default animation
        vPos.set(vOrigPart);
        vPart = new Vector2(vOrigPart.x, vOrigPart.y);
        txHitPoint = _txHitPoint;
        sprHitPoint = new Sprite(txHitPoint);
        vPrevPos = new Vector2(0, 0);
    }

    void update(Vector2 _vDinoPos, int nAni, Sprite _sprDino) {
        if (nAni == 0||nAni == 1) { //if the animation is facing right, keep the orientation of the hit point
            vPart.set(vOrigPart);
        } else if (nAni == 2 || nAni == 5) {//if the animation is facing left, reflect the hitpoints in the middle of the image's veticle axis 
            if(vPart.x < (_sprDino.getWidth()/2)){
                 vPart.set((_sprDino.getWidth()/2)+((_sprDino.getWidth()/2)- vOrigPart.x),vOrigPart.y);
            }
            vPart.set(((_sprDino.getWidth()/2)-(vOrigPart.x-(_sprDino.getWidth()/2)))-sprHitPoint.getWidth(),vOrigPart.y);
        } 
        vPrevPos.set(vPos);
        vPos.set(_vDinoPos.x + vPart.x, _vDinoPos.y + vPart.y);
        sprHitPoint.setPosition(vPos.x, vPos.y);
    }

    public Sprite getSprite() {
        return sprHitPoint;
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
