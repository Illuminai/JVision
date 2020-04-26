package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;

public class DistantLight extends Light {

    private Vector3d direction;

    public DistantLight(Color color, double intensity, Vector3d direction) {
        super(color, intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getLightIntensity(Vector3d point) {
        return color.multiply(intensity);
    }

    @Override
    public Vector3d getDirection(Vector3d point) {
        return direction;
    }
}
