package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.Camera;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.Screen;

public class Raytracer {

    private Scene scene;
    private Camera camera;

    private long[] outliner;

    private double renderWidth = 800, renderHeight = 600;

    public Raytracer(Scene scene) {
        this.scene = scene;
        camera = new Camera(new Vector3d(-5, 0, 0),
                new Vector3d(0, 0, 0));
    }

    public Screen renderScene(double maxDiversion) {
        Screen screen = new Screen((int) renderWidth, (int) renderHeight);
        //outliner = new long[screen.getPixels().length];

        for (int x = 0; x < renderWidth; x++) {
            for (int y = 0; y < renderHeight; y++) {
                //double u = ((x + maxDiversion * (.5 - Math.random())) - renderWidth / 2.0) / renderWidth;
                //double v = ((y + maxDiversion * (.5 - Math.random())) - renderHeight / 2.0) / renderHeight;
                double u = (x - 400) / 800.0;
                double v = (y - 300) / 600.0;
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
            return new Color(colorVector.getX(), colorVector.getY(), colorVector.getZ());
        }

        Color color = new Color(0, 0, 0);

        //outliner[x + y * (int)renderWidth] = intersection.getShape().getId();

        for (Light light : scene.getLights()) {
            Vector3d hitToLight = light.getDirection(intersection.getPoint()).scale(-1.0);
            boolean shade = isShadow(new Ray(intersection.getPoint().add(intersection.getNormal().scale(0.0001)), hitToLight));
            if (!shade) {
                Vector3d normal = intersection.getNormal();
                Vector3d c = new Vector3d(1, 1, 1);
                c = new Vector3d(c.getX() * light.getColor().getRed(), c.getY() * light.getColor().getGreen(), c.getZ() * light.getColor().getBlue());
                c = new Vector3d(Math.min(1.0, c.getX() * light.getLightIntensity(intersection.getPoint()).getRed()),
                        Math.min(1.0, c.getY() * light.getLightIntensity(intersection.getPoint()).getGreen()),
                        Math.min(1.0, c.getZ() * light.getLightIntensity(intersection.getPoint()).getBlue()));
                c = c.scale(intersection.getShape().getMaterial().getAlbedo()).scale(Math.max(0.0, normal.dot(hitToLight)));
                color = color.add(c);
            }
        }
        return color;
    }

    public boolean isShadow(Ray ray) {
        Intersection intersection = getIntersection(ray);
        return intersection != null;
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
