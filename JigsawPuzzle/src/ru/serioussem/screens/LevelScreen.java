package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.BaseActor;
import ru.serioussem.actors.PuzzleArea;
import ru.serioussem.actors.PuzzlePiece;

public class LevelScreen extends BaseScreen {
    private Label messageLabel;

    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("assets/background.jpg");
        BaseActor.setWorldBounds(background);

        int numberRows = 5;
        int numberCols = 5;

        Texture texture = new Texture(Gdx.files.internal("assets/pikachu.jpg"), true);
        int imageWidth = texture.getWidth();
        int imageHeight = texture.getHeight();
        int pieceWidth = imageWidth / numberCols;
        int pieceHeight = imageHeight / numberRows;

        TextureRegion[][] temp = TextureRegion.split(texture, pieceWidth, pieceHeight);

        for (int r = 0; r < numberRows; r++) {
            for (int c = 0; c < numberCols; c++) {
                //create puzzle piece at random location on left half of screen
                int pieceX = MathUtils.random(0, Gdx.graphics.getWidth() / 2 - pieceWidth);
                int pieceY = MathUtils.random(0, Gdx.graphics.getHeight() - pieceHeight);
                PuzzlePiece pp = new PuzzlePiece(pieceX, pieceY, mainStage);

                Animation<TextureRegion> anim = new Animation<>(1, temp[r][c]);
                pp.setAnimation(anim);
                pp.setRow(r);
                pp.setCol(c);

                int marginX = (Gdx.graphics.getWidth() / 2 - imageWidth) / 2;
                int marginY = (Gdx.graphics.getHeight() - imageHeight) / 2;

                int areaX = (Gdx.graphics.getWidth() / 2 + marginX) + pieceWidth * c;
                int areaY = (Gdx.graphics.getHeight() - marginY - pieceHeight) - pieceHeight * r;

                PuzzleArea pa = new PuzzleArea(areaX, areaY, mainStage);
                pa.setSize(pieceWidth, pieceHeight);
                pa.setBoundaryRectangle();
                pa.setRow(r);
                pa.setCol(c);
            }
        }

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);
        uiTable.add(messageLabel).expandX().expandY().bottom().pad(50);
        messageLabel.setVisible(false);
    }

    public void update(float dt) {
        boolean solved = true;
        for (BaseActor actor : BaseActor.getList(mainStage, "ru.serioussem.actors.PuzzlePiece")) {
            PuzzlePiece pp = (PuzzlePiece) actor;

            if (!pp.isCorrectlyPlaced()) {
                solved = false;
            } else {  // если на месте, то уже не оторвать
                pp.setDraggable(false);
            }

            if (solved) {
                messageLabel.setText("You win!");
                messageLabel.setVisible(true);
            } else {
                messageLabel.setText("...");
                messageLabel.setVisible(false);
            }
        }

    }
}