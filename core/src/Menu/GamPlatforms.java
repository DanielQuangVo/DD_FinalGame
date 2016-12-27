package Menu;

import Screens.ScrMain;
import com.badlogic.gdx.Game;
import Screens.ScrPlatform;

public class GamPlatforms extends Game {

    ScrMain scrMain;
    ScrPlatform scrPlatform;
    public int nScreen;

    public void updateState() {
        if (nScreen == 0) {
            setScreen(scrMain);
        } else if (nScreen == 1) {
            setScreen(scrPlatform);
        }
    }

    @Override
    public void create() {
        nScreen = 0;
        scrMain = new ScrMain(this);
        scrPlatform = new ScrPlatform(this);
        updateState();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

}
