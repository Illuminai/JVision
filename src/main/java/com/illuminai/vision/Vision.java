package com.illuminai.vision;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.shape.Plane;
import com.illuminai.vision.backend.scene.shape.Triangle;
import com.illuminai.vision.frontend.GameClass;

public class Vision {
    private static void test() {
        Triangle t = new Triangle(
                new Vector3d(-1,0,-3),
                new Vector3d(1,0,0),new Vector3d(0,0,1),null);
        Plane p = new Plane(new Vector3d(0,0,0), null, new Vector3d(0,-1,0));
        Ray ray = new Ray(new Vector3d(0,-3,0), new Vector3d(0,1,0));

        Intersection i1 = p.getIntersection(ray);
        Intersection i2 = t.getIntersection(ray);

        //System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        test();
        GameClass game = GameClass.createGame(args, 800, 600);
        game.initCanvas();
        game.start();
    }

}
