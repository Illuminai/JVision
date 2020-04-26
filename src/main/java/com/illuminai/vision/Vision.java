package com.illuminai.vision;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.shape.Plane;
import com.illuminai.vision.backend.scene.shape.Triangle;
import com.illuminai.vision.frontend.GameClass;

public class Vision {
    public static void main(String[] args) throws Exception {
        GameClass game = GameClass.createGame(args, 800, 600);
        game.initCanvas();
        game.start();
    }
}
