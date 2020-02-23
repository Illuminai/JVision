package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene
 */
public class Scene {
    private double time = 0;
    /**
     * the shapes
     */
    private List<Shape> shapes;

    /**
     * Constructs and initializes a Scene.
     */
    public Scene() {
        shapes = new ArrayList<>();
    }

    /**
     * @return the shapes
     */
    public List<Shape> getShapes() {
        return shapes;
    }


    public void tick() {
        green.setPosition(new Point3d(2 * Math.sin(time/20),2 * Math.cos(time/20), 3));
        white.setPosition(new Point3d(3,Math.sin(time/20),Math.cos(time/20)));
        time++;
    }

    Sphere green;
    Sphere white;

    public void sceneInit() {
        green = new Sphere(new Point3d(0, 0, 1), 1,0x00ff00);
        getShapes().add(green);
        white = new Sphere(new Point3d(3, 0, 0), .5,0xffffff);
        getShapes().add(white);


        getShapes().add(new Sphere(new Point3d(3, 0, 0), .5,0));
        getShapes().add(new Sphere(new Point3d(3, -1, -1), .5,0xff));

    }
}
