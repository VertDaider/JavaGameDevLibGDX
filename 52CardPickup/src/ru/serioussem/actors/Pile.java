package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Pile extends DropTargetActor {
    private ArrayList<Card> cardList;

    public Pile(float x, float y, Stage s) {
        super(x, y, s);
        cardList = new ArrayList<>();
        loadTexture("assets/pile.png");
        setSize(100, 120);
        setBoundaryRectangle();
    }

    public void addCard(Card c) {
        cardList.add(0, c);
    }

    public Card getTopCard() {
        return cardList.get(0);
    }

    public int getSize() {
        return cardList.size();
    }
}