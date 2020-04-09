package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.Camera;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Mesh;
import com.illuminai.vision.frontend.Screen;

public class Raymarcher {

    private Scene scene;
    private Camera camera;

    private long[] outliner;

    private double renderWidth = 800,renderHeight = 600;

    public Raymarcher(Scene scene) {
        this.scene = scene;
        camera = new Camera(new Vector3d(-5, 0, 0),
                new Vector3d(0, 0, 0));
    }

    public Screen renderScene() {
        Screen screen = new Screen((int)renderWidth, (int)renderHeight);
        outliner = new long[screen.getPixels().length];

        for (int x = 0; x < renderWidth; x++) {
            for (int y = 0; y < renderHeight; y++) {
                double u = (x - renderWidth/2) / renderWidth;
                double v = (y - renderHeight/2) / renderHeight;
                Ray ray = camera.getRay(u, v);

                Color color = marchRay(ray, x, y);
                screen.setPixel(x, y, color.getRGB());
            }
        }
        return screen;
    }


    private Color marchRay(Ray ray, int x, int y) {
        Intersection intersection = getIntersection(ray);
        if (intersection == null) {
            Vector3d unitDirection = ray.getDirection().normalize();
            double t = 0.5 * (unitDirection.getZ() + 1.0);
            Vector3d colorVector = new Vector3d(1, 1, 1).scale(1.0 - t).add(new Vector3d(.5, .7, 1).scale(t));
            return new Color((int) (255 * colorVector.getX()), (int) (255 * colorVector.getY()), (int) (255 * (colorVector.getZ())));
        }

        outliner[x + y * (int)renderWidth] = intersection.getMesh().getId();
        //normal direction algorithm
        Vector3d normal = intersection.getNormal();
        return new Color((int) (255 * (normal.getX() + 1)), (int) (255 * (normal.getY() + 1)), (int) (255 * (normal.getZ() + 1))).intensify(0.5);

        //facing ratio algorithm
        /*double facingRatio = Math.max(0, intersection.getNormal().dot(ray.getDirection().scale(-1)));
        Color color = new Color(0xffff00);
        Color intense = color.intensify(facingRatio);
        return intense;*/

    }

    public Intersection getIntersection(Ray ray) {
        double time = 0;
        int maxDist = 100;
        while (time < maxDist) {
            Mesh intersected = null;
            double minDist = maxDist;
            Vector3d point = ray.getPointOnRay(time);

            for (Mesh sdf : scene.getMeshes()) {
                double d = sdf.getDistance(point);
                if (d < minDist) {
                    minDist = d;
                    intersected = sdf;
                }
            }

            if (minDist <= 10e-6 * time) {
                Vector3d normal = estimateNormal(intersected, point);
                return new Intersection(ray, intersected, normal, time);
            }
            time += minDist;
        }
        return null;
    }

    public Vector3d estimateNormal(Mesh mesh, Vector3d point) {
        return new Vector3d(
                mesh.getDistance(point.add(new Vector3d(10e-10, 0, 0))) - mesh.getDistance(point.subtract(new Vector3d(10e-10, 0, 0))),
                mesh.getDistance(point.add(new Vector3d(0, 10e-10, 0))) - mesh.getDistance(point.subtract(new Vector3d(0, 10e-10, 0))),
                mesh.getDistance(point.add(new Vector3d(0, 0, 10e-10))) - mesh.getDistance(point.subtract(new Vector3d(0, 0, 10e-10)))
        ).normalize();
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
