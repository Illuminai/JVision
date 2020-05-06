package com.illuminai.vision.backend.shape.sdf.flat;

import com.illuminai.vision.backend.math.Vector2d;

public class UnevenCapsuleSDF extends SDF2d {

    private double radius1;
    private double radius2;
    private double height;

    public UnevenCapsuleSDF(double radius1, double radius2, double height) {
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.height = height;
    }

    @Override
    public double getDistance(Vector2d point) {
        Vector2d p = point;
        p.setX(Math.abs(p.getX()));

        double b = (radius1 - radius2) / height;
        double a = Math.sqrt(1.0 - b * b);
        double k = p.dot(new Vector2d(-b, a));
        if (k < 0.0) return p.length() - radius1;
        if (k > a * height) return p.subtract(new Vector2d(0, height)).length() - radius2;
        return p.dot(new Vector2d(a, b)) - radius1;
    }

}
