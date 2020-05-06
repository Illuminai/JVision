package com.illuminai.vision.backend.scene.camera;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Ray;

public class PinholeCamera extends Camera {

    public PinholeCamera(Vector3d position, Vector3d rotation) {
        super(position, rotation);
    }

    @Override
    public Ray getRay(double u, double v) {
        Vector3d direction = rotationMatrix.transformed(new Vector3d(10, 0, 0)
                .add(new Vector3d(0, 4, 0).scale(u)))
                .add(new Vector3d(0, 0, -3).scale(v));
        return new Ray(position, direction);
    }
}
