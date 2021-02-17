package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.serioussem.BaseGame;
import ru.serioussem.FileUtils;
import ru.serioussem.SongData;

public class RecorderScreen extends BaseScreen {
    Music music;
    SongData songData;
    float lastSongPosition;
    boolean recording;
    TextButton loadButton;
    TextButton recordButton;
    TextButton saveButton;

    @Override
    public void initialize() {
        recording = false;

        loadButton = new TextButton("Load Music File", BaseGame.textButtonStyle);
        loadButton.addListener(
                (Event e) -> {
                    if (!isTouchDownEvent(e)) {
                        return false;
                    }
                    FileHandle musicFile = FileUtils.showOpenDialog();
                    if (musicFile != null) {
                        music = Gdx.audio.newMusic(musicFile);
                        songData = new SongData();
                        songData.setSongName(musicFile.name());
                    }
                    return true;
                }
        );

        recordButton = new TextButton("Record Keystrokes", BaseGame.textButtonStyle);
        recordButton.addListener(
                (Event e) -> {
                    if (!isTouchDownEvent(e)) {
                        return false;
                    }
                    if (!recording) {
                        music.play();
                        recording = true;
                        lastSongPosition = 0;
                    }
                    return true;
                }
        );

        saveButton = new TextButton("Save Keystrokes File", BaseGame.textButtonStyle);
        saveButton.addListener(
                (Event e) -> {
                    if (!isTouchDownEvent(e)) {
                        return false;
                    }
                    FileHandle textFile = FileUtils.showSaveDialog();

                    if (textFile != null) {
                        songData.writeToFile(textFile);
                    }
                    return true;
                }
        );

        uiTable.add(loadButton);
        uiTable.row();
        uiTable.add(recordButton);
        uiTable.row();
        uiTable.add(saveButton);
    }

    @Override
    public void update(float dt) {
        if (recording) {
            if (music.isPlaying()) {
                lastSongPosition = music.getPosition();
            } else {
                recording = false;
                songData.setSongDuration(lastSongPosition);
            }
        }
    }

    public boolean keyDown(int keycode) {
        if (recording) {
            String key = Input.Keys.toString(keycode);
            Float time = music.getPosition();
            songData.addKeyTime(key, time);
        }
        return false;
    }
}
