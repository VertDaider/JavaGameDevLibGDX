package ru.serioussem.rhythmtapper;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class SongData {
    private String songName;
    private float songDuration;
    private ArrayList<KeyTimePair> keyTimeList;
    private int keyTimeIndex;

    public class KeyTimePair {
        private String key;
        private Float time;

        public KeyTimePair(String key, Float time) {
            this.key = key;
            this.time = time;
        }

        public String getKey() {
            return key;
        }

        public Float getTime() {
            return time;
        }
    }

    public SongData() {
        keyTimeList = new ArrayList<>();
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public float getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(float songDuration) {
        this.songDuration = songDuration;
    }

    public void addKeyTime(String k, Float t) {
        keyTimeList.add(new KeyTimePair(k, t));
    }

    public void resetIndex() {
        keyTimeIndex = 0;
    }

    public void advanceIndex() {
        keyTimeIndex++;
    }

    public KeyTimePair getCurrentKeyTime() {
        return keyTimeList.get(keyTimeIndex);
    }

    public int ketTimeCount() {
        return keyTimeList.size();
    }

    public boolean isFinished() {
        return keyTimeIndex >= ketTimeCount();
    }

    public void writeToFile(FileHandle file) {
        file.writeString(getSongName() + "\n", false);
        file.writeString(getSongDuration() + "\n", true);
        for (KeyTimePair ktp : keyTimeList) {
            String data = ktp.getKey() + "," + ktp.getTime() + "\n";
            file.writeString(data, true);
        }
    }

    public void readFromFile(FileHandle file) {
        String rawData = file.readString();
        String[] dataArray = rawData.split("\n");
        setSongName(dataArray[0]);
        setSongDuration(Float.parseFloat(dataArray[1]));
        keyTimeList.clear();
        for (int i = 2; i < dataArray.length; i++) {
            String[] keyTimeData = dataArray[i].split(",");
            String key = keyTimeData[0];
            Float time = Float.parseFloat(keyTimeData[1]);
            keyTimeList.add(new KeyTimePair(key, time));
        }
    }
}
