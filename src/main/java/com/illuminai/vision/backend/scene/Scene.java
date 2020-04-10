package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.light.PointLight;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.Sphere;

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

    private List<PointLight> lights;

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

    public List<PointLight> getLights() {
        return lights;
    }

    //TODO: Remove hard coded stuff

    private double time = 0;

    public void tick() {
        time++;
    }

    //Sphere red, green, blue;
    public void sceneInit() {
        /*SphereSDF s1 = new SphereSDF(new Vector3d(0, 0, 0), .3);
        TorusSDF s2 = new TorusSDF(.1, .3);
        IntersectionSDF sdf = new IntersectionSDF(s1, s2);

        SphereSDF s3 = new SphereSDF(new Vector3d(.2,.2,.2), .2);
        SubtractionSDF sdf2 = new SubtractionSDF(s3, sdf);*/

        shapes.add(new Sphere(new Vector3d(1, 1, 1), .2, 0xff00ff));
        shapes.add(new Sphere(new Vector3d(2, 2, 2), .1, 0xff00ff));
        lights.add(new PointLight(new Vector3d(0,0,3)));
    }
}
