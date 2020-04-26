package com.illuminai.vision.backend.scene.material;

public class FresnelMaterial extends Material {

    private double ior;

    public FresnelMaterial(double ior) {
        this.ior = ior;
    }

    public double getIor() {
        return ior;
    }

    public void setIor(double ior) {
        this.ior = ior;
    }
}
