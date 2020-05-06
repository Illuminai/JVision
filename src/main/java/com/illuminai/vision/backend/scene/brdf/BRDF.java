package com.illuminai.vision.backend.scene.brdf;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;

public abstract class BRDF {

    public abstract Color f(Vector3d wo, Vector3d wi);

    public abstract Color rho();

}
