package ru.serioussem.mazerunman.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.mazerunman.Maze;
import ru.serioussem.mazerunman.actor.Hero;
import ru.serioussem.mazerunman.actor.Wall;

public class LevelScreen extends BaseScreen {
    Maze maze;
    Hero hero;

    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("assets/white.png");
        background.setColor(Color.GRAY);
        maze = new Maze(mainStage);
        background.setSize(maze.getRoomWidth(), maze.getRoomHeight());

        hero = new Hero(0,0,mainStage);
        hero.centerAtActor(maze.getRoom(0,0));
    }

    @Override
    public void update(float dt) {
        for (BaseActor wall : BaseActor.getList(mainStage, Wall.class.getName())) {
            hero.preventOverlap(wall);
        }
    }

    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.R)
            BaseGame.setActiveScreen(new LevelScreen());
        return false;
    }
}
