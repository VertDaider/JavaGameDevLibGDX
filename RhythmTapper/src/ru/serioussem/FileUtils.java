package ru.serioussem;

import com.badlogic.gdx.files.FileHandle;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;

import java.io.File;

public class FileUtils {
    private static boolean finished;
    private static FileHandle fileHandle;
    private static int openDialog = 1;
    private static int saveDialog = 2;

    static FileHandle showOpenDialog() {
        return showDialog(openDialog);
    }

    public static FileHandle showSaveDialog() {
        return showDialog(saveDialog);
    }

    private static FileHandle showDialog(int dialogType) {
        new JFXPanel();

        finished = false;

        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            File file;

            if (dialogType == openDialog) {
                file = fileChooser.showOpenDialog(null);
            } else // dialogType == saveDialog
            {
                file = fileChooser.showSaveDialog(null);
            }
            if (file != null) {
                fileHandle = new FileHandle(file);
            } else {
                fileHandle = null;
            }
            finished = true;
        });

        while (!finished) {
            // waiting for FileChooser window to close
        }
        return fileHandle;
    }
}