package com.illuminai.vision.backend.shape.sdf.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.shape.sdf.SignedDistanceField;

public class SphereSDF extends SignedDistanceField {

    private double radius;
    private Vector3d position;

    public SphereSDF(double radius, Vector3d position) {
        this.radius = radius;
        this.position = position;
    }

    public SphereSDF(double radius) {
        this.radius = radius;
        position = new Vector3d(0, 0, 0);
    }

    @Override
    public double getDistance(Vector3d point) {
        return point.subtract(position).length() - radius;
    }
}
