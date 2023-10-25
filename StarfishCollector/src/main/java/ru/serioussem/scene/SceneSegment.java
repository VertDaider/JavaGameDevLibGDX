package ru.serioussem.scene;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SceneSegment {
    private Actor actor;
    private Action action;

    public SceneSegment(Actor a1, Action a2) {
        actor = a1;
        action = a2;
    }

    public void start() {
        actor.clearActions();
        actor.addAction(action);
    }

    public boolean isFinished() {
        return (actor.getActions().size == 0);
    }

    public void finish() {
        if (actor.hasActions()) {
            actor.getActions().first().act(100000);
        }
        actor.clearActions();
    }
}