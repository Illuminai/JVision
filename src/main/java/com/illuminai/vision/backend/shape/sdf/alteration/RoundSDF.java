package com.illuminai.vision.backend.shape.sdf.alteration;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.shape.sdf.SignedDistanceField;

public class RoundSDF extends SignedDistanceField {

    private SignedDistanceField sdf;
    private double radius;

    public RoundSDF(SignedDistanceField sdf, double radius) {
        this.sdf = sdf;
        this.radius = radius;
    }

    @Override
    public double getDistance(Vector3d point) {
        return sdf.getDistance(point) - radius;
    }
}
