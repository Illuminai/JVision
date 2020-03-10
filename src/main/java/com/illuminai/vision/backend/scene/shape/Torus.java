package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Point3d;

public class Torus extends Shape {

    private double majorRadius = .2;

    private double minorRadius = 0.1;

    public Torus(Point3d position, int color, double majorRadius, double minorRadius) {
        super(position, color);
        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
    }

    public Torus(Point3d position, int color) {
        super(position, color);
    }

    @Override
    public double getDistance(Point3d point) {
        double x = Math.sqrt(point.getX() * point.getX() + point.getZ() * point.getZ()) - majorRadius;
        double y = point.getY();
        return Math.sqrt(x * x + y * y) - minorRadius;
    }

    public double getMajorRadius() {
        return majorRadius;
    }

    public void setMajorRadius(double majorRadius) {
        this.majorRadius = majorRadius;
    }

    public double getMinorRadius() {
        return minorRadius;
    }

    public void setMinorRadius(double minorRadius) {
        this.minorRadius = minorRadius;
    }
}
