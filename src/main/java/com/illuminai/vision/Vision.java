package com.illuminai.vision;

import com.illuminai.vision.backend.math.Matrix3x3;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.frontend.GameClass;

public class Vision {

    public static void main(String[] args) throws Exception {
        /*
        //TEST CODE
        Matrix3x3 m = Matrix3x3.createRotationMatrix('x', Math.PI);
        Vector3d v = new Vector3d(12,6,1);
        return;
        */

        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}
