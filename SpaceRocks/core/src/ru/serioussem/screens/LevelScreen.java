package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.serioussem.actors.BaseActor;
import ru.serioussem.actors.Explosion;
import ru.serioussem.actors.Rock;
import ru.serioussem.actors.Spaceship;

public class LevelScreen extends BaseScreen{
    private Spaceship spaceship;
    private boolean gameOver;

    @Override
    public void initialize() {
        BaseActor space = new BaseActor(0, 0,mainStage);
        space.loadTexture("space.png");
        space.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BaseActor.setWorldBounds(space);
        gameOver = false;

        spaceship = new Spaceship(400, 300, mainStage);

        new Rock(600,500, mainStage);
        new Rock(600,300, mainStage);
        new Rock(600,100, mainStage);
        new Rock(400,100, mainStage);
        new Rock(200,100, mainStage);
        new Rock(200,300, mainStage);
        new Rock(200,500, mainStage);
        new Rock(400,500, mainStage);
    }

    @Override
    public void update(float dt) {
        for (BaseActor rockActor: BaseActor.getList(mainStage, "ru.serioussem.actors.Rock")) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    Explosion boom = new Explosion(0,0,mainStage);
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000,-1000);
                    BaseActor messageLose = new BaseActor(0,0, uiStage);
                    messageLose.loadTexture("message-lose.png");
                    messageLose.centerAtPosition((float) Gdx.graphics.getWidth() / 2,(float) Gdx.graphics.getHeight() / 2);
                    messageLose.setOpacity(0);
                    messageLose.addAction( Actions.fadeIn(1) );
                    gameOver = true;
                } else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0,0,mainStage);
                    boom.centerAtActor(rockActor);
                    rockActor.remove();
                }
            }

            for (BaseActor laserActor : BaseActor.getList(mainStage, "ru.serioussem.actors.Laser")) {
                if (laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0,0,mainStage);
                    boom.centerAtActor(rockActor);
                    laserActor.remove();
                    rockActor.remove();
                }
            }
        }

        if (!gameOver && BaseActor.count(mainStage,"ru.serioussem.actors.Rock" ) == 0) {
            BaseActor messageWin = new BaseActor(0,0,mainStage);
            messageWin.loadTexture("message-win.png");
            messageWin.centerAtPosition((float) Gdx.graphics.getWidth() / 2,(float) Gdx.graphics.getHeight() / 2);
            messageWin.setOpacity(0);
            messageWin.addAction(Actions.fadeIn(1));
            gameOver = true;
        }
    }

    @Override
    public boolean keyDown( int keycode) {
        if (keycode == Input.Keys.X) {
            spaceship.warp();
        }
        if (keycode == Input.Keys.SPACE) {
            spaceship.shoot();
        }
        return false;
    }
}