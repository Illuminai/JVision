package ch.herzog.lighthouse.render;

import ch.gedik.lighthouse.graphics.Screen;
import ch.herzog.lighthouse.math.Point3d;
import ch.herzog.lighthouse.math.Vector3d;
import ch.herzog.lighthouse.scene.Scene;
import ch.herzog.lighthouse.scene.shape.Shape;

import java.awt.image.BufferedImage;

public class Raytracer {

    private Scene scene;

    public Raytracer(Scene scene) {
        this.scene = scene;
    }

    public Screen renderScene() {
        //BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Screen screen = new Screen(500,500);
        for (int x = 0; x < 500; x++) {
            for (int y = 0; y < 500; y++) {
                double u = (double) x / 500;
                double v = (double) y / 500;
                Point3d origin = new Point3d(0, 0, 0);
                Vector3d direction = new Vector3d(-2, -1, -1)
                        .add(new Vector3d(4, 0, 0).scale(u))
                        .add(new Vector3d(0, 4, 0).scale(v));
                Ray ray = new Ray(origin, direction);

                Intersection nearest = null;
                for (Shape shape : scene.getShapes()) {
                    Intersection intersection = shape.getIntersection(ray);
                    if (nearest == null || intersection.getTime() < nearest.getTime()) {
                        nearest = intersection;
                    }
                }

                if (nearest != null) {
                    screen.setPixel(x,y,0x00ff00);
                } else {
                    screen.setPixel(x, y, 0xff00ff);
                }
            }
        }
        return screen;
    }

}
