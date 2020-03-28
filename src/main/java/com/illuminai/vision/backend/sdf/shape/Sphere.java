package com.illuminai.vision.backend.sdf.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.sdf.SignedDistanceField;

public class Sphere extends SignedDistanceField {

    private Vector3d position;
    private double radius;

    public Sphere(Vector3d position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public double getDistance(Vector3d point) {
        return position.subtract(point).length() - radius;
    }
}
