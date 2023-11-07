package ru.serioussem.tanks.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameScore {
    private static final String fileSeparator = System.getProperty("file.separator");
    private static String filePath;

    public static boolean isNewRecord(GameType gameType, int score) throws IOException {
        switch (gameType) {
            case NORMAL:
                filePath = "assets" + fileSeparator + "highScore";
                break;
            case NIGHTMARE:
                filePath = "assets" + fileSeparator + "highScoreNightmare";
                break;
            case TIME:
                filePath = "assets" + fileSeparator + "highScoreTime";
        }
        if (isFileExist()) {
            fillFile();
        }
        return readFromFile() < score;
    }

    public static boolean isFileExist() throws IOException {
        File file = new File(filePath);
        return file.createNewFile();
    }

    public static void fillFile() throws IOException {
        String sb = "0000000000000000000";
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(sb);
        }
    }

    public static void writeToFile(StringBuilder sb) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            if (sb != null) {
                writer.println(sb);
            }
        }
    }

    public static int readFromFile() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        }
        //возвращаем значение в первой строке
        return Integer.parseInt(list.get(0).substring(7));
    }
}
