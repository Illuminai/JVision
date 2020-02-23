package ch.herzog.lighthouse.scene;

import ch.herzog.lighthouse.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private List<Shape> shapes;

    public Scene() {
        shapes = new ArrayList<>();
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}
