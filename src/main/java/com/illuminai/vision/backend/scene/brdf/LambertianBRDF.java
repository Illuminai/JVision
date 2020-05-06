package com.illuminai.vision.backend.scene.brdf;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;

public class LambertianBRDF extends BRDF {

    // diffuse reflection coefficient
    // k_d \in [0,1]
    private double kd = .6;

    // diffuse color
    private Color cd = new Color(.8, .8, .8);

    @Override
    public Color f(Vector3d wo, Vector3d wi) {
        return cd.multiply(kd / Math.PI);
    }

    @Override
    public Color rho() {
        return cd.multiply(kd);
    }

}
