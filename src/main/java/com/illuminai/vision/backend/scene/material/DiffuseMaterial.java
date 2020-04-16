package com.illuminai.vision.backend.scene.material;

import com.illuminai.vision.backend.render.Color;

public class DiffuseMaterial extends Material {

    private double albedo;
    private Color emittance;

    public DiffuseMaterial(double albedo, Color emittance) {
        this.albedo = albedo;
        this.emittance = emittance;
    }

    public double getAlbedo() {
        return albedo;
    }

    public void setAlbedo(double albedo) {
        this.albedo = albedo;
    }

    public Color getEmittance() {
        return emittance;
    }

    public void setEmittance(Color emittance) {
        this.emittance = emittance;
    }


}
