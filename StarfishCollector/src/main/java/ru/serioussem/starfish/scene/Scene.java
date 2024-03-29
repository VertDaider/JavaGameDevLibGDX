package ru.serioussem.starfish.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Scene extends Actor {
    private ArrayList<SceneSegment> segmentList;
    private int index;

    public Scene() {
        super();
        segmentList = new ArrayList<>();
        index = -1;
    }

    public void addSegment(SceneSegment segment) {
        segmentList.add(segment);
    }

    public void clearSegments() {
        segmentList.clear();
    }

    public void start() {
        index = 0;
        segmentList.get(index).start();
    }

    public void act(float dt) {
        if (isSegmentFinished() && !isLastSegment()) {
            loadNextSegment();
        }
    }

    public void loadNextSegment() {
        if (isLastSegment()) return;
        segmentList.get(index).finish();
        index++;
        segmentList.get(index).start();
    }

    public boolean isLastSegment() {
        return (index >= segmentList.size() - 1);
    }

    public boolean isSegmentFinished() {
        return segmentList.get(index).isFinished();
    }

    public boolean isSceneFinished() {
        return (isLastSegment() && isSegmentFinished());
    }
}