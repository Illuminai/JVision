package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.light.PointLight;
import com.illuminai.vision.backend.scene.material.Material;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.Triangle;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene
 */
public class Scene {

    /**
     * the shapes
     */
    private List<Shape> shapes;

    private List<Light> lights;

    /**
     * Constructs and initializes a Scene.
     */
    public Scene() {
        shapes = new ArrayList<>();
        lights = new ArrayList<>();
    }

    /**
     * @return the shapes
     */
    public List<Shape> getShapes() {
        return shapes;
    }

    public List<Light> getLights() {
        return lights;
    }

    //TODO: Remove hard coded stuff

    private double time = 0;

    public void tick() {
        time++;
    }

    public void sceneInit() {
        //shapes.add(new Sphere(new Vector3d(0, 0, 0), .1, new Material(.8)));
        Vector3d p1 = new Vector3d(0,.5,0);
        Vector3d p2 = new Vector3d(.5,0,0);
        Vector3d p3 = new Vector3d(0,0,.5);
        Vector3d loc = new Vector3d(0,0, 0);
        shapes.add(new Triangle(loc.add(p1), loc.add(p2), loc.add(p3), new Material(.8)));
        for(double i = 0; i < Math.PI * 2; i += Math.PI /10) {
            lights.add(new PointLight(new Color(1, 0, 1), 1, new Vector3d(10*Math.sin(i),10* Math.cos(i), Math.cos(2 * i))));
        }
    }
}
