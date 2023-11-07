package ru.serioussem.missinghomework.scene;

import com.badlogic.gdx.scenes.scene2d.Action;
import ru.serioussem.missinghomework.actors.DialogBox;

public class SetTextAction extends Action {
    protected String textToDisplay;

    public SetTextAction(String t) {
        textToDisplay = t;
    }

    @Override
    public boolean act(float delta) {
        DialogBox db = (DialogBox) target;
        db.setText(textToDisplay);
        return true;
    }
}