package Screens;

import Menu.GamPlatforms;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class ScrMain extends InputAdapter implements Screen {
    
    GamPlatforms gamPlatforms;
    SpriteBatch batch;
    BitmapFont textFont, textFontLevel, textCreate, textOrder, textExit;
    boolean isChange = false, isExit = false;
    Texture txDinFor1;
    float fScreenWidth = Gdx.graphics.getWidth(), fScreenHei = Gdx.graphics.getHeight();
    Sprite sprBack;
    SprDino sprDino;

    public void SetFont() {
        FileHandle fontFile = Gdx.files.internal("Woods.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.BLACK;
        textFont = generator.generateFont(parameter);
        parameter.size = 45;
        textFontLevel = generator.generateFont(parameter);
        parameter.size = 15;
        textCreate = generator.generateFont(parameter);
        parameter.size = 25;
        textOrder = generator.generateFont(parameter);
        parameter.size = 25;
        textExit = generator.generateFont(parameter);
        generator.dispose();
    }

    public ScrMain(GamPlatforms _game) {
        SetFont();
        batch = new SpriteBatch();        
        txDinFor1 = (new Texture(Gdx.files.internal("0.png")));
        gamPlatforms = _game;
        sprBack = new Sprite(new Texture(Gdx.files.internal("world.jpg")));
        sprBack.setSize(fScreenWidth, fScreenHei);
        sprDino = new SprDino(txDinFor1);
        Gdx.input.setInputProcessor((this));
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(sprBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textFontLevel.draw(batch, "DINO DASH", fScreenWidth / 6, (fScreenHei / 10) * 9);
        textCreate.draw(batch, "Created by David and Daniel", fScreenWidth / 6, (fScreenHei / 10) * 8);
        textOrder.draw(batch, "To Proceed, Click Enter", fScreenWidth / 5, (fScreenHei / 10) * 5);
        textExit.draw(batch, "To Exit, Click Esc", fScreenWidth / 5, (fScreenHei / 10) * 6);
        batch.draw(sprDino.getSprite(), fScreenWidth / 3, sprDino.getY());
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gamPlatforms.nScreen = 1;
            gamPlatforms.updateState();
            dispose();
        } 
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {
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
    }
}
