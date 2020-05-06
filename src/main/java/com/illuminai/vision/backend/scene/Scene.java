package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.scene.light.DistantLight;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.light.PointLight;
import com.illuminai.vision.backend.shape.Shape;
import com.illuminai.vision.backend.shape.implicit.Plane;
import com.illuminai.vision.backend.shape.sdf.SDFShape;
import com.illuminai.vision.backend.shape.sdf.SignedDistanceField;
import com.illuminai.vision.backend.shape.sdf.alteration.RevolutionSDF;
import com.illuminai.vision.backend.shape.sdf.csg.SubtractionSDF;
import com.illuminai.vision.backend.shape.sdf.flat.Onion2dSDF;
import com.illuminai.vision.backend.shape.sdf.flat.UnevenCapsuleSDF;
import com.illuminai.vision.backend.shape.sdf.shape.SphereSDF;

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
        //SphereSDF s1 = new SphereSDF(.3, new Vector3d(0, 0, 0));
        //TorusSDF s2 = new TorusSDF(.1, .3);
        //IntersectionSDF sdf = new IntersectionSDF(s1, s2);

        //SphereSDF s3 = new SphereSDF(.2, new Vector3d(.2, .2, .2));
        //SubtractionSDF sdf2 = new SubtractionSDF(s3, sdf);

        //shapes.add(new SDFShape(new Vector3d(0, 0, 2), sdf2));
        //shapes.add(new SDFShape(new Vector3d(0, 0, 1.3), new OctahedronSDF()));
        SignedDistanceField sdf1 = new RevolutionSDF(new Onion2dSDF(new UnevenCapsuleSDF(0.1,0.05,.3), .02), .7);
        SignedDistanceField sdf2 = new SphereSDF(1, new Vector3d(-.5, 0, 0));
        SignedDistanceField sdf = new SubtractionSDF(sdf2, sdf1);

        shapes.add(new SDFShape(new Vector3d(1, 1, 2), sdf));
        /*shapes.add(new Sphere(new Vector3d(2, 0, 1), .15));

        shapes.add(new Sphere(new Vector3d(2, .5, 1), .15));
        shapes.add(new Sphere(new Vector3d(2, 1, 1), .15));*/

        //Bottom Plane
        shapes.add(new Plane(new Vector3d(0, 0, .75), new Vector3d(0, 0, 1)));

        //Top Plane
        //shapes.add(new Plane(new Vector3d(0, 0, 2), new Vector3d(0, 0, -1)));

        //Right Plane
        //shapes.add(new Plane(new Vector3d(0, -.5, 0), new Vector3d(0, 1, 0)));

        // Left Plane
        //shapes.add(new Plane(new Vector3d(0, 1.5, 0), new Vector3d(0, -1, 0)));

        // Behind Plane
        //shapes.add(new Plane(new Vector3d(3, 0, 0), new Vector3d(-1, 0, 0)));


        lights.add(new DistantLight(new Color(1, 1, 1), 2, new Vector3d(0, -.2, -1)));
        //lights.add(new PointLight(new Color(1, 1, 1), 7, new Vector3d(1, 0, 1)));
        lights.add(new PointLight(new Color(1, 1, 1), 6, new Vector3d(0, 0, 1.5)));
    }

    public void prepare() {
        shapes.forEach(Shape::prepare);
    }
}
