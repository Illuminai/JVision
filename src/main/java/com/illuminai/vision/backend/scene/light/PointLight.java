package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Vector3d;

public class PointLight {

    private Vector3d position;

    public PointLight(Vector3d position) {
        this.position = position;
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }
}
