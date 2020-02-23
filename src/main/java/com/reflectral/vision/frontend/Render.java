package com.reflectral.vision.frontend;

import com.reflectral.vision.backend.math.Point3d;
import com.reflectral.vision.backend.render.Raytracer;
import com.reflectral.vision.backend.scene.Scene;
import com.reflectral.vision.backend.scene.shape.Sphere;

public class Render {
    private final Screen renderOn;
    private final GameCanvas parent;
    private Raytracer tracer;
    private double time = 0;

    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.parent = parent;
        init();
    }

    private void init() {
        Sphere sphere = new Sphere(new Point3d(0, 0, 1), 1);
        Scene scene = new Scene();
        scene.getShapes().add(sphere);

        tracer = new Raytracer(scene);
    }

    public void render() {
        //TODO render here your image


        int[] p = this.getScreen().getPixels();
        for(int i = 0; i < p.length; i++){
            p[i] = (int)(Math.random() * 0xFFFFFF);
        }
        renderOn.drawScreen(0,0,tracer.renderScene());
    }

    public void tick() {
        tracer.getScene().getShapes().get(0).setPosition(new Point3d(2 * Math.sin(time/20),2 * Math.cos(time/20), 3));

        time++;
    }

    public Screen getScreen() {
        return renderOn;
    }
}
