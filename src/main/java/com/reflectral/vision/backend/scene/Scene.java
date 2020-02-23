package com.reflectral.vision.backend.scene;

import com.reflectral.vision.backend.scene.shape.Shape;

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
