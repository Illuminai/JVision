package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.camera.Camera;
import com.illuminai.vision.backend.scene.camera.PinholeCamera;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.material.DiffuseMaterial;
import com.illuminai.vision.backend.scene.material.Material;
import com.illuminai.vision.backend.shape.Shape;
import com.illuminai.vision.frontend.Screen;

public class Raytracer {

    private Scene scene;
    private Camera camera;

    private long[] outliner;

    private double renderWidth = 800, renderHeight = 600;

    private int maxDepth = 4;

    public Raytracer(Scene scene) {
        this.scene = scene;
        camera = new PinholeCamera(new Vector3d(-10, 0, 1), new Vector3d(0, 0, 0));
    }

    public Screen renderScene(int samples) {
        scene.prepare();
        camera.prepare();

        Screen screen = new Screen((int) renderWidth, (int) renderHeight);
        outliner = new long[screen.getPixels().length];


        for (int x = 0; x < renderWidth; x++) {
            for (int y = 0; y < renderHeight; y++) {

                Color sampled = new Color(0, 0, 0);
                for (int s = 1; s <= samples; s++) {
                    double sampleX = getSampledHalton(s, 2);
                    double sampleY = getSampledHalton(s, 3);
                    double u = (x + sampleX - renderWidth / 2) / renderWidth;
                    double v = (y + sampleY - renderHeight / 2) / renderHeight;
                    Ray ray = camera.getRay(u, v);

                    Color color = marchRay(ray, x, y, maxDepth);
                    sampled = sampled.multiply(s - 1).add(color).multiply(1.0 / s);
                }

                screen.setPixel(x, y, sampled.getRGB());
            }
        }
        return screen;
    }

    // TODO: sampler in own class
    public double getSampledHalton(int index, int base) {
        double f = 1;
        double r = 0;

        double i = index;
        while (i > 0) {
            f = f / base;
            r = r + f * (i % base);
            i = Math.floor(i / base);
        }

        return r;
    }

    // Hardcoded debug boolean
    boolean debug = true;

    private Color marchRay(Ray ray, int x, int y, int depth) {
        Intersection intersection = getIntersection(ray);

        if (intersection == null || depth < 1) {
            return new Color(0, 0, 0);
        }

        if (depth == maxDepth) outliner[x + y * (int) renderWidth] = intersection.getShape().getId();


        Color irradiance = new Color(0, 0, 0);

        if (debug) {
            Vector3d normal = intersection.getNormal();
            return new Color(normal.getX() + 1, normal.getY() + 1, normal.getZ() + 1).multiply(0.5);
        }

        Material mat = new DiffuseMaterial();
        for (Light light : scene.getLights()) {
            Ray hitToLight = new Ray(intersection.getPoint().add(intersection.getNormal().scale(10e-6)), light.getDirection(intersection.getPoint()).scale(-1.0));
            if (!isShadow(light, hitToLight)) {
                irradiance = irradiance.add(mat.shade(intersection, light));
            }
        }

        return irradiance;
    }

    public boolean isShadow(Light light, Ray ray) {
        Intersection intersection = getIntersection(ray);
        if (intersection == null || light.getPosition().subtract(ray.getOrigin()).length() < intersection.getPoint().subtract(ray.getOrigin()).length())
            return false;
        return true;
    }

    public Intersection getIntersection(Ray ray) {
        Intersection nearest = null;
        for (Shape shape : scene.getShapes()) {
            Intersection intersection = shape.getIntersection(ray);
            if (nearest == null || (intersection != null && intersection.getTime() < nearest.getTime())) {
                nearest = intersection;
            }
        }
        return nearest;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene getScene() {
        return scene;
    }

    public long[] getOutliner() {
        return outliner;
    }

    public double getRenderWidth() {
        return renderWidth;
    }

    public double getRenderHeight() {
        return renderHeight;
    }
}
