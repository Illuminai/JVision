package com.illuminai.vision;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.shape.Cylinder;
import com.illuminai.vision.frontend.GameClass;

public class Vision {

    static void test() {
        //TODO remove
        Ray ray = new Ray(new Point3d(-1,-1,-1), new Vector3d(1,1,1));
        Cylinder c = new Cylinder(new Point3d(0,0,0), 1,1,0 );
        Intersection s = c.getIntersection(ray);

        //System.out.println(s.getNormal());

        //System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        test();
        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}
