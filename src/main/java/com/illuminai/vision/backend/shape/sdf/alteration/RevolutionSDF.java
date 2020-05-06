package com.illuminai.vision.backend.shape.sdf.alteration;

import com.illuminai.vision.backend.math.Vector2d;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.shape.sdf.SignedDistanceField;
import com.illuminai.vision.backend.shape.sdf.flat.SDF2d;

public class RevolutionSDF extends SignedDistanceField {

    private SDF2d sdf2d;
    private double radius;

    public RevolutionSDF(SDF2d sdf2d, double radius) {
        this.sdf2d = sdf2d;
        this.radius = radius;
    }

    @Override
    public double getDistance(Vector3d point) {
        Vector2d q = new Vector2d(new Vector2d(point.getX(), point.getZ()).length() - radius, point.getY());
        return sdf2d.getDistance(q);
    }

}
