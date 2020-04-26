package com.illuminai.vision.backend.scene.material;

public class SpecularMaterial extends Material {

    private double specular;

    public SpecularMaterial(double specular) {
        this.specular = specular;
    }

    public double getSpecular() {
        return specular;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }
}
