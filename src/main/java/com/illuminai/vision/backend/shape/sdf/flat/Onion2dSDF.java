package com.illuminai.vision.backend.shape.sdf.flat;

import com.illuminai.vision.backend.math.Vector2d;

public class Onion2dSDF extends SDF2d {

    private SDF2d sdf2d;
    private double radius;

    public Onion2dSDF(SDF2d sdf2d, double radius) {
        this.sdf2d = sdf2d;
        this.radius = radius;
    }

    @Override
    public double getDistance(Vector2d point) {
        return Math.abs(sdf2d.getDistance(point)) - radius;
    }
}
