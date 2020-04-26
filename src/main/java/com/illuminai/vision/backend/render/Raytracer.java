package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.Camera;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.light.Light;
import com.illuminai.vision.backend.scene.material.DiffuseMaterial;
import com.illuminai.vision.backend.scene.material.FresnelMaterial;
import com.illuminai.vision.backend.scene.material.SpecularMaterial;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.Screen;

public class Raytracer {

    private Scene scene;
    private Camera camera;

    private long[] outliner;

    private double renderWidth = 800, renderHeight = 600;

    private int maxDepth = 4;

    public Raytracer(Scene scene) {
        this.scene = scene;
        camera = new Camera(new Vector3d(-10, 0, 0), new Vector3d(0, 0, 0));
    }

    public Screen renderScene(double maxDiversion) {
        scene.prepare();
        camera.prepare();

        Screen screen = new Screen((int) renderWidth, (int) renderHeight);
        outliner = new long[screen.getPixels().length];

        for (int x = 0; x < renderWidth; x++) {
            for (int y = 0; y < renderHeight; y++) {
                double u = ((x + maxDiversion * (.5 - Math.random())) - renderWidth / 2.0) / renderWidth;
                double v = ((y + maxDiversion * (.5 - Math.random())) - renderHeight / 2.0) / renderHeight;
                Ray ray = camera.getRay(u, v);

                Color color = marchRay(ray, x, y, maxDepth);
                screen.setPixel(x, y, color.getRGB());
            }
        }
        return screen;
    }

    private Color marchRay(Ray ray, int x, int y, int depth) {

        Intersection intersection = getIntersection(ray);
        if (intersection == null || depth < 1) {
            // Lerp sky
            Vector3d unitDirection = ray.getDirection().normalize();
            double t = 0.5 * (unitDirection.getZ() + 1.0);
            Vector3d colorVector = new Vector3d(1, 1, 1).scale(1.0 - t).add(new Vector3d(.5, .7, 1).scale(t));
            return new Color(colorVector.getX(), colorVector.getY(), colorVector.getZ());
            //return new Color(0, 0, 0);
        }


        Color directLight = new Color(0, 0, 0);

        if (depth == maxDepth) outliner[x + y * (int) renderWidth] = intersection.getShape().getId();

        //direct illumination : hard coded -> code into materials
        if (intersection.getShape().getMaterial() instanceof DiffuseMaterial) {
            DiffuseMaterial material = (DiffuseMaterial) intersection.getShape().getMaterial();
            for (Light light : scene.getLights()) {
                Vector3d hitToLight = light.getDirection(intersection.getPoint()).scale(-1.0);
                Ray shadowRay = new Ray(intersection.getPoint().add(intersection.getNormal().scale(10e-6)), hitToLight);
                if (!isShadow(light, shadowRay)) {
                    Vector3d normal = intersection.getNormal();
                    Vector3d c = new Vector3d(1, 1, 1);
                    c = new Vector3d(c.getX() * light.getColor().getRed(), c.getY() * light.getColor().getGreen(), c.getZ() * light.getColor().getBlue());
                    c = new Vector3d(Math.min(1.0, c.getX() * light.getLightIntensity(intersection.getPoint()).getRed()),
                            Math.min(1.0, c.getY() * light.getLightIntensity(intersection.getPoint()).getGreen()),
                            Math.min(1.0, c.getZ() * light.getLightIntensity(intersection.getPoint()).getBlue()));
                    c = c.scale(material.getAlbedo()).scale(Math.max(0.0, normal.dot(hitToLight)));
                    directLight = directLight.add(c);
                }
            }
        } else if (intersection.getShape().getMaterial() instanceof SpecularMaterial) {
            SpecularMaterial material = (SpecularMaterial) intersection.getShape().getMaterial();
            Ray specularRay = getReflectionRay(ray, intersection.getPoint(), intersection.getNormal());
            directLight = directLight.add(marchRay(specularRay, x, y, depth - 1).multiply(material.getSpecular()));
        } else if (intersection.getShape().getMaterial() instanceof FresnelMaterial) {
            FresnelMaterial material = (FresnelMaterial) intersection.getShape().getMaterial();
            Vector3d origin = intersection.getRay().getOrigin();
            Vector3d direction = intersection.getRay().getDirection();
            Vector3d normal = intersection.getNormal();
            double kr = getFresnelReflection(direction, normal, material.getIor());
            boolean outside = direction.dot(normal) < 0;
            Vector3d bias = normal.scale(10e-6);
            Color refractionColor = new Color(0, 0, 0);
            if (kr < 1) {
                Vector3d refractionDirection = getRefractedDirection(direction, normal, material.getIor());
                Vector3d refractionRayOrigin = outside ? origin.add(bias) : origin.subtract(bias);
                refractionColor = marchRay(new Ray(refractionRayOrigin, refractionDirection), x, y, depth - 1);
            }
            Ray specularRay = getReflectionRay(ray, intersection.getPoint(), intersection.getNormal());
            Color reflectionColor = marchRay(specularRay, x, y, depth - 1);

            directLight = directLight.add(reflectionColor.multiply(kr)).add(refractionColor.multiply(1.0 - kr));
        }

        return directLight;
    }

