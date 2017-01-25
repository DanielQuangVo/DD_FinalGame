package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import java.util.Vector;

public class SprDino extends Sprite {

    private float fScreenWid, faspRat;
    Texture txDino, txDeadDino;
    Texture[] txHitPoint;
    Vector2 vPos, vDir, vGrav, vPrevPos, vHitPoint, vCurPlat;
    private Sprite sprDino;
    boolean bJump, bGrav, bGoThrough, bPlatformCarry, bMove;
    float fGround;
    int[] nHitType;
    SprHitPoint sprHitPoint;
    Array<SprHitPoint> arsprHitPoint;

    SprDino(Texture _txDino, Texture[] _txHitPoint) {
        vCurPlat = new Vector2(200, 200);
        txHitPoint = _txHitPoint;
        txDino = _txDino;
        sprDino = new Sprite(txDino);
        vPos = new Vector2((0), 30);
        vDir = new Vector2(0, 0);
        vGrav = new Vector2(0, 0);
        vPrevPos = new Vector2(0, 30);
        vHitPoint = new Vector2(0, 0);
        fGround = 30;
        bGrav = false;
        bGoThrough = false;
        bPlatformCarry = false;
        bMove = false;
        arsprHitPoint = new Array<SprHitPoint>();
        nHitType = new int[6];
        vHitPoint.set(50, 0); //set all the hit point sprites for the dinosaur
        sprHitPoint = new SprHitPoint(txHitPoint[0], vHitPoint);//each hit point has their own position within the dinosaur
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(66, 33);
        sprHitPoint = new SprHitPoint(txHitPoint[1], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(0, 35);
        sprHitPoint = new SprHitPoint(txHitPoint[2], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(140, 75);
        sprHitPoint = new SprHitPoint(txHitPoint[3], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(170, 110);
        sprHitPoint = new SprHitPoint(txHitPoint[4], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(115, 135);
        sprHitPoint = new SprHitPoint(txHitPoint[5], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        for (int i = 0; i < 6; i++) {
            nHitType[i] = 0;
        }
    }

    void gravity() {
        vPrevPos.set(vPos);
        if (vPos.y < 30) { //if the dinosaur falls below the path
            vPos.set(vPos.x, 30);//set dinosaur to the path and stop gravity from acting upon the sprite
            vDir.set(vDir.x, 0);
            vGrav.set(0, 0);
            bGrav = false;
            bJump = false;
        }
        if (bGrav) {
            vGrav.set(0, (float) (vGrav.y * 1.1)); //make the dinosaur fall faster over time
        }
        if (vPos.y == fGround) { //when the dinosaur lands on the path 
            vDir.set(vDir.x, 0);//stop gravity from acting upon the dinosaur 
            vGrav.set(0, 0); //fGround is used when for where the dinosaur could land, so the platforms as well as the ground
            vPos.set(vPos.x, fGround);
            bJump = false;
            bGrav = false;
        }
    }

    void update(int nAni) {
        if (bPlatformCarry && bMove == false) { //if the platform needs to carry the dinosaur and the user is not moving
            vDir.set((float) -1.25, 0); // then let the platform carry the dinosaur
        } else if (bPlatformCarry == false && bMove == false) { //if the platform doesn't need to be carried and the dinosaur isn't moving
            vDir.set(0, vDir.y);//stop the dinosaur from moving left and right, keep the y direction
        }
        vDir.add(vGrav);//change the direction of the dinosaur based on the gravity
        vPos.add(vDir);//any changes to the dinosaur's position will happen here
        sprDino.setPosition(vPos.x, vPos.y); //set the sprite position to the position vector
        Iterator<SprHitPoint> iter = arsprHitPoint.iterator();
        while (iter.hasNext()) {//loop through the hit point sprites
            SprHitPoint sprHitPoint = iter.next();
            sprHitPoint.update(vPos, nAni, sprDino);//update the hit point with the position of the dinosaur and the animation state
        }
    }

    void PositionSet() {
        sprDino.setPosition(vPos.x, vPos.y);
    }

    int nHitDetection(SprPlatform sprPlatform, int nGeneralHitType) {//
        HitDetectionPoints(sprPlatform);
        if (nGeneralHitType == 0) {
            return 0;
        } else if (nGeneralHitType == 1) {
            return 1;
        } else if (nGeneralHitType == 2) {
            if (nHitType[0] == 2) {
                return 2;
            }
        } else if (nGeneralHitType == 3) {
            if (nHitType[4] == 3 && nHitType[0] != 6 && nHitType[1] != 6) {
                //&& nHitType[2] == 0 && nHitType[3] == 0) {
                if (nHitType[5] == 0 || nHitType[5] == 1 || nHitType[5] == 3) {
                    System.out.println("decapitation");
                    return 5;
                }
            }
            return 3;
        } else if (nGeneralHitType == 4) {
            return 4;
        }
        return 0;
    }

    void HitDetectionPoints(SprPlatform sprPlatform) {//check the type of collision between all hit points
        Iterator<SprHitPoint> iter = arsprHitPoint.iterator();//follows same structure as nHitPlatform() function
        for (int x = 0; iter.hasNext(); x++) {
            SprHitPoint sprHitPoint = iter.next();
            if (sprHitPoint.getSprite().getBoundingRectangle().overlaps(sprPlatform.getSprite().getBoundingRectangle())) {
                if (sprHitPoint.vPrevPos.y >= (sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight())) {
                    nHitType[x] = 2;
                } else if (sprHitPoint.vPos.y == sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight() - 1) {
                    nHitType[x] = 2;
                } else if (sprHitPoint.vPrevPos.y + sprHitPoint.getSprite().getHeight() <= sprPlatform.vPrevPos.y) {
                    nHitType[x] = 3;
                } else if (bGrav && bGoThrough == false) {
                    nHitType[x] = 1;
                } else if (bGoThrough == true) {
                    nHitType[x] = 3;
                } else if (bGrav == false) {
                    nHitType[x] = 4;
                }
                if (x == 0) {
                    System.out.println("feet");//if there is collison between the hit point and the platform print what hit point it is
                } else if (x == 1) {
                    System.out.println("belly");
                } else if (x == 2) {
                    System.out.println("tail");
                } else if (x == 3) {
                    System.out.println("chin");
                } else if (x == 4) {
                    System.out.println("nose");
                } else if (x == 5) {
                    System.out.println("forehead");
                }
            } else if (sprHitPoint.vPos.x <= sprPlatform.vPos.x && sprHitPoint.vPos.x + sprHitPoint.getSprite().getWidth() >= sprPlatform.vPos.x || sprHitPoint.vPos.x >= sprPlatform.vPos.x && sprHitPoint.vPos.x <= sprPlatform.vPos.x + sprPlatform.getSprite().getWidth()) {
                nHitType[x] = 6; // if the hit point is below the platform
                System.out.println("below");
            } else {
                nHitType[x] = 0;
            }
            System.out.println(x + " " + sprPlatform.vPos.x + " " + sprHitPoint.vPos.x);
        }
    }

    void HitDetectionBounds(float _ScreenWid) {
        fScreenWid = _ScreenWid;
        if ((sprDino.getX() + sprDino.getWidth() >= fScreenWid)) {
            vPos.x = fScreenWid - (sprDino.getWidth());
        } else if (sprDino.getX() <= 0) {
            vPos.x = 0;
        }
    }

        void Animate
        (Texture _txDinoState
        
            ) {
        sprDino.setTexture(_txDinoState);
        }

    

    public Sprite getSprite() {
        return sprDino;
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
