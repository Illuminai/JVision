package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Matrix3x3;
import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.Screen;

public class Raymarcher {

    private Scene scene;

    private Point3d position;

    private Point3d rotation;

    public Raymarcher(Scene scene) {
        this.scene = scene;
        position = new Point3d(0, 0, 0);
        position = new Point3d(-5, 0, 0);
        rotation = new Point3d(0, 0, 0);
    }

    public Screen renderScene() {
        //TODO y-axis is rendered as height; the z-axis should be the height, as it makes it easier when doing math-stuff
        Screen screen = new Screen(500, 500);
        Matrix3x3 rotationMatrix = Matrix3x3.createRotationMatrix(rotation.getX(), rotation.getY(), rotation.getZ());
        for (int x = 0; x < 500; x++) {
            for (int y = 0; y < 500; y++) {
                double u = (x - 250) / 500.0;
                double v = (y - 250) / 500.0;
                Point3d origin = new Point3d(position.getX(), position.getY(), position.getZ());
                Vector3d direction = rotationMatrix.transformed(new Vector3d(10, 0, 0)
                        .add(new Vector3d(0, 0, -4).scale(v))
                        .add(new Vector3d(0, 4, 0).scale(u)));

                Ray ray = new Ray(origin, direction);

                int color = marchRay(ray);
                screen.setPixel(x, y, color);
            }
        }
        return screen;
    }

    private int marchRay(Ray ray) {
        Intersection intersection = getIntersection(ray);
        if (intersection == null) {
            return 0x000000;
        }

        double facingRatio = Math.max(0, intersection.getNormal().dot(ray.getDirection().scale(-1)));
        Color color = new Color(intersection.getShape().getColor());
        Color intense = color.intensify(facingRatio);

        return intense.getRGB();
    }

    public Intersection getIntersection(Ray ray) {
        double time = 0;
        int maxDist = 100;

        while (time < maxDist) {
            Shape intersected = null;
            double minDist = Double.POSITIVE_INFINITY;
            Point3d point = ray.getPointOnRay(time);

            for (Shape shape : scene.getShapes()) {
                double d = shape.getDistance(point);
                if (d < minDist) {
                    minDist = d;
                    intersected = shape;
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

    public Vector3d estimateNormal(Shape shape, Point3d point) {
        return new Vector3d(
                shape.getDistance(point.add(new Vector3d(10e-6, 0, 0))) - shape.getDistance(point.subtract(new Vector3d(10e-6, 0, 0))),
                shape.getDistance(point.add(new Vector3d(0, 10e-6, 0))) - shape.getDistance(point.subtract(new Vector3d(0, 10e-6, 0))),
                shape.getDistance(point.add(new Vector3d(0, 0, 10e-6))) - shape.getDistance(point.subtract(new Vector3d(0, 0, 10e-6)))
        ).normalize();
    }


    /**
     * Returns the direction which currently looked at
     */
    public Vector3d getDirection() {
        return Matrix3x3.createRotationMatrix(rotation.getX(), rotation.getY(), rotation.getZ()).transformed(new Vector3d(1, 0, 0));
    }

    /**
     * Moves the camera forward in the direction of {@link #rotation}
     */
    public void moveForward(double amount) {
        position.set(position.add(getDirection().scale(amount)));
    }

    /**
     * Moves the camera forward in the direction perpendicular to {@link #rotation}
     */
    public void moveSideward(double amount) {
        Vector3d p = new Vector3d(getDirection());
        p.setZ(0);
        position.set(position.add(Matrix3x3.createRotationMatrix('z', Math.PI / 2).transformed(p.scale(amount))));
    }

    public void moveUpwards(double amount) {
        position.set(position.add(new Vector3d(0, 0, 1).scale(amount)));
    }

    public Scene getScene() {
        return scene;
    }

    public Point3d getPosition() {
        return position;
    }

    public void setPosition(Point3d position) {
        this.position = position;
    }

    public Point3d getRotation() {
        return rotation;
    }

    public void setRotation(Point3d rotation) {
        this.rotation = rotation;
    }
}
