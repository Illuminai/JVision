package com.illuminai.vision;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.shape.Cylinder;
import com.illuminai.vision.frontend.GameClass;

public class Vision {

    static void test() {
        //TODO spot in the middle of cylinder?
        //TODO remove
        Ray ray = new Ray(new Point3d(-1,-.5,0), new Vector3d(1,0,0));
        Cylinder c = new Cylinder(new Point3d(0,-.5,0),1,1, new Point3d(0,Math.PI/2,Math.PI/2),0xffff00 );
        Intersection s = c.getIntersection(ray);

        //System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        test();
        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}
