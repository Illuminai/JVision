package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.Screen;

public class Raytracer {

    private Scene scene;

    private Point3d position;

    public Raytracer(Scene scene) {
        this.scene = scene;
        position = new Point3d(0,0,0);
    }

    public Screen renderScene() {
        //TODO y-axis is rendered as height; the z-axis should be the height, as it makes it easier when doing math-stuff
        Screen screen = new Screen(500,500);
        for (int x = 0; x < 500; x++) {
            for (int y = 0; y < 500; y++) {
                double u = (x - 250) / 500.0;
                double v = (y - 250) / 500.0;
                Point3d origin = new Point3d(position.getX(),position.getY(),position.getZ());
                Vector direction = new Vector(10,0,0)
                        .add(new Vector(0,0,4).scale(u))
                        .add(new Vector(0,-4,0).scale(v));

                Ray ray = new Ray(origin, direction);

                Intersection nearest = null;
                for (Shape shape : scene.getShapes()) {
                    Intersection intersection = shape.getIntersection(ray);
                    if (nearest == null || (intersection != null && intersection.getTime() < nearest.getTime())) {
                        nearest = intersection;
                    }
                }

                if (nearest != null) {
                    screen.setPixel(x,y, nearest.getShape().getColor());
                } else {
                    screen.setPixel(x, y, 0xff00ff);
                }
            }
        }
        return screen;
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
}
