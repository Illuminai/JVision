package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;

public abstract class Light {

    protected Color color;
    protected double intensity;

    public Light(Color color, double intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public abstract Vector3d getDirection(Vector3d point);

    public abstract Color getLightIntensity(Vector3d point);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}
