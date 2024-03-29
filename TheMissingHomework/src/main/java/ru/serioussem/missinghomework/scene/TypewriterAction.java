package ru.serioussem.missinghomework.scene;

import ru.serioussem.missinghomework.actors.DialogBox;

public class TypewriterAction extends SetTextAction {
    private float elapsedTime;
    private float charactersPerSecond;

    public TypewriterAction(String t) {
        super(t);
        elapsedTime = 0;
        charactersPerSecond = 30;
    }

    @Override
    public boolean act(float dt) {
        elapsedTime += dt;
        int numberOfCharacters = (int) (elapsedTime * charactersPerSecond);
        if (numberOfCharacters > textToDisplay.length()) {
            numberOfCharacters = textToDisplay.length();
        }
        String partialText = textToDisplay.substring(0, numberOfCharacters);
        DialogBox db = (DialogBox) target;
        db.setText(partialText);
        return (numberOfCharacters >= textToDisplay.length());
    }
}
