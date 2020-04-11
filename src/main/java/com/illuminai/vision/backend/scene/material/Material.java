package com.illuminai.vision.backend.scene.material;

public class Material {

    private double albedo;

    public Material(double albedo) {
        this.albedo = albedo;
    }

    public double getAlbedo() {
        return albedo;
    }

    public void setAlbedo(double albedo) {
        this.albedo = albedo;
    }

}