    public Ray getReflectionRay(Ray ray, Vector3d point, Vector3d normal) {
        Vector3d incoming = ray.getDirection().normalize();
        return new Ray(point.add(normal.scale(10e-6)), incoming.subtract(normal.scale(2.0 * incoming.dot(normal)))); // 10e-6 is epsilon
    }

    public Vector3d getRefractedDirection(Vector3d direction, Vector3d normal, double ior) {
        double cosIncident = clamp(direction.dot(normal), -1, 1);
        double etaIncident = 1;
        double etaTransmission = ior;
        Vector3d n = normal;
        if (cosIncident < 0) {
            cosIncident = -cosIncident;
        } else {
            // Java Swap
            double tempEtaIncident = etaIncident;
            etaIncident = etaTransmission;
            etaTransmission = tempEtaIncident;
            n = normal.scale(-1.0);
        }
        double eta = etaIncident / etaTransmission;
        double k = 1 - eta * eta * (1 - cosIncident * cosIncident);
        return k < 0 ? new Vector3d(0, 0, 0) : direction.scale(eta).add(new Vector3d(
                eta * cosIncident - Math.sqrt(k) * n.getX(),
                eta * cosIncident - Math.sqrt(k) * n.getY(),
                eta * cosIncident - Math.sqrt(k) * n.getZ()));
    }

    public double getFresnelReflection(Vector3d direction, Vector3d normal, double ior) {
        double cosIncident = clamp(direction.dot(normal), -1, 1);
        double etaIncident = 1;
        double etaTransmission = ior;
        if (cosIncident > 0) {
            // Java Swap
            double tempEtaIncident = etaIncident;
            etaIncident = etaTransmission;
            etaTransmission = tempEtaIncident;
        }

        double sinTransmission = etaIncident / etaTransmission * Math.sqrt(Math.max(0.0, 1 - cosIncident * cosIncident));
        if (sinTransmission >= 1) {
            // Only internal reflection
            return 1;
        } else {
            double cosTransmission = Math.sqrt(Math.max(0.0, 1 - sinTransmission * sinTransmission));
            cosIncident = Math.abs(cosIncident);
            double rs = ((etaTransmission * cosIncident) - (etaIncident * cosTransmission)) /
                    ((etaTransmission * cosIncident) + (etaIncident * cosTransmission));
            double rp = ((etaIncident * cosIncident) - (etaTransmission * cosTransmission)) /
                    ((etaIncident * cosIncident) + (etaTransmission * cosTransmission));
            return (rs * rs + rp * rp) / 2;
        }
    }

    public double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public boolean isShadow(Light light, Ray ray) {
        Intersection intersection = getIntersection(ray);
        if (intersection == null || intersection.getTime() > light.getDirection(intersection.getPoint()).scale(-1.0).length())
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
