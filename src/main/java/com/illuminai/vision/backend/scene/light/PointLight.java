package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;

public class PointLight extends Light {

    private Vector3d position;

    public PointLight(Color color, double intensity, Vector3d position) {
        super(color, intensity);
        this.position = position;
    }

    @Override
    public Vector3d getDirection(Vector3d point) {
        return point.subtract(position).normalize();
    }

    @Override
    public Color getLightIntensity(Vector3d point) {
        double r2 = point.subtract(position).length();
        return color.multiply(intensity).multiply(1.0 / 4.0 * Math.PI * r2);
    }

}
