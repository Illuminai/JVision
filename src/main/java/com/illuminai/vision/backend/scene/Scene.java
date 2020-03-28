package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.shape.Mesh;
import com.illuminai.vision.backend.sdf.alteration.IntersectionSDF;
import com.illuminai.vision.backend.sdf.alteration.SubtractionSDF;
import com.illuminai.vision.backend.sdf.shape.Sphere;
import com.illuminai.vision.backend.sdf.shape.Torus;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene
 */
public class Scene {

    /**
     * the shapes
     */
    private List<Mesh> meshes;

    /**
     * Constructs and initializes a Scene.
     */
    public Scene() {
        meshes = new ArrayList<>();
    }

    /**
     * @return the shapes
     */
    public List<Mesh> getMeshes() {
        return meshes;
    }

    //TODO: Remove hard coded stuff

    private double time = 0;

    public void tick() {
        time++;
    }

    //Sphere red, green, blue;
    public void sceneInit() {
        Sphere s1 = new Sphere(new Vector3d(0, 0, 0), .3);
        Torus s2 = new Torus(.1, .3);
        IntersectionSDF sdf = new IntersectionSDF(s1, s2);

        Sphere s3 = new Sphere(new Vector3d(.2,.2,.2), .2);
        SubtractionSDF sdf2 = new SubtractionSDF(s3, sdf);

        meshes.add(new Mesh(sdf2));
        meshes.add(new Mesh(new Sphere(new Vector3d(0,0,-2) ,.4)));
    }
}
