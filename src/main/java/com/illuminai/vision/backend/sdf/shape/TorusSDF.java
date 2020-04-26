package com.illuminai.vision.backend.sdf.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.sdf.SignedDistanceField;

public class TorusSDF extends SignedDistanceField {

    private double minorRadius;
    private double majorRadius;

    public TorusSDF(double minorRadius, double majorRadius) {
        this.minorRadius = minorRadius;
        this.majorRadius = majorRadius;
    }

    @Override
    public double getDistance(Vector3d point) {
        double x = Math.sqrt(point.getX() * point.getX() + point.getZ() * point.getZ()) - majorRadius;
        double y = point.getY();
        return Math.sqrt(x * x + y * y) - minorRadius;
    }

}
