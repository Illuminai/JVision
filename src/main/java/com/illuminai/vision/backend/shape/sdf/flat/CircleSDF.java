package com.illuminai.vision.backend.shape.sdf.flat;

import com.illuminai.vision.backend.math.Vector2d;

public class CircleSDF extends SDF2d {

    private double radius;

    public CircleSDF(double radius) {
        this.radius = radius;
    }

    @Override
    public double getDistance(Vector2d point) {
        return point.length() - radius;
    }

}
