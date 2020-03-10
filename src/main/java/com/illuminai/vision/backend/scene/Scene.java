package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.shape.Cylinder;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.backend.scene.shape.Sphere;

import javax.swing.*;
import java.awt.*;
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

    /**
     * the lights
     */
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

    /**
     * @return the lights
     */
    public List<Light> getLights() {
        return lights;
    }

    //TODO: Remove hard coded stuff

    private double time = 0;

    public void tick() {

        red.setPosition(new Point3d(0,Math.sin(time/20), Math.cos(time/20)));
        green.setPosition(new Point3d(Math.cos(time/20),0, Math.sin(time/20)));
        blue.setPosition(new Point3d(Math.sin(time/20), Math.cos(time/20), 0));

        //test.setPosition(new Point3d(0,Math.sin(time/10)/2 - .5,0));
        test.setRotation(new Point3d(Math.PI/2,Math.PI/2, 0));

        time++;
    }

    Sphere red, green, blue;
    Cylinder test;
    public void sceneInit() {

        //getShapes().add(test = new Cylinder(new Point3d(0,-.5,0),1,1, new Point3d(0,Math.PI/2,Math.PI/2),0xffff00));
        getShapes().add(test = new Cylinder(new Point3d(0,-.5,0),1,1, new Point3d(0,0,0),0xffff00));

        for(int x = -5; x < 6; x++) {
            if(x == 5)
                getShapes().add(new Sphere(new Point3d(x/10.0,0,0),.05,0x7f0000));
            else
            getShapes().add(new Sphere(new Point3d(x/10.0,0,0),.05,0xff0000));

        }

        for(int y = -5; y < 6; y++) {
            if(y == 5)
                getShapes().add(new Sphere(new Point3d(0,y/10.0,0),.05,0x007f00));
            else
            getShapes().add(new Sphere(new Point3d(0,y/10.0,0),.05,0x00ff00));
        }
        for(int z = -5; z < 6; z++) {
            if(z == 5)
                getShapes().add(new Sphere(new Point3d(0,0,z/10.0),.05,0x00007f));
            else
            getShapes().add(new Sphere(new Point3d(0,0,z/10.0),.05,0x0000ff));
        }
        red = new Sphere(new Point3d(0, 0, 1), .1,0xff0000);
        getShapes().add(red);

        green = new Sphere(new Point3d(1, 0, 0), .1,0x00ff00);
        getShapes().add(green);

        blue = new Sphere(new Point3d(0,1,0),.1,0x0000ff);
        getShapes().add(blue);


    }
}
