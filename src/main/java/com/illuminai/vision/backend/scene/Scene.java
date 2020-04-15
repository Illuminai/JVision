package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.scene.light.DistantLight;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.material.Material;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.Sphere;
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

    //Sphere red, green, blue;
    public void sceneInit() {
        /*SphereSDF s1 = new SphereSDF(new Vector3d(0, 0, 0), .3);
        TorusSDF s2 = new TorusSDF(.1, .3);
        IntersectionSDF sdf = new IntersectionSDF(s1, s2);

        SphereSDF s3 = new SphereSDF(new Vector3d(.2,.2,.2), .2);
        SubtractionSDF sdf2 = new SubtractionSDF(s3, sdf);*/

        shapes.add(new Sphere(new Vector3d(5, 0, 0), .1, new Material(0.18)));

        for(int x = 0; x < 7; x++) {
            Vector3d p1 = new Vector3d(.1,0,0);
            Vector3d p2 = new Vector3d(0,0,0);
            Vector3d p3 = new Vector3d(0,0,.1);
            Vector3d loc = new Vector3d((x - 3.) / 3+3,(x - 3.) / 3, (x - 3.) / 3);
            shapes.add(new Triangle(loc.add(p1), loc.add(p2), loc.add(p3), new Material(.8)));
            shapes.add(new Sphere(loc.add(p1), .01, new Material(0.18)));
            shapes.add(new Sphere(loc.add(p2), .01, new Material(0.18)));
            shapes.add(new Sphere(loc.add(p3), .01, new Material(0.18)));
        }

        shapes.add(new Triangle(new Vector3d(0,0,0),new Vector3d(1,0,0),new Vector3d(0,1,0), new Material(.8)));

        lights.add(new DistantLight(new Color(1, 0, 1), 15, new Vector3d(2, 1, -3)));
        //lights.add(new PointLight(new Color(1, 1, 1), 5, new Vector3d(2, -.2, 1)));
    }
}
