package ch.herzog.lighthouse;

import ch.herzog.lighthouse.math.Point3d;
import ch.herzog.lighthouse.render.Raytracer;
import ch.herzog.lighthouse.scene.Scene;
import ch.herzog.lighthouse.scene.shape.Sphere;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LightHouse {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Sphere sphere = new Sphere(new Point3d(0, 0, 2), 1);
        Scene scene = new Scene();
        scene.getShapes().add(sphere);

        Raytracer raytracer = new Raytracer(scene);
        BufferedImage image = raytracer.renderScene();

        File output = new File("D:/", "img.png");
        try {
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
