package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.light.PointLight;
import com.illuminai.vision.backend.scene.material.DiffuseMaterial;
import com.illuminai.vision.backend.scene.material.Material;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.SimplePolygon;
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

    public void sceneInit() {
        Material m = new DiffuseMaterial(1, new Color(1,1,1));


        //shapes.add(new Sphere(new Vector3d(0,0,0),.1,m));
        //shapes.add(new Sphere(new Vector3d(1,1,1),.1,m));

        SimplePolygon p = new SimplePolygon(m,new Vector3d[]{
            new Vector3d(0,0,0),
                new Vector3d(.1,0,0),
                    new Vector3d(0,.1,0),
                    new Vector3d(0,0,.1),
            }, new int[][]{
                    {0,2,1},
                    {1,3,2},
                    {2,3,0},
                    {0,1,3},
                    {0,2,3}
            });
        shapes.add(p);

        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(-1,0,0)));
        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(1,0,0)));
        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(0,-1,0)));
        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(0,1,0)));
        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(0,0,-1)));
        lights.add(new PointLight(new Color(1, 1, 1),
                15, new Vector3d(0,0,1)));
    }

    public void prepare() {
        shapes.forEach(Shape::prepare);
    }
}
